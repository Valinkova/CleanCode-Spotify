package spotify.service;

import org.junit.Test;
import spotify.model.Song;

import static org.junit.Assert.*;

public class PlaylistsRepoTest {
    private static final String PLAYLIST_ALREADY_EXIST = "%s already exist";
    private static final String PLAYLIST_IS_CREATED_SUCCESSFULLY = "%s is " +
            "successfully created";
    private static final String PLAYLIST_IS_NOT_EXIST = "%s is not exist";
    private static final String SONG_NOT_EXIST = "This song not exist";
    private static final String PLAYLIST_IS_EMPTY = "-- empty --";
    private static final String SONG_IS_ADDED_SUCCESSFULLY = "%s is added " +
            "successfully";
    private static final String SONGS_NAME = "songsName";
    private static final String PLAYLISTS_NAME = "playlistsName";
    private static final String SECOND_PLAYLISTS_NAME = "secondPlaylistsName";
    private static final String USERNAME = "username";
    private static final String SONGS_PATH = "path";

    @Test
    public void testAddSongInPlaylistsRepoUnsuccessfullyWhenPlaylistIsNotExist() {
        PlaylistsRepo playlistsRepo = new PlaylistsRepo();
        Song song = new Song(SONGS_NAME, SONGS_PATH);
        playlistsRepo.addSongToPlaylist(PLAYLISTS_NAME, song, USERNAME);

        assertEquals(playlistsRepo.addSongToPlaylist(PLAYLISTS_NAME, song,
                USERNAME),
                String.format(PLAYLIST_IS_NOT_EXIST, PLAYLISTS_NAME));
    }

    @Test
    public void testAddSongInPlaylistsRepoSuccessfully() {
        PlaylistsRepo playlistsRepo = new PlaylistsRepo();
        Song song = new Song(SONGS_NAME, SONGS_PATH);
        playlistsRepo.createPlaylist(PLAYLISTS_NAME, USERNAME);

        assertEquals(playlistsRepo.addSongToPlaylist(PLAYLISTS_NAME, song,
                USERNAME),
                String.format(SONG_IS_ADDED_SUCCESSFULLY, SONGS_NAME));
    }

    @Test
    public void testAddSongInPlaylistsRepoUnsuccessfullyWhenSongIsNotExist() {
        PlaylistsRepo playlistsRepo = new PlaylistsRepo();
        Song song = null;
        playlistsRepo.createPlaylist(PLAYLISTS_NAME, USERNAME);
        playlistsRepo.addSongToPlaylist(PLAYLISTS_NAME, song, USERNAME);

        assertEquals(playlistsRepo.addSongToPlaylist(PLAYLISTS_NAME, song,
                USERNAME), SONG_NOT_EXIST);
    }

    @Test
    public void testCreatePlaylistInPlaylistsRepoSuccessfully() {
        PlaylistsRepo playlistsRepo = new PlaylistsRepo();

        assertEquals(playlistsRepo.createPlaylist(PLAYLISTS_NAME, USERNAME),
                String.format(PLAYLIST_IS_CREATED_SUCCESSFULLY,
                        PLAYLISTS_NAME));
    }

    @Test
    public void testCreatePlaylistInPlaylistsRepoSuccessfullyWhenRepoIsNotEmpty() {
        PlaylistsRepo playlistsRepo = new PlaylistsRepo();
        playlistsRepo.createPlaylist(SECOND_PLAYLISTS_NAME, USERNAME);

        assertEquals(playlistsRepo.createPlaylist(PLAYLISTS_NAME, USERNAME),
                String.format(PLAYLIST_IS_CREATED_SUCCESSFULLY,
                        PLAYLISTS_NAME));
    }

    @Test
    public void testCreatePlaylistInPlaylistsRepoUnsuccessfullyWhenPlaylistIsAlreadyExist() {
        PlaylistsRepo playlistsRepo = new PlaylistsRepo();
        playlistsRepo.createPlaylist(PLAYLISTS_NAME, USERNAME);

        assertEquals(playlistsRepo.createPlaylist(PLAYLISTS_NAME, USERNAME),
                String.format(PLAYLIST_ALREADY_EXIST, PLAYLISTS_NAME));
    }

    @Test
    public void testShowPlaylistInPlaylistsRepoSuccessfully() {
        PlaylistsRepo playlistsRepo = new PlaylistsRepo();
        playlistsRepo.createPlaylist(PLAYLISTS_NAME, USERNAME);
        Song song = new Song(SONGS_NAME, SONGS_PATH);
        playlistsRepo.addSongToPlaylist(PLAYLISTS_NAME, song, USERNAME);

        assertEquals(playlistsRepo.showPlaylist(PLAYLISTS_NAME, USERNAME),
                SONGS_NAME);
    }

    @Test
    public void testShowPlaylistInPlaylistsRepoSuccessfullyWhenThereIsNotExistSong() {
        PlaylistsRepo playlistsRepo = new PlaylistsRepo();
        playlistsRepo.createPlaylist(PLAYLISTS_NAME, USERNAME);

        assertEquals(playlistsRepo.showPlaylist(PLAYLISTS_NAME, USERNAME),
                PLAYLIST_IS_EMPTY);
    }

    @Test
    public void testShowPlaylistInPlaylistsRepoUnsuccessfullyWhenPlaylistIsNotExist() {
        PlaylistsRepo playlistsRepo = new PlaylistsRepo();

        assertEquals(playlistsRepo.showPlaylist(PLAYLISTS_NAME, USERNAME),
                String.format(PLAYLIST_IS_NOT_EXIST, PLAYLISTS_NAME));
    }
}