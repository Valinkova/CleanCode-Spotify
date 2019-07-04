package spotify.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class Player extends Thread {
    private static final Logger log = LoggerFactory.getLogger(Player.class);
    private static final String NO_SONG_IS_PLAYED = "No song is played";
    private static final String THE_SONG_WAS_STOPPED = "%s was stopped";
    private static final String SPACE_DELIMITER = " ";
    private static final String NEW_LINE = "\n";

    private String songPath;
    private Socket clientSocket;

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * This method start playing song
     */
    @Override
    public void run() {
        playSong();
    }

    private void playSong() {
        try {
            File audioFile = new File(songPath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat audioFormat = audioInputStream.getFormat();
            String format = audioFormat.getEncoding() + SPACE_DELIMITER + audioFormat.getSampleRate()
                    + SPACE_DELIMITER + audioFormat.getSampleSizeInBits() + SPACE_DELIMITER + audioFormat.getChannels()
                    + SPACE_DELIMITER + audioFormat.getFrameSize() + SPACE_DELIMITER + audioFormat.getFrameRate()
                    + SPACE_DELIMITER + audioFormat.isBigEndian() + NEW_LINE;

            byte[] formatBytes = format.getBytes();
            clientSocket.getOutputStream().write(formatBytes);
            byte[] bytes = Files.readAllBytes(Paths.get(audioFile.getAbsolutePath()));
            clientSocket.getOutputStream().write(bytes);

        } catch (IOException | UnsupportedAudioFileException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * This method stop playing song
     */
    public String stopSong() {
        if (this.isAlive()) {
            this.interrupt();
            return THE_SONG_WAS_STOPPED;
        }

        return NO_SONG_IS_PLAYED;
    }
}
