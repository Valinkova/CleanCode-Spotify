package spotify.command.songCommand;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreatePlaylistCommandTest {
    private static final String USER_IS_NOT_INSERTED_CORRECT_DATA = "You are " +
            "not inserted correct data";
    private static final String PLAYLIST_IS_CREATED_SUCCESSFULLY =
            "playlistsName is successfully created";
    private static final String SONG_NAME = "songsName";
    private static final String EMAIL = "email";
    private static final String PLAYLIST_NAME = "playlistsName";

    @Mock
    private SongsList songsList;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private static UsersData usersData;
    @Mock
    private PlaylistsRepo playlistsRepo;
    @Mock
    private Player player;
    private CreatePlaylistCommand createPlaylistCommand;

    @Before
    public void setUp() {
        createPlaylistCommand =
                new CreatePlaylistCommand();
    }

    @Test
    public void testCreatePlaylistCommandSuccessfully() {
        when(usersData.getUser().getEmail()).thenReturn(EMAIL);
        when(playlistsRepo.createPlaylist(PLAYLIST_NAME, EMAIL))
                .thenReturn(PLAYLIST_IS_CREATED_SUCCESSFULLY);

        assertEquals(PLAYLIST_IS_CREATED_SUCCESSFULLY,
                createPlaylistCommand.execute(songsList,
                        usersData,
                        playlistsRepo,
                        player,
                        PLAYLIST_NAME));

    }

    @Test
    public void testAddSongCommandWithIncorrectData() {
        when(usersData.getUser().getEmail()).thenReturn(EMAIL);
        assertEquals(USER_IS_NOT_INSERTED_CORRECT_DATA,
                createPlaylistCommand.execute(songsList,
                        usersData,
                        playlistsRepo,
                        player,
                        PLAYLIST_NAME, SONG_NAME));

        verify(playlistsRepo, never()).addSongToPlaylist(anyString(), any(),
                anyString());
    }


}
