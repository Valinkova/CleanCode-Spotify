package spotify.model;

public class Song {
    private String name;
    private String path;
    private int playedCount;

    public Song(String name, String path) {
        this.name = name;
        this.path = path;
        playedCount = 0;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    /**
     * This method increase the number of songs played
     */
    public void increasePlayedCount() {
        playedCount++;
    }

    public int getPlayedCount() {
        return playedCount;
    }

    /**
     * This method checks whether the entered words are in the name of the song
     * @param words The inserted words from user
     */
    public boolean containsWords(String... words) {
        String toLowerCaseName = name.toLowerCase();
        for (String word : words) {
            if (!toLowerCaseName.contains(word.toLowerCase())) {
                return false;
            }
        }

        return true;
    }
}