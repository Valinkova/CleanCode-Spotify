package spotify.command.songCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.service.Player;
import spotify.service.SongsList;
import spotify.service.PlaylistsRepo;
import spotify.model.Song;
import spotify.model.UsersData;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class performs the showing the most popular songs
 */
@Component
public class TopCommand implements SongCommand {
    private static final Logger log = LoggerFactory.getLogger(TopCommand.class);
    static final String
            THERE_IS_NO_SUCH_CURRENTLY_LISTEN_TOP_SONGS = "There is no such " +
            "currently listen top songs";

    @Override
    public String execute(SongsList songsList,
                          UsersData usersData,
                          PlaylistsRepo playlistRepo,
                          Player player,
                          String... insertedCommandParameters) {

        if (insertedCommandParameters.length != 1) {
            return USER_NOT_INSERT_CORRECT_DATA;
        }

        int songsNumber = getTopCurrentlyListenSongsNumber(insertedCommandParameters[0]);
        Set<Song> topSongs =
                songsList.getTopCurrentlyListenSongs(songsNumber);

        if (!topSongs.isEmpty()) {
            return topSongs.stream().map(Song::getName)
                    .collect(Collectors.joining(SONGS_DELIMITER));
        }

        return THERE_IS_NO_SUCH_CURRENTLY_LISTEN_TOP_SONGS;
    }

    @Override
    public String getName() {
        return CommandName.TOP.toString();
    }

    private int getTopCurrentlyListenSongsNumber(String insertedNumber) {
        try {
            return Integer.parseInt(insertedNumber);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }

        return 0;
    }
}
