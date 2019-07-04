package spotify.command.userCommand;

import org.springframework.stereotype.Component;
import spotify.command.CommandName;
import spotify.model.UsersData;
import spotify.service.UsersRepo;

import java.net.Socket;

/**
 * This class performs the registering of user
 */
@Component
public class RegisterCommand implements UserCommand {
    private static final String LETTERS = "[a-zA-Z]+";

    @Override
    public String execute(UsersRepo usersRepo,
                          UsersData user,
                          Socket clientSocket,
                          String... insertedCommandParameters) {

        if (insertedCommandParameters.length < 2 || !insertedCommandParameters[0].matches(LETTERS)) {
            return USER_WAS_NOT_INSERTED_CORRECT_DATA;
        }

        return usersRepo.registerUser(insertedCommandParameters[0], insertedCommandParameters[1], user);
    }

    @Override
    public String getName() {
        return CommandName.REGISTER.toString();
    }
}
