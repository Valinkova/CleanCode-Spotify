package spotify.service;

import org.junit.Test;
import org.mockito.Mock;
import spotify.model.UsersData;

import java.net.Socket;

import static org.junit.Assert.*;

public class UsersRepoTest {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String SECOND_PASSWORD = "second password";
    private static final String PASSWORD_IS_WRONG = "password: %s is wrong";
    private static final String USER_IS_ALREADY_LOGGED = "%s is already logged";
    private static final String USER_IS_NOT_REGISTERED = "%s is not registered";
    private static final String USER_IS_NOT_LOGGED = "Yoy are not logged";
    private static final String USER_IS_SUCCESSFULLY_DISCONNECTED = "%s is successfully disconnected";
    private static final String SECOND_EMAIL = "second email";
    private static final String USER_IS_SUCCESSFULLY_REGISTERED = "%s is successfully registered";
    private static final String USER_IS_ALREADY_REGISTERED = "%s is already registered";

    @Mock
    Socket clientSocket;

    @Test
    public void testLoginUserUnsuccessfullyWheTheUserIsNotRegistered() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();

        assertEquals(usersRepo.loginUser(EMAIL, PASSWORD, usersData, clientSocket),
                String.format(USER_IS_NOT_REGISTERED, EMAIL));
    }

    @Test
    public void testLoginUserUnsuccessfullyWhenTheUserIsAlreadyLogged() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();
        usersRepo.registerUser(EMAIL, PASSWORD, usersData);
        usersRepo.loginUser(EMAIL, PASSWORD, usersData, clientSocket);

        assertEquals(usersRepo.loginUser(EMAIL, PASSWORD, usersData, clientSocket),
                String.format(USER_IS_ALREADY_LOGGED, EMAIL));
    }

    @Test
    public void testLoginUserUnsuccessfullyWhenPasswordIsWrong() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();
        usersRepo.registerUser(EMAIL, PASSWORD, usersData);

        assertEquals(usersRepo.loginUser(EMAIL, SECOND_PASSWORD, usersData, clientSocket),
                String.format(PASSWORD_IS_WRONG, SECOND_PASSWORD));
    }

    @Test
    public void testDisconnectUserSuccessfully() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();
        usersRepo.registerUser(EMAIL, PASSWORD, usersData);
        usersRepo.loginUser(EMAIL, PASSWORD, usersData, clientSocket);

        assertEquals(usersRepo.disconnectUser(usersData),
                String.format(USER_IS_SUCCESSFULLY_DISCONNECTED, EMAIL));
    }

    @Test
    public void testDisconnectUserUnsuccessfullyWhenTheUserIsNotLogged() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();
        usersRepo.registerUser(EMAIL, PASSWORD, usersData);

        assertEquals(usersRepo.disconnectUser(usersData), USER_IS_NOT_LOGGED);
    }

    @Test
    public void testRegisterUserSuccessfully() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();

        assertEquals(usersRepo.registerUser(EMAIL, PASSWORD, usersData),
                String.format(USER_IS_SUCCESSFULLY_REGISTERED, EMAIL));
    }

    @Test
    public void testRegisterUserUnsuccessfully() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();
        usersRepo.registerUser(EMAIL, PASSWORD, usersData);

        assertEquals(usersRepo.registerUser(EMAIL, PASSWORD, usersData),
                String.format(USER_IS_ALREADY_REGISTERED, EMAIL));
    }

    @Test
    public void testRegisterUserSuccessfullyWhenEmailIsNotSame() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();
        usersRepo.registerUser(EMAIL, PASSWORD, usersData);

        assertEquals(usersRepo.registerUser(SECOND_EMAIL, PASSWORD, usersData),
                String.format(USER_IS_SUCCESSFULLY_REGISTERED, SECOND_EMAIL));
    }

    @Test
    public void testRegisterUserUnsuccessfullyWhenPasswordIsNotSame() {
        UsersRepo usersRepo = new UsersRepo();
        UsersData usersData = new UsersData();
        usersRepo.registerUser(EMAIL, PASSWORD, usersData);

        assertEquals(usersRepo.registerUser(EMAIL, SECOND_PASSWORD, usersData),
                String.format(USER_IS_ALREADY_REGISTERED, EMAIL));
    }
}