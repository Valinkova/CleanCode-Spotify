package spotify.command;

public enum CommandName {
    REGISTER("register"), LOGIN("login"),
    DISCONNECT("disconnect"), SEARCH("search"), PLAY("play"),
    STOP("stop"), CREATE_PLAYLIST("create-playlist"),
    ADD_SONG_TO_PLAYLIST("add-song-to"), SHOW_PLAYLIST("show-playlist"), TOP("top");

    private String value;

    CommandName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
