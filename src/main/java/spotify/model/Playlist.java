package spotify.model;

import java.util.HashMap;
import java.util.Map;

public class Playlist {
    private static final String SONG_ALREADY_EXIST = "%s already exist";
    private static final String SONG_IS_ADDED_SUCCESSFULLY = "%s is added successfully";
    private static final String SONG_JOINER = ", ";

    private String name;
    private Map<String, Song> songByNameMap;

    public Playlist(String name) {
        this.name = name;
        songByNameMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getSongs() {
        return String.join(SONG_JOINER, songByNameMap.keySet());
    }

    /**
     * This method add the song to playlist
     * @param song The song that will be added
     */
    public String addSong(Song song) {
        if (!songByNameMap.isEmpty() && songByNameMap.containsKey(song.getName())) {
            return String.format(SONG_ALREADY_EXIST, song.getName());
        }

        songByNameMap.put(song.getName(), song);
        return String.format(SONG_IS_ADDED_SUCCESSFULLY, song.getName());
    }
}
