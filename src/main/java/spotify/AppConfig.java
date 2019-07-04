package spotify;

import org.springframework.context.annotation.Bean;
import spotify.command.CommandName;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AppConfig {
    private static final String LOCALHOST = "localhost";

    @Bean({"userCommands"})
    public Set<String> getUserCommands() {
        Set<String> commandNames = new HashSet<>();
        commandNames.add(CommandName.DISCONNECT.toString());
        commandNames.add(CommandName.LOGIN.toString());
        commandNames.add(CommandName.REGISTER.toString());

        return commandNames;
    }

    @Bean({"songCommands"})
    public Set<String> getSongCommands() {
        EnumSet<CommandName> commands = EnumSet.allOf(CommandName.class);

        return commands.stream()
                .filter(commandName -> commandName != CommandName.DISCONNECT
                        && commandName != CommandName.LOGIN
                        && commandName != CommandName.REGISTER)
                .map(CommandName::toString)
                .collect(Collectors.toSet());
    }
}
