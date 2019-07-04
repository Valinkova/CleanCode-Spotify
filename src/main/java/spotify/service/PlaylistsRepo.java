package spotify.service;

import org.springframework.stereotype.Service;
import spotify.StateSaver;
import spotify.model.Playlist;
import spotify.model.Song;
import spotify.model.UsersPlaylists;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlaylistsRepo {
    private static final String PLAYLIST_ALREADY_EXIST = "%s already exist";
    private static final String PLAYLIST_IS_CREATED_SUCCESSFULLY = "%s is " +
            "successfully created";
    private static final String PLAYLIST_NOT_EXIST = "%s is not exist";
    private static final String SONG_NOT_EXIST = "This song not exist";
    private static final String PLAYLIST_IS_EMPTY = "-- empty --";
    private static final String EMPTY_STRING = "";

    private Map<String, UsersPlaylists> playlistsByUsernameMap;

    public PlaylistsRepo() {
        playlistsByUsernameMap = new ConcurrentHashMap<>();
    }

    public void getPlaylistsFromJson(Map<String, UsersPlaylists> fromJson) {
        playlistsByUsernameMap = fromJson;
    }

    /**
     * This method show user's playlist with songs and return message with result
     * @param playlistName This is the name of the playlist
     * @param username This is the username of the current user
     */
    public String showPlaylist(String playlistName, String username) {
        if (!playlistsByUsernameMap.isEmpty() && playlistsByUsernameMap.containsKey(username)) {
            UsersPlaylists usersPlaylists =
                    playlistsByUsernameMap.get(username);
            for (Playlist playlist : usersPlaylists.getPlaylists()) {

                if (playlist.getName().equals(playlistName)) {
                    String result = playlist.getSongs();

                    if (result.equals(EMPTY_STRING)) {
                        return PLAYLIST_IS_EMPTY;
                    }
                    return result;
                }
            }
        }
        return String.format(PLAYLIST_NOT_EXIST, playlistName);
    }

    /**
     * This method create current user's playlist and return message with result
     * @param playlistName This is the name of the playlist
     * @param username This is the username of the current user
     */
    public String createPlaylist(String playlistName, String username) {
        Playlist newPlaylist = new Playlist(playlistName);
        StateSaver stateSaver = new StateSaver();
        UsersPlaylists usersPlaylists;

        if (playlistsByUsernameMap.containsKey(username)) {
            usersPlaylists =
                    playlistsByUsernameMap.get(username);

            if (!usersPlaylists.isExists(playlistName)) {
                return addSongToPlaylistFromFile(usersPlaylists, newPlaylist,
                        stateSaver,
                        username, playlistName);
            }
            return String.format(PLAYLIST_ALREADY_EXIST, playlistName);
        }

        usersPlaylists = new UsersPlaylists();
        playlistsByUsernameMap.put(username, usersPlaylists);
        return addSongToPlaylistFromFile(usersPlaylists, newPlaylist, stateSaver,
                username, playlistName);
    }

    /**
     * This method add song to playlist and return message with result
     * @param song This is the song that will be added
     * @param playlistName This is the name of the playlist
     * @param username This is the username of the current user
     */
    public String addSongToPlaylist(String playlistName, Song song,
                                    String username) {

        if (!playlistsByUsernameMap.isEmpty() && playlistsByUsernameMap.containsKey(username)) {
            UsersPlaylists playlists = playlistsByUsernameMap.get(username);

            for (Playlist playlist : playlists.getPlaylists()) {
                if (playlist.getName().equals(playlistName)) {
                    if (song == null) {
                        return SONG_NOT_EXIST;
                    }

                    String result = playlist.addSong(song);
                    StateSaver stateSaver = new StateSaver();
                    stateSaver.playlistToJson(username, playlists);

                    return result;
                }
            }
        }
        return String.format(PLAYLIST_NOT_EXIST, playlistName);
    }

    private String addSongToPlaylistFromFile(UsersPlaylists usersPlaylists,
                                             Playlist newPlaylist,
                                             StateSaver stateSaver, String username,
                                             String playlistName) {

        usersPlaylists.add(newPlaylist);
        stateSaver.playlistToJson(username, usersPlaylists);

        return String.format(PLAYLIST_IS_CREATED_SUCCESSFULLY,
                playlistName);
    }
}
