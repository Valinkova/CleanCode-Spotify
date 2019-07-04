package spotify.command.songCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spotify.service.Player;
import spotify.service.SongsList;
import spotify.service.PlaylistsRepo;
import spotify.model.UsersData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spotify.command.songCommand.AddSongCommand.FROM;
import static spotify.command.songCommand.SongCommand.INSERTED_PARAMITERS_DELIMITER;

@Service
public class SongCommandExecutor {
    private final static String COMMAND_NOT_FOUND = "%s command not found";
    private Map<String, SongCommand> commandByNameMap;

    @Autowired
    public SongCommandExecutor(List<SongCommand> commands) {
        commandByNameMap = new HashMap<>();

        for (SongCommand command : commands) {
            commandByNameMap.put(command.getName(), command);
        }
    }

    /**
     * This method executes the commands related to the songs and return message with result
     * @param input This is the inserted command with parameters from user
     * @param playlistsRepo The song will be added/deleted from here
     * @param songsList The song will be take from here
     * @param usersData This is data for user who execute command
     * @param player From here the songs will be stop or play
     */
    public String executeCommand(String input, SongsList songsList,
                                 UsersData usersData,
                                 PlaylistsRepo playlistsRepo, Player player) {

        String[] parameters = input.split(INSERTED_PARAMITERS_DELIMITER);
        String commandName = parameters[0];

        if (commandByNameMap.containsKey(commandName)) {

            return commandByNameMap.get(commandName)
                    .execute(songsList, usersData,
                            playlistsRepo, player,
                            Arrays.copyOfRange(parameters,
                                    FROM, parameters.length));
        }

        return String.format(COMMAND_NOT_FOUND, commandName);
    }
}

