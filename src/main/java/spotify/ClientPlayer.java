package spotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ClientPlayer {
    private static final String SONG_WAS_STOPPED = "Song was stopped";
    private static final Logger log = LoggerFactory.getLogger(ClientPlayer.class);
    private AtomicBoolean playing = new AtomicBoolean(false);

    void playSelectedSong(SourceDataLine sourceDataLine, AudioInputStream audioInputStream) {
        Thread playingThread = new Thread(() -> {
            try {
                sourceDataLine.start();
                byte[] bytesBuffer = new byte[4096];
                int bytesRead = -1;
                playing.set(true);

                while (playing.get() && (bytesRead =
                        audioInputStream.read(bytesBuffer)) != -1) {
                    sourceDataLine.write(bytesBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                playing.set(false);
            }
        });
        playingThread.start();
    }

    void stopPlayingSong(Socket clientSocket) throws IOException {
        playing.set(false);

        while (clientSocket.getInputStream().available() > 0) {
            clientSocket.getInputStream().skip(clientSocket.getInputStream().available());
        }

        System.out.println(SONG_WAS_STOPPED);
    }
}
