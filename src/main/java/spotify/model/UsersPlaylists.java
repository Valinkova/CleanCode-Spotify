package spotify.model;

import java.util.HashSet;
import java.util.Set;

public class UsersPlaylists {
    private Set<Playlist> playlists;

    public UsersPlaylists() {
        playlists = new HashSet<>();
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * This method add playlist to repository
     * @param playlist This is the playlist of logged user
     */
    public void add(Playlist playlist) {
        playlists.add(playlist);
    }

    /**
     * This method checks whether the playlist already exist
     * @param playlistsName This is the name of playlist of logged user
     */
    public boolean isExists(String playlistsName) {
        return playlists.stream()
                .anyMatch(playlist -> playlist.getName()
                        .equals(playlistsName));
    }
}
