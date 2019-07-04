package spotify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spotify.StateSaver;
import spotify.model.User;
import spotify.model.UsersData;

import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UsersRepo {
    private static final String USER_IS_NOT_LOGGED = "Yoy are not logged";
    private static final String OTHER_USER_IS_ALREADY_LOGGED = "other user is" +
            " already logged as %s";
    private static final String USER_IS_SUCCESSFULLY_DISCONNECTED = "%s is " +
            "successfully disconnected";
    private static final String USER_IS_ALREADY_REGISTERED = "%s is already " +
            "registered";
    private static final String USER_IS_SUCCESSFULLY_REGISTERED = "%s is " +
            "successfully registered";
    private static final String USER_IS_SUCCESSFULLY_LOGIN = "%s is " +
            "successfully login";
    private static final String PASSWORD_IS_WRONG = "password: %s is wrong";
    private static final String USER_IS_ALREADY_LOGGED = "%s is already logged";
    private static final String USER_IS_NOT_REGISTERED = "%s is not registered";
    private static final String FIRSTLY_LOG_OUT = "firstly log out";

    private Map<String, User> registeredUserByEmailMap;
    private Set<User> loggedUsers;
    private StateSaver stateSaver;

    @Autowired
    public UsersRepo() {
        registeredUserByEmailMap = new ConcurrentHashMap<>();
        loggedUsers = new HashSet<>();
        stateSaver = new StateSaver();
    }

    /**
     * This method add all registered users from file where they are saved
     * @param usersFromFile These are the extracted users from the file
     */
    public void addRegisteredUsers(Set<User> usersFromFile) {
        for (User user : usersFromFile) {
            registeredUserByEmailMap.put(user.getEmail(), user);
        }
    }

    public Set<User> getRegisteredUsers() {
        return new HashSet<>(registeredUserByEmailMap.values());
    }

    /**
     * This method register user and return message with result
     * @param insertedEmail This is email of user
     * @param insertedPassword This is password of user
     * @param usersData Here is the data for the registered user and client socket
     */
    public synchronized String registerUser(String insertedEmail,
                                            String insertedPassword,
                                            UsersData usersData) {
        if (usersData.getUser() != null) {
            return FIRSTLY_LOG_OUT;
        }

        if (registeredUserByEmailMap.containsKey(insertedEmail)) {
            return String.format(USER_IS_ALREADY_REGISTERED, insertedEmail);
        }

        registeredUserByEmailMap.put(insertedEmail, new User(insertedEmail,
                insertedPassword));
        stateSaver.usersToJson(this);

        return String.format(USER_IS_SUCCESSFULLY_REGISTERED, insertedEmail);
    }

    /**
     * This method login user and return message with result
     * @param insertedEmail This is email of user
     * @param insertedPassword This is password of user
     * @param usersData Here is the data for the registered user and client socket
     * @param clientSocket This is the socket of user
     */
    public synchronized String loginUser(String insertedEmail,
                                         String insertedPassword,
                                         UsersData usersData,
                                         Socket clientSocket) {

        String errorMessage = validationLoginUser(usersData, insertedEmail,
                insertedPassword);
        if (errorMessage != null) {
            return errorMessage;
        }

        usersData.setUser(registeredUserByEmailMap.get(insertedEmail));
        usersData.setClientSocket(clientSocket);
        loggedUsers.add(registeredUserByEmailMap.get(insertedEmail));

        return String.format(USER_IS_SUCCESSFULLY_LOGIN, insertedEmail);
    }

    /**
     * This method disconnect user and return message with result
     * @param loggedUsersData This is information of current user
     */
    public synchronized String disconnectUser(UsersData loggedUsersData) {
        boolean isLogged = loggedUsers.stream()
                .anyMatch(user -> loggedUsersData.getUser()
                        .getEmail()
                        .equals(user.getEmail()));

        if (!isLogged) {
            return USER_IS_NOT_LOGGED;
        }

        loggedUsers.remove(loggedUsersData.getUser());
        return String.format(USER_IS_SUCCESSFULLY_DISCONNECTED,
                loggedUsersData.getUser().getEmail());
    }

    private String validationLoginUser(UsersData usersData, String email,
                                       String password) {
        if (usersData.getUser() != null) {
            return String.format(USER_IS_ALREADY_LOGGED,
                    usersData.getUser().getEmail());
        }

        if (!registeredUserByEmailMap.containsKey(email)) {
            return String.format(USER_IS_NOT_REGISTERED, email);
        }

        boolean isLogged = loggedUsers.stream().anyMatch(user ->
                email.equals(user.getEmail()));

        if (isLogged) {
            return String.format(OTHER_USER_IS_ALREADY_LOGGED, email);
        }

        if (!password.equals(registeredUserByEmailMap.get(
                email).getPassword())) {
            return String.format(PASSWORD_IS_WRONG, password);
        }

        return null;
    }

}
