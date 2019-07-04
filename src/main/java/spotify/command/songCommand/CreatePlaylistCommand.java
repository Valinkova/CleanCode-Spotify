package spotify.command.songCommand;

import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;

/**
 * This class performs the creation of playlist
 */
@Component
public class CreatePlaylistCommand implements SongCommand {

    @Override
    public String execute(SongsList songsList,
                          UsersData usersData,
                          PlaylistsRepo playlistsRepo,
                          Player player,
                          String... insertedCommandParameters) {

        if (insertedCommandParameters.length != 1) {
            return USER_NOT_INSERT_CORRECT_DATA;
        }

        String playlistName = String.join(INSERTED_PARAMITERS_DELIMITER, insertedCommandParameters);

        return playlistsRepo.createPlaylist(playlistName, usersData.getUser().getEmail());
    }

    @Override
    public String getName() {
        return CommandName.CREATE_PLAYLIST.toString();
    }
}
