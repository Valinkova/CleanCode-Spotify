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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String SUCCESSFULLY = "successfully";
    private static final String USER_IS_NOT_INSERTED_CORRECT_DATA = "You are " +
            "not inserted correct data";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private static UsersData usersData;
    @Mock
    private PlaylistsRepo playlistsRepo;
    @Mock
    private static UsersRepo usersRepo;
    @Mock
    private static Socket clientSocket;
    private LoginCommand loginCommand;


    @Before
    public void setUp() {
        loginCommand = new LoginCommand();
    }

    @Test
    public void testLoginCommandSuccessfully() {
        when(usersRepo.loginUser(EMAIL, PASSWORD, usersData, clientSocket))
                .thenReturn(SUCCESSFULLY);

        assertEquals(SUCCESSFULLY,
                loginCommand.execute(usersRepo, usersData, clientSocket,
                        EMAIL, PASSWORD));

    }

    @Test
    public void testLoginCommandWithIncorrectData() {
        when(usersRepo.loginUser(EMAIL, PASSWORD, usersData, clientSocket))
                .thenReturn(SUCCESSFULLY);

        assertEquals(USER_IS_NOT_INSERTED_CORRECT_DATA,
                loginCommand.execute(usersRepo, usersData, clientSocket,
                        EMAIL));

        verify(playlistsRepo, never()).addSongToPlaylist(anyString(), any(),
                anyString());
    }
}
