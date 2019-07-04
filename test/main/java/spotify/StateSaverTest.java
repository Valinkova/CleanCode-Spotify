package spotify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spotify.service.PlaylistsRepo;
import spotify.service.UsersRepo;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StateSaverTest {
    @Mock
    private UsersRepo usersRepo;
    @Mock
    private PlaylistsRepo playlistsRepo;
    private StateSaver stateSaver;

    @Before
    public void setUp(){
        stateSaver = new StateSaver();
    }

    @Test
    public void testGetUsersFromJsonSuccessfully() {
        doNothing().when(usersRepo).addRegisteredUsers(anySet());
        stateSaver.usersFromJson(usersRepo);
    }

    @Test
    public void testGetPlaylistFromJsonSuccessfully() {
        stateSaver.playlistFromJson(playlistsRepo);
    }
}