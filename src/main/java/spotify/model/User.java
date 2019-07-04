package spotify.model;

import java.util.Objects;

public class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    /**
     * This method checks whether this user have equal password and email with other user
     * @param otherUser This is the second user
     */
    @Override
    public boolean equals(Object otherUser) {
        if (this == otherUser) {
            return true;
        }

        if (otherUser == null || getClass() != otherUser.getClass()) {
            return false;
        }

        User user = (User) otherUser;

        return email.equals(user.email) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
