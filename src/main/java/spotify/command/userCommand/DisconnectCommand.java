package spotify.command.userCommand;

import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.model.UsersData;
import spotify.service.UsersRepo;

import java.net.Socket;

/**
 * This class performs the disconnecting of user
 */
@Component
public class DisconnectCommand implements UserCommand {

    @Override
    public String execute(UsersRepo usersRepo,
                          UsersData user,
                          Socket clientSocket,
                          String... insertedCommandParameters) {

        if (insertedCommandParameters.length != 0) {
            return USER_WAS_NOT_INSERTED_CORRECT_DATA;
        }

        return usersRepo.disconnectUser(user);
    }

    @Override
    public String getName() {
        return CommandName.DISCONNECT.toString();
    }
}
