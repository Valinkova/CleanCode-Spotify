package spotify.command.userCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spotify.model.UsersData;
import spotify.service.UsersRepo;

import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spotify.command.songCommand.SongCommand.INSERTED_PARAMITERS_DELIMITER;

@Service
public class UserCommandExecutor {
    private static final String COMMAND_NOT_FOUND = "%s command not found";
    private Map<String, UserCommand> commandByNameMap;

    @Autowired
    public UserCommandExecutor(List<UserCommand> commands) {
        commandByNameMap = new HashMap<>();

        for (UserCommand command : commands) {
            commandByNameMap.put(command.getName(), command);
        }
    }

    /**
     * This method executes the commands related to the songs and return message with result
     * @param usersRepo In repository add and remove users
     * @param user This is user with email and password
     * @param clientSocket This is socket with hostname of user and port
     * @param input This is the inserted command with parameters from user
     */
    public String executeCommand(UsersRepo usersRepo,
                                 UsersData user,
                                 Socket clientSocket,
                                 String input) {

        String[] parameters = input.split(INSERTED_PARAMITERS_DELIMITER);
        String commandName = parameters[0];

        if (commandByNameMap.containsKey(commandName)) {
            return commandByNameMap.get(commandName)
                    .execute(usersRepo, user, clientSocket, Arrays.copyOfRange(parameters, 1, parameters.length));
        }

        return String.format(COMMAND_NOT_FOUND, commandName);
    }
}
