package spotify.command.songCommand;

import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.service.Player;
import spotify.service.SongsList;
import spotify.service.PlaylistsRepo;
import spotify.model.UsersData;

/**
 * This class performs the showing of all user's songs
 */
@Component
public class ShowPlaylistCommand implements SongCommand {

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

        return playlistsRepo.showPlaylist(playlistName, usersData.getUser().getEmail());
    }

    @Override
    public String getName() {
        return CommandName.SHOW_PLAYLIST.toString();
    }
}
