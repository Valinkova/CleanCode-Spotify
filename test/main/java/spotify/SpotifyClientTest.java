package spotify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;
import static spotify.SpotifyClient.FIRSTLY_YOU_SHOULD_REGISTER_OR_LOGIN;

@RunWith(MockitoJUnitRunner.class)
public class SpotifyClientTest {
    @Mock
    private Socket clientSocket;
    @Mock
    private ClientPlayer player;
    @Mock
    private ClientSocketFactory clientSocketFactory;
    private SpotifyClient spotifyClient;
    private String disconnectCommand;
    private String playCommand;
    private InputStream inputStream;
    private OutputStream outputStream;

    @Before
    public void setUp() throws IOException {
        when(clientSocketFactory.getSocket()).thenReturn(clientSocket);
        spotifyClient = new SpotifyClient(clientSocketFactory, player);
        outputStream = new ByteArrayOutputStream();
        disconnectCommand = "disconnect";
        playCommand = "play";
    }

    @Test
    public void testRunUnsuccessful() throws IOException {
        inputStream = new StringBufferInputStream(disconnectCommand);
        when(clientSocket.getOutputStream()).thenReturn(outputStream);
        when(clientSocket.getInputStream()).thenReturn(inputStream);
        doNothing().when(player).stopPlayingSong(clientSocket);

        spotifyClient.run(inputStream);
    }

    @Test
    public void testRuntWithoutLogin() throws IOException {
        inputStream =
                new StringBufferInputStream(playCommand + "\n" + disconnectCommand);
        InputStream secondInputStream =
                new StringBufferInputStream(FIRSTLY_YOU_SHOULD_REGISTER_OR_LOGIN);
        when(clientSocket.getOutputStream()).thenReturn(outputStream);
        when(clientSocket.getInputStream()).thenReturn(secondInputStream);
        doNothing().when(player).stopPlayingSong(clientSocket);

        spotifyClient.run(inputStream);
    }
}