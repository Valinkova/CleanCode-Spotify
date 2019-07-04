package spotify.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandNameTest {
    private static final String LOGIN = "login";

    @Test
    public void testConstructor() {
        assertEquals(CommandName.LOGIN.toString(), LOGIN);
    }
}
