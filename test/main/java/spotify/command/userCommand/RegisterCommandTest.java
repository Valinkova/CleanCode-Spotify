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

import static spotify.command.userCommand.UserCommand.USER_WAS_NOT_INSERTED_CORRECT_DATA;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterCommandTest {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String SUCCESSFULLY = "successfully";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private static UsersData usersData;
    @Mock
    private PlaylistsRepo playlistsRepo;
    @Mock
    private static UsersRepo usersRepo;
    @Mock
    private Socket clientSocket;
    private RegisterCommand registerCommand;

    @Before
    public void setUp() {
        registerCommand = new RegisterCommand();
    }

    @Test
    public void testRegisterCommandSuccessfully() {
        when(usersRepo.registerUser(EMAIL, PASSWORD, usersData))
                .thenReturn(SUCCESSFULLY);

        assertEquals(SUCCESSFULLY,
                registerCommand.execute(usersRepo, usersData, clientSocket,
                        EMAIL, PASSWORD));
    }

    @Test
    public void testRegisterCommandWithIncorrectData() {
        when(usersRepo.registerUser(EMAIL, PASSWORD, usersData))
                .thenReturn(SUCCESSFULLY);

        assertEquals(USER_WAS_NOT_INSERTED_CORRECT_DATA,
                registerCommand.execute(usersRepo, usersData, clientSocket,
                        EMAIL));

        verify(playlistsRepo, never()).addSongToPlaylist(anyString(), any(),
                anyString());
    }
}
