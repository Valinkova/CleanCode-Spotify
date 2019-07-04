package spotify.command.userCommand;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spotify.model.UsersData;
import spotify.service.PlaylistsRepo;
import spotify.service.UsersRepo;

import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DisconnectCommandTest {
    private static final String USER_IS_NOT_INSERTED_CORRECT_DATA = "You are " +
            "not inserted correct data";
    private static final String SUCCESSFULLY = "successfully";
    private static final String EMAIL = "email";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private static UsersData usersData;
    @Mock
    private PlaylistsRepo playlistsRepo;
    @Mock
    private static UsersRepo usersRepo;
    @Mock
    private Socket clientSocket;
    private DisconnectCommand disconnectCommand;

    @Before
    public void setUp() {
        disconnectCommand = new DisconnectCommand();
    }

    @Test
    public void testDisconnectCommandSuccessfully() {
        when(usersRepo.disconnectUser(usersData))
                .thenReturn(SUCCESSFULLY);

        assertEquals(SUCCESSFULLY,
                disconnectCommand.execute(usersRepo, usersData, clientSocket));

    }

    @Test
    public void testDisconnectCommandWithIncorrectData() {
        when(usersRepo.disconnectUser(usersData))
                .thenReturn(SUCCESSFULLY);

        assertEquals(USER_IS_NOT_INSERTED_CORRECT_DATA,
                disconnectCommand.execute(usersRepo, usersData, clientSocket,
                        EMAIL));

        verify(playlistsRepo, never()).addSongToPlaylist(any(), any(), any());
    }
}
