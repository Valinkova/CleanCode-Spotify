package spotify.command.songCommand;

import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;

import java.util.Arrays;

/**
 * This class performs the addition of songs
 */
@Component
public class AddSongCommand implements SongCommand {
    static final int FROM = 1;

    @Override
    public String execute(SongsList songsList,
                          UsersData usersData,
                          PlaylistsRepo playlistsRepo,
                          Player player,
                          String... insertedCommandParameters) {

        if (insertedCommandParameters.length < 2) {
            return USER_NOT_INSERT_CORRECT_DATA;
        }

        String songName = String.join(INSERTED_PARAMITERS_DELIMITER,
                Arrays.copyOfRange(insertedCommandParameters,
                        FROM,
                        insertedCommandParameters.length));

        return playlistsRepo.addSongToPlaylist(insertedCommandParameters[0],
                songsList.getSong(songName), usersData.getUser().getEmail());
    }

    @Override
    public String getName() {
        return CommandName.ADD_SONG_TO_PLAYLIST.toString();
    }
}
