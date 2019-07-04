package spotify.command.songCommand;

import spotify.service.Player;
import spotify.service.SongsList;
import spotify.service.PlaylistsRepo;
import spotify.model.UsersData;

public interface SongCommand {
    String INSERTED_PARAMITERS_DELIMITER = " ";
    String SONGS_DELIMITER = ", ";
    String USER_NOT_INSERT_CORRECT_DATA = "You are not inserted correct data";

    /**
     * This method executes the commands related to the songs and return message with result
     * @param songsList The song will be take from here
     * @param usersData This is data for user who execute command
     * @param playlistsRepo The song will be added/deleted from here
     * @param player From here the songs will be stop or play
     * @param insertedCommandParameters These are the parameters for each command
     */
    String execute(SongsList songsList,
                   UsersData usersData,
                   PlaylistsRepo playlistsRepo,
                   Player player,
                   String... insertedCommandParameters);

    String getName();
}
