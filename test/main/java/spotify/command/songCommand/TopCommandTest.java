package spotify.command.songCommand;

import org.junit.Before;
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

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static spotify.command.songCommand.TopCommand.THERE_IS_NO_SUCH_CURRENTLY_LISTEN_TOP_SONGS;

@RunWith(MockitoJUnitRunner.class)
public class TopCommandTest {
    private static final String USER_IS_NOT_INSERTED_CORRECT_DATA = "You are " +
            "not inserted correct data";
    private static final String NUMBER = "6";
    private static final String SONG_NAME = "song name";

    @Mock
    private static SongsList songsList;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private UsersData usersData;
    @Mock
    private PlaylistsRepo playlistsRepo;
    @Mock
    private Player player;
    @Mock
    private Song song;
    private TopCommand topCommand;
    private Set<Song> songs;

    @Before
    public void setUp() {
        topCommand = new TopCommand();
        songs = new LinkedHashSet<>();
    }

    @Test
    public void testTopCommandWithNoSongs() {
        when(songsList.getTopCurrentlyListenSongs(Integer.parseInt(NUMBER))).thenReturn(songs);

        assertEquals(THERE_IS_NO_SUCH_CURRENTLY_LISTEN_TOP_SONGS,
                topCommand.execute(songsList,
                        usersData,
                        playlistsRepo,
                        player,
                        NUMBER));
    }

    @Test
    public void testTopCommandWithAnySongs() {
        songs.add(song);
        when(songsList.getTopCurrentlyListenSongs(Integer.parseInt(NUMBER))).thenReturn(songs);
        when(song.getName()).thenReturn(SONG_NAME);

        assertEquals(SONG_NAME,
                topCommand.execute(songsList,
                        usersData,
                        playlistsRepo,
                        player,
                        NUMBER));
    }

    @Test
    public void testTopCommandWithIncorrectData() {
        when(songsList.getTopCurrentlyListenSongs(Integer.parseInt(NUMBER))).thenReturn(songs);

        assertEquals(USER_IS_NOT_INSERTED_CORRECT_DATA,
                topCommand.execute(songsList,
                        usersData,
                        playlistsRepo,
                        player,
                        NUMBER, NUMBER));
    }
}


