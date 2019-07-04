package spotify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import spotify.command.songCommand.SongCommandExecutor;
import spotify.command.userCommand.UserCommandExecutor;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;
import spotify.service.UsersRepo;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientProcessorTest {
    @Mock
    private Set<String> userCommands;
    @Mock
    private Set<String> songCommands;
    @Mock
    private SongsList songsList;
    @Mock
    private UsersRepo usersRepo;
    @Mock
    private Socket clientSocket;
    @Mock
    private PlaylistsRepo playlistsRepo;
    @Mock
    private UserCommandExecutor userCommandExecutor;
    @Mock
    private SongCommandExecutor songCommandExecutor;
    @Mock
    private OutputStream outputStream;
    @Mock
    private UsersData usersData;
    private ClientProcessor clientProcessor;
    private String commandName;
    private InputStream inputStream;

    @Before
    public void setUp() {
        clientProcessor = new ClientProcessor(playlistsRepo, clientSocket,
                songsList, usersRepo,
                userCommandExecutor, songCommandExecutor, userCommands,
                songCommands);
    }

    @Test
    public void testRunWithoutRegisteredUser() throws IOException {
        commandName = "top";
        inputStream = new StringBufferInputStream(commandName);
        when(clientSocket.getInputStream()).thenReturn(inputStream);
        when(clientSocket.getOutputStream()).thenReturn(outputStream);
        when(clientSocket.isClosed()).thenReturn(false);
        when(clientSocket.isConnected()).thenReturn(true);
        when(songCommands.contains(commandName)).thenReturn(true);
        doThrow(new IOException()).when(clientSocket).close();
        clientProcessor.run();
    }

    @Test
    public void testRunSuccessful() throws IOException {
        commandName = "login";
        inputStream = new StringBufferInputStream(commandName);
        when(clientSocket.getInputStream()).thenReturn(inputStream);
        when(clientSocket.getOutputStream()).thenReturn(outputStream);
        when(userCommandExecutor.executeCommand(usersRepo, usersData,
                clientSocket, commandName)).thenReturn("");
        when(clientSocket.isClosed()).thenReturn(false);
        when(clientSocket.isConnected()).thenReturn(true);
        when(userCommands.contains(commandName)).thenReturn(true);
        doThrow(new IOException()).when(clientSocket).close();
        clientProcessor.run();
    }

    @Test
    public void testRunUnsuccessful() throws IOException {
        commandName = "command";
        inputStream = new StringBufferInputStream(commandName);
        when(clientSocket.getInputStream()).thenReturn(inputStream);
        when(clientSocket.getOutputStream()).thenReturn(outputStream);
        when(clientSocket.isClosed()).thenReturn(false);
        when(clientSocket.isConnected()).thenReturn(true);
        doThrow(new IOException()).when(clientSocket).close();
        clientProcessor.run();
    }
}