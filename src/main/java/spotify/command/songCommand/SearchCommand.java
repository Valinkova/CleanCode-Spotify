package spotify.command.songCommand;

import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.model.Song;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class performs the searching of songs
 */
@Component
public class SearchCommand implements SongCommand {
    private static final String THERE_IS_NO_SUCH_SONG = "There is no such song";

    @Override
    public String execute(SongsList songsList,
                          UsersData usersData,
                          PlaylistsRepo playlistRepo,
                          Player player,
                          String... insertedCommandParameters) {

        List<Song> songsByWords = songsList.searchSongsByWords(insertedCommandParameters);

        if (songsByWords.size() != 0) {
            return songsByWords.stream()
                    .map(Song::getName)
                    .collect(Collectors.joining(SONGS_DELIMITER));
        }

        return THERE_IS_NO_SUCH_SONG;
    }

    @Override
    public String getName() {
        return CommandName.SEARCH.toString();
    }
}
