package spotify.command.songCommand;

import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;


/**
 * This class performs playing the song
 */
@Component
public class PlayCommand implements SongCommand {
    private static final String YOU_WERE_LISTENING_THIS_SONG = "You were listening %s";

    @Override
    public String execute(SongsList songsList,
                          UsersData usersData,
                          PlaylistsRepo playlistsRepo,
                          Player player,
                          String... insertedCommandParameters) {

        if (insertedCommandParameters.length == 0) {
            return USER_NOT_INSERT_CORRECT_DATA;
        }

        String songsName = String.join(INSERTED_PARAMITERS_DELIMITER, insertedCommandParameters);
        player.setSongPath(songsList.getSong(songsName).getPath());
        player.setClientSocket(usersData.getClientSocket());
        songsList.addCurrentlyListenSong(songsName);
        songsList.increasePlayedCount(songsName);
        player.run();

        return YOU_WERE_LISTENING_THIS_SONG;
    }

    @Override
    public String getName() {
        return CommandName.PLAY.toString();
    }
}
