package spotify.model;

import java.net.Socket;

public class UsersData {
    private Socket clientSocket;
    private User user;

    public UsersData() {
        clientSocket = null;
        user = null;
    }

    public UsersData(Socket clientSocket, User user) {
        this.clientSocket = clientSocket;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

}
