package spotify.command.userCommand;

import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.model.UsersData;
import spotify.service.UsersRepo;

import java.net.Socket;

/**
 * This class performs the logging of user
 */
@Component
public class LoginCommand implements UserCommand {

    @Override
    public String execute(UsersRepo usersRepo,
                          UsersData user,
                          Socket client,
                          String... insertedCommandParameters) {

        if (insertedCommandParameters.length != 2) {
            return USER_WAS_NOT_INSERTED_CORRECT_DATA;
        }

        return usersRepo.loginUser(insertedCommandParameters[0], insertedCommandParameters[1], user, client);
    }

    @Override
    public String getName() {
        return CommandName.LOGIN.toString();
    }
}
