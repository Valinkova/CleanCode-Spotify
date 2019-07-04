package spotify.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spotify.model.Song;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongsList {
    private static final Logger log = LoggerFactory.getLogger(SongsList.class);
    private static final String PATH_TO_FOLDER_WITH_SONGS_IN_SPOTIFY = "resources/songsList/";

    private Map<String, Song> songByNameMap;
    private List<Song> currentlyListenedSongs;

    public SongsList() {
        songByNameMap = new HashMap<>();
        currentlyListenedSongs = new ArrayList<>();
        addSongs();
    }

    private void addSongs() {
        Path dir = Paths.get(PATH_TO_FOLDER_WITH_SONGS_IN_SPOTIFY);
        try (DirectoryStream<Path> filesPaths = Files.newDirectoryStream(dir)) {
            for (Path path : filesPaths) {
                String name = path.getFileName().toString();
                songByNameMap.put(name, new Song(name, path.toString()));
            }
        } catch (IOException | DirectoryIteratorException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void increasePlayedCount(String songsName) {
        songByNameMap.get(songsName).increasePlayedCount();
    }

    public Song getSong(String name) {
        if (songByNameMap.isEmpty() || !songByNameMap.containsKey(name)) {
            return null;
        }

        return songByNameMap.get(name);
    }

    /**
     * This method return the most popular current listening songs
     * @param numberOfTopSongs This is the number of top songs that will be return
     */
    public synchronized Set<Song> getTopCurrentlyListenSongs(int numberOfTopSongs) {
        currentlyListenedSongs.sort(Comparator.comparingInt(Song::getPlayedCount));

        return currentlyListenedSongs.stream()
                .limit(numberOfTopSongs)
                .collect(Collectors.toSet());
    }

    /**
     * This method add current listening song
     * @param songsName This is the name of the current listening song
     */
    public synchronized void addCurrentlyListenSong(String songsName) {
        currentlyListenedSongs.add(songByNameMap.get(songsName));
    }

    /**
     * This method return the songs that contains inserted words
     * @param words This is the inserted words from user
     */
    public List<Song> searchSongsByWords(String... words) {
        return songByNameMap.values()
                .stream()
                .filter(song -> song.containsWords(words))
                .collect(Collectors.toList());
    }
}
