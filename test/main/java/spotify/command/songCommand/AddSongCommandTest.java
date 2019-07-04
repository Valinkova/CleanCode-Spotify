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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddSongCommandTest {
    private static final String USER_IS_NOT_INSERTED_CORRECT_DATA = "You are " +
            "not inserted correct data";
    private static final String SONG_NAME = "songsName";
    private static final String EMAIL = "email";
    private static final String PLAYLIST_NAME = "playlistsName";
    private static final String SUCCESSFULLY = "successfully";

    @Mock
    private static SongsList songsList;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private static UsersData usersData;
    @Mock
    private PlaylistsRepo playlistsRepo;
    @Mock
    private static Song song;
    @Mock
    private Player player;
    private AddSongCommand addSongCommand;

    @Before
    public void setUp() {
        addSongCommand = new AddSongCommand();
    }

    @Test
    public void testAddSongCommandSuccessfully() {
        when(usersData.getUser().getEmail()).thenReturn(EMAIL);
        when(songsList.getSong(SONG_NAME)).thenReturn(song);
        when(playlistsRepo.addSongToPlaylist(PLAYLIST_NAME, song, EMAIL))
                .thenReturn(SUCCESSFULLY);

        assertEquals(SUCCESSFULLY,
                addSongCommand.execute(songsList,
                        usersData,
                        playlistsRepo,
                        player,
                        PLAYLIST_NAME,
                        SONG_NAME));
    }

    @Test
    public void testAddSongCommandWithIncorrectData() {
        when(usersData.getUser().getEmail()).thenReturn(EMAIL);
        when(songsList.getSong(SONG_NAME)).thenReturn(song);

        assertEquals(USER_IS_NOT_INSERTED_CORRECT_DATA,
                addSongCommand.execute(songsList,
                        usersData,
                        playlistsRepo,
                        player,
                        PLAYLIST_NAME));

        verify(playlistsRepo, never()).addSongToPlaylist(anyString(), any(),
                anyString());
    }
}
