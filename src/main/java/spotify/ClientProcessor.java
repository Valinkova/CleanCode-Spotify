package spotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import spotify.command.songCommand.SongCommandExecutor;
import spotify.command.userCommand.UserCommandExecutor;
import spotify.model.UsersData;
import spotify.service.Player;
import spotify.service.PlaylistsRepo;
import spotify.service.SongsList;
import spotify.service.UsersRepo;

import java.io.*;
import java.net.Socket;
import java.util.Set;

@Import(AppConfig.class)
@Service
public class ClientProcessor extends Thread {
    private static final Logger log =
            LoggerFactory.getLogger(ClientProcessor.class);
    private static final String FIRSTLY_YOU_SHOULD_REGISTER_OR_LOGIN =
            "firstly you should register or login";
    private static final String NEW_LINE = "\n";
    private static final String USER_IS_NOT_INSERTED_CORRECT_COMMAND = "You " +
            "are not inserted correct command";

    private UsersData usersData;
    private SongsList songsList;
    private UsersRepo usersRepo;
    private Socket clientSocket;
    private Player player;
    private PlaylistsRepo playlistsRepo;
    private UserCommandExecutor userCommandExecutor;
    private SongCommandExecutor songCommandExecutor;
    private Set<String> userCommands;
    private Set<String> songCommands;

    @Autowired
    public ClientProcessor(PlaylistsRepo playlistsRepo,
                           ClientSocketFactory clientSocketFactory,
                           SongsList songsList,
                           UsersRepo usersRepo,
                           UserCommandExecutor userCommandExecutor,
                           SongCommandExecutor songCommandExecutor,
                           Set<String> userCommands,
                           Set<String> songCommands) throws IOException {

        this.songsList = songsList;
        this.clientSocket = clientSocketFactory.getSocket();
        this.usersRepo = usersRepo;
        player = new Player();
        usersData = new UsersData();
        this.playlistsRepo = playlistsRepo;
        this.userCommandExecutor = userCommandExecutor;
        this.songCommandExecutor = songCommandExecutor;
        this.userCommands = userCommands;
        this.songCommands = songCommands;
    }

    private boolean isUsersCommand(String input) {
        return userCommands.contains(input);
    }

    private boolean isSongCommand(String input) {
        return songCommands.contains(input);
    }

    /**
     * This method run the process of execute the inserted command
     */
    @Override
    public void run() {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter =
                     new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            while (!clientSocket.isClosed() && clientSocket.isConnected()) {
                String readedLine = bufferedReader.readLine();

                if (readedLine == null) {
                    clientSocket.close();
                    return;
                }

                String result = getCommandResult(readedLine);
                bufferedWriter.write(result + NEW_LINE);
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getCommandResult(String readedLine) {
        if (isUsersCommand(readedLine)) {
            return userCommandExecutor.executeCommand(usersRepo, usersData,
                    clientSocket, readedLine);
        } else if (isSongCommand(readedLine)) {
            if (usersData.getUser() != null) {
                return songCommandExecutor.executeCommand(readedLine, songsList,
                        usersData, playlistsRepo, player);
            } else {
                return FIRSTLY_YOU_SHOULD_REGISTER_OR_LOGIN;
            }
        }
        return USER_IS_NOT_INSERTED_CORRECT_COMMAND;
    }
}
