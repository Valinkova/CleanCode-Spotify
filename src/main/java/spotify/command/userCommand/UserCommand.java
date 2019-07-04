package spotify.command.userCommand;

import spotify.model.UsersData;
import spotify.service.UsersRepo;

import java.net.Socket;

public interface UserCommand {
    String USER_WAS_NOT_INSERTED_CORRECT_DATA = "You are not inserted correct data";

    /**
     * This method executes the commands related to the songs and return message with result
     * @param usersRepo This is repository that contains all registered users
     * @param user This is user
     * @param clientSocket This is socket with hostname of user and port
     * @param insertedCommandParameters These are the parameters for each command
     */
    String execute(UsersRepo usersRepo,
                   UsersData user,
                   Socket clientSocket,
                   String... insertedCommandParameters );

    String getName();
}
