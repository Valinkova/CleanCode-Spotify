package spotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppMain implements ApplicationRunner {

    @Autowired private SpotifyServer spotifyServer;

    public static void main(String[] args) {
        SpringApplication.run(SpotifyServer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        spotifyServer.run();
    }
}
