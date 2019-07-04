package spotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import spotify.command.CommandName;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@SpringBootApplication
@Import({AppConfig.class})
public class SpotifyClient {
    static final String FIRSTLY_YOU_SHOULD_REGISTER_OR_LOGIN = "firstly you " +
            "should register or login";
    private static final Logger log =
            LoggerFactory.getLogger(SpotifyClient.class);
    private static final String SPACE_DELIMITER = " ";
    private static final String PLAY = "play";
    private static final String STOP = "stop";

    private Socket clientSocket;
    private ClientPlayer player;

    @Autowired
    public SpotifyClient(Socket clientSocket, ClientPlayer player) {
        this.clientSocket = clientSocket;
        this.player = player;
    }

    private void playSong(Socket clientSocket) {
        try {
            BufferedInputStream bufferedInputStream =
                    new BufferedInputStream(clientSocket.getInputStream());
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(bufferedInputStream));
            String[] format = bufferedReader.readLine().split(SPACE_DELIMITER);
            StringBuilder readLine = new StringBuilder();

            for (String formatPart : format)
                readLine.append(formatPart).append(SPACE_DELIMITER);

            if (readLine.toString().contains(FIRSTLY_YOU_SHOULD_REGISTER_OR_LOGIN)) {
                System.out.println(FIRSTLY_YOU_SHOULD_REGISTER_OR_LOGIN);
                return;
            }

            selectSong(bufferedInputStream, format);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void selectSong(BufferedInputStream bufferedInputStream,
                            String[] format) {

        AudioInputStream audioInputStream = null;
        SourceDataLine sourceDataLine = null;
        try {
            audioInputStream =
                    AudioSystem.getAudioInputStream(bufferedInputStream);
            AudioFormat audioFormat =
                    new AudioFormat(new AudioFormat.Encoding(format[0])
                            , Float.parseFloat(format[1]),
                            Integer.parseInt(format[2])
                            , Integer.parseInt(format[3]),
                            Integer.parseInt(format[4])
                            , Float.parseFloat(format[5]),
                            Boolean.parseBoolean(format[6]));

            DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                    audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceDataLine.open(audioFormat);

        } catch (LineUnavailableException | IOException |
                UnsupportedAudioFileException e) {
            log.error(e.getMessage(), e);
        }
        player.playSelectedSong(sourceDataLine, audioInputStream);
    }

    private void stopSong(Socket clientSocket) throws IOException {
        if (player != null) {
            player.stopPlayingSong(clientSocket);
        }
    }

    /**
     * This method run the client
     * @param inputStream This is the stream from where read the user's input
     */
    public void run(InputStream inputStream) {
        try {
            PrintWriter bufferedWriter =
                    new PrintWriter(clientSocket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            Scanner scanner = new Scanner(inputStream);
            String inputCommand;
            boolean isDisconnectCommand = false;

            while (!isDisconnectCommand) {
                if (scanner.hasNextLine()) {
                    inputCommand = scanner.nextLine();
                    System.out.println(inputCommand);
                    bufferedWriter.println(inputCommand);
                    bufferedWriter.flush();

                    if (inputCommand.startsWith(PLAY)) {
                        playSong(clientSocket);
                    } else if (inputCommand.startsWith(STOP)) {
                        stopSong(clientSocket);
                    } else {
                        String messageAfterExecuteCommand =
                                bufferedReader.readLine();
                        System.out.println(messageAfterExecuteCommand);
                    }

                    if (inputCommand.equals(CommandName.DISCONNECT.toString())) {
                        isDisconnectCommand = true;
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpotifyClient.class, args);
    }
}


