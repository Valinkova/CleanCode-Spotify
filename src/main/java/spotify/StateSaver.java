package spotify;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spotify.model.User;
import spotify.model.UsersPlaylists;
import spotify.service.PlaylistsRepo;
import spotify.service.UsersRepo;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class StateSaver {
    private static final Logger log = LoggerFactory.getLogger(StateSaver.class);
    private static final String REGISTERED_USERS_FILE = "registeredUsers.json";
    private static final String JSON_FORMAT = ".json";
    private static final String PLAYLISTS_FOLDER = "resources/playlists/";
    private static final String POINT_DELIMITER = ".";

    private Gson gson;
    private Map<String, UsersPlaylists> playlistByNameMap;

    public StateSaver() {
        gson = new GsonBuilder().setPrettyPrinting().create(); //bean
        playlistByNameMap = new HashMap<>();
    }

    synchronized public void usersToJson(UsersRepo usersRepo) {
        try (Writer writer = new FileWriter(REGISTERED_USERS_FILE)) {
            gson.toJson(usersRepo.getRegisteredUsers(), writer);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    synchronized void usersFromJson(UsersRepo usersRepo) {
        File registeredUsers = new File(REGISTERED_USERS_FILE);
        if (registeredUsers.exists()) {
            try (Reader reader = new FileReader(registeredUsers)) {
                Type usersSetType = new TypeToken<Set<User>>() {
                }.getType();

                Set<User> usersFromFile = gson.fromJson(reader, usersSetType);
                usersRepo.addRegisteredUsers(usersFromFile);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    synchronized public void playlistToJson(String username, UsersPlaylists playlists) {
        String filePath = PLAYLISTS_FOLDER + username + JSON_FORMAT;
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(playlists, writer);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    synchronized void playlistFromJson(PlaylistsRepo playlistsRepo) {
        File playlistsFolder = new File(PLAYLISTS_FOLDER);

        if (playlistsFolder.exists()) {
            if (playlistsFolder.length() != 0) {
                File[] files = playlistsFolder.listFiles();

                if (files == null) {
                    return;
                }
                savePlaylistsFromJson(files);
            }
            playlistsRepo.getPlaylistsFromJson(playlistByNameMap);
        }
    }

    private void savePlaylistsFromJson(File[] files) {
        String fileName;
        for (File fileInFolder : files) {
            fileName = fileInFolder.getName();
            String filesPath = PLAYLISTS_FOLDER + fileName;
            File file = new File(filesPath);

            try (Reader reader = new FileReader(file)) {
                UsersPlaylists playlists = gson.fromJson(reader, UsersPlaylists.class);
                int pointIndex = fileName.indexOf(POINT_DELIMITER);
                playlistByNameMap.put(fileName.substring(0, pointIndex), playlists);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
