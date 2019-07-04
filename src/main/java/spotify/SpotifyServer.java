package spotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spotify.service.PlaylistsRepo;
import spotify.service.UsersRepo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class SpotifyServer implements CommandLineRunner {
    private static final Logger log =
            LoggerFactory.getLogger(SpotifyServer.class);
    static final int PORT = 4444;

    private ClientProcessor clientProcessor;
    private StateSaver stateSaver;

    @Autowired
    public SpotifyServer(UsersRepo usersRepo,
                         PlaylistsRepo playlistsRepo,
                         ClientProcessor clientProcessor,
                         StateSaver stateSaver) {

        this.clientProcessor = clientProcessor;
        this.stateSaver = stateSaver;
        stateSaver.usersFromJson(usersRepo);
        stateSaver.playlistFromJson(playlistsRepo);
    }


    /**
     * This method run the server
     */
    public void run(String... args) throws Exception {
        log.info("Starting server on port {}", PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("Successfully started server on port {}", PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    log.info("Accepted socket from {}",
                            clientSocket.getInetAddress().getHostName());
                    clientProcessor.start();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
