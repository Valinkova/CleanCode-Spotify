package spotify.command.songCommand;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spotify.model.Song;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchCommandTest {
    private static final String THERE_IS_NO_SUCH_SONG = "There is no such song";
    private static final String SONG_NAME = "songsName";

    @Mock
    private SongsList songsList;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private UsersData usersData;
    @Mock
    private PlaylistsRepo playlistsRepo;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Song song;
    @Mock
    private Player player;
    private SearchCommand searchCommand;

    @Before
    public void setUp() {
        searchCommand = new SearchCommand();
    }

    @Test
    public void testSearchCommandSuccessfully() {
        List<Song> songs = new ArrayList<>();
        songs.add(song);
        when(songsList.searchSongsByWords(SONG_NAME)).thenReturn(songs);
        when(song.getName()).thenReturn(SONG_NAME);

        assertEquals(SONG_NAME,
                searchCommand.execute(songsList,
                        usersData,
                        playlistsRepo,
                        player,
                        SONG_NAME));

    }

    @Test
    public void testSearchWithNotExistsSong() {
        assertEquals(THERE_IS_NO_SUCH_SONG,
                searchCommand.execute(songsList,
                        usersData,
                        playlistsRepo,
                        player));

        verify(playlistsRepo, never()).addSongToPlaylist(anyString(), any(),
                anyString());
    }
}
