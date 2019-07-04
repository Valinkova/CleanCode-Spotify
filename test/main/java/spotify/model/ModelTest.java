package spotify.model;

import org.junit.Test;
import org.mockito.Mock;
import spotify.model.Playlist;
import spotify.model.Song;
import spotify.service.UsersRepo;

import java.net.Socket;

import static org.junit.Assert.*;

public class ModelTest {
    private static final String PLAYLIST_NAME = "playlistsName";
    private static final String SONG_NAME = "songsName";
    private static final String SECOND_SONG_NAME = "name";
    private static final String SONG_PATH = "songsPath";
    private static final String WORDS = "Song";
    private static final String SONG_ALREADY_EXIST = "%s already exist";
    private static final String SONG_IS_ADDED_SUCCESSFULLY = "%s is added " +
            "successfully";
    private static final String USER_IS_SUCCESSFULLY_LOGIN = "%s is " +
            "successfully login";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Mock
    Socket clientSocket;

    @Test
    public void testAddSongToPlaylistSuccessfully() {
        Playlist playlist = new Playlist(PLAYLIST_NAME);
        Song song = new Song(SONG_NAME, SONG_PATH);

        assertEquals(playlist.addSong(song),
                String.format(SONG_IS_ADDED_SUCCESSFULLY, SONG_NAME));
    }

    @Test
    public void testAddSongToPlaylistUnsuccessfully() {
        Playlist playlist = new Playlist(PLAYLIST_NAME);
        Song song = new Song(SONG_NAME, SONG_PATH);
        playlist.addSong(song);

        assertEquals(playlist.addSong(song), String.format(SONG_ALREADY_EXIST
                , SONG_NAME));
    }

    @Test
    public void testContainsWordSuccessfully() {
        Song song = new Song(SONG_NAME, SONG_PATH);
        assertTrue(song.containsWords(WORDS));
    }

    @Test
    public void testContainsWordUnsuccessfully() {
        Song song = new Song(SECOND_SONG_NAME, SONG_PATH);
        assertFalse(song.containsWords(WORDS));
    }


    @Test
    public void testLoginUserSuccessfully() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();
        usersRepo.registerUser(EMAIL, PASSWORD, usersData);

        assertEquals(usersRepo.loginUser(EMAIL, PASSWORD, usersData,
                clientSocket),
                String.format(USER_IS_SUCCESSFULLY_LOGIN, EMAIL));
        assertEquals(usersData.getUser().getEmail(), EMAIL);
    }

}
