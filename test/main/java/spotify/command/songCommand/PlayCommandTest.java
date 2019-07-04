package spotify.command.songCommand;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spotify.model.Song;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayCommandTest {
    private static final String SONG_NAME = "song name";
    private static final String SONG_PATH = "song path";

    @Mock
    private SongsList songsList;
    @Mock
    private UsersData usersData;
    @Mock
    private PlaylistsRepo playlistsRepo;
    @Mock
    private Player player;
    @Mock
    private Song song;
    private PlayCommand playCommand;

    @Before
    public void setUp() {
        playCommand = new PlayCommand();
    }

    @Test
    public void testPlaySongSuccessfully() {
        doNothing().when(player).setSongPath(SONG_PATH);
        when(songsList.getSong(SONG_NAME)).thenReturn(song);
        when(song.getPath()).thenReturn(SONG_PATH);

        playCommand.execute(songsList, usersData, playlistsRepo, player,
                SONG_NAME);
    }
}