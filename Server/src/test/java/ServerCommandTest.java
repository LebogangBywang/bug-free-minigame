import ServerCommands.Command;
import ServerCommands.ServerArguments;
import SServer.World.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServerCommandTest {
    Map map;

    @BeforeEach
    void initialise(){
        String[] args = new String[]{ "-s", "2",};
        ServerArguments serverArguments = new ServerArguments(args);
        map = new Map(serverArguments);
    }

    @Test
    void createServerPurgeCommand() {
        Command command = Command.create("purge test",map);
        assertEquals("purge",command.getName());
        assertEquals("test",command.getArg());
    }

    @Test
    void createServerCloseCommand() {
        Command command = Command.create("quit",map);
        assertEquals("quit",command.getName());
        assertEquals("",command.getArg());
    }

    @Test
    void notCommand() {
        assertThrows(IllegalArgumentException.class, () -> { Command.create("ffff",map); });
    }
}
