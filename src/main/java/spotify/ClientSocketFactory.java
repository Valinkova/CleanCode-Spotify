package spotify;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
public class ClientSocketFactory {
    private static final String LOCALHOST = "localhost";

    public Socket getSocket() throws IOException {
        return new Socket(LOCALHOST, SpotifyServer.PORT);
    }
}
