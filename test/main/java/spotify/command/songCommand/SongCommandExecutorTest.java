package spotify.command.songCommand;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import spotify.command.userCommand.UserCommand;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class SongCommandExecutorTest {
    private static final String SUCCESSFULLY_EXECUTE = "successfully execute";
    private static final String INPUT = "show-playlist playlistsName";
    private static final String SECOND_INPUT = "show playlistsName";
    private static final String PLAYLIST_NAME= "playlistsName";
    private final static String SHOW_COMMAND_NOT_FOUND = "show command not found";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private SongsList songsList;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private UsersData usersData;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PlaylistsRepo playlistsRepo;
    @Mock
    private Player player;
    @Mock
    private ShowPlaylistCommand showPlaylistCommand;
    private SongCommandExecutor songCommandExecutor;

    @Before
    public void setUp() {
        List<SongCommand> comamands = new ArrayList<>();
        comamands.add(new ShowPlaylistCommand());
        songCommandExecutor = new SongCommandExecutor(comamands);
    }

    @Test
    public void testExecuteSuccessfully() {
        when(playlistsRepo.showPlaylist(PLAYLIST_NAME, usersData.getUser().getEmail())).thenReturn(SUCCESSFULLY_EXECUTE);
        when(showPlaylistCommand.execute(songsList,
                usersData,
                playlistsRepo,
                player,
                PLAYLIST_NAME)).thenReturn(SUCCESSFULLY_EXECUTE);

        assertEquals(SUCCESSFULLY_EXECUTE,
                songCommandExecutor.executeCommand(INPUT,
                        songsList,
                        usersData,
                        playlistsRepo,
                        player));
    }

    @Test
    public void testExecuteUnsuccessfully() {
        assertEquals(SHOW_COMMAND_NOT_FOUND,
                songCommandExecutor.executeCommand(SECOND_INPUT,
                        songsList,
                        usersData,
                        playlistsRepo,
                        player));


    }
}
