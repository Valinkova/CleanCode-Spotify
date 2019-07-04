package spotify.command.userCommand;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import spotify.command.songCommand.ShowPlaylistCommand;
import spotify.command.songCommand.SongCommand;
import spotify.command.songCommand.SongCommandExecutor;
import spotify.model.UsersData;
import spotify.service.UsersRepo;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserCommandExecutorTest {
    private static final String INPUT = "disconnect";
    private static final String SUCCESSFULLY = "successfully";
    private static final String SECOND_INPUT = "show";
    private final static String SHOW_COMMAND_NOT_FOUND = "show command not found";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private static UsersData usersData;
    @Mock
    private DisconnectCommand disconnectCommand;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private static UsersRepo usersRepo;
    @Mock
    private Socket clientSocket;
    private UserCommandExecutor userCommandExecutor;


    @Before
    public void setUp() {
        List<UserCommand> comamands = new ArrayList<>();
        comamands.add(new DisconnectCommand());
        userCommandExecutor = new UserCommandExecutor(comamands);
    }

    @Test
    public void testExecuteSuccessfully() {
        when(usersRepo.disconnectUser(usersData)).thenReturn(SUCCESSFULLY);
        when(disconnectCommand.execute(usersRepo, usersData, clientSocket, INPUT)).thenReturn(SUCCESSFULLY);

        assertEquals(SUCCESSFULLY,
                userCommandExecutor.executeCommand(usersRepo, usersData, clientSocket, INPUT));
    }

    @Test
    public void testExecuteWithWrongCommand() {
        assertEquals(SHOW_COMMAND_NOT_FOUND,
                userCommandExecutor.executeCommand(usersRepo, usersData, clientSocket, SECOND_INPUT));
    }
}
