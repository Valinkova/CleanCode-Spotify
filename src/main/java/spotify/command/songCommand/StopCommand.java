package spotify.command.songCommand;

import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.service.Player;
import spotify.service.SongsList;
import spotify.service.PlaylistsRepo;
import spotify.model.UsersData;

/**
 * This class performs the stopping of song
 */
@Component
public class StopCommand implements SongCommand {

    @Override
    public String execute(SongsList songsList,
                          UsersData usersData,
                          PlaylistsRepo playlistsRepo,
                          Player player,
                          String... insertedCommandParameters) {

        return player.stopSong();
    }

    @Override
    public String getName() {
        return CommandName.STOP.toString();
    }
}
