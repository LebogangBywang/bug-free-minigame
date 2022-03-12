import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ServerMainTest {
    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setIn(standardIn);
    }

//    @Test
//    public void testServerConfigs() {
//        String[] args = new String[]{"-p", "4096", "-o", "1,1", "-s", "2x2"};
//        ServerArguments serverArgs = new ServerArguments(args);
//        ServerMain.initialize("testConfig.properties",serverArgs);
//
//        assertEquals(ServerMain.getReloadTime(),3);
//        assertEquals(ServerMain.getRepairTime(),3);
//        assertEquals(ServerMain.getShields(),5);
//        assertEquals(ServerMain.getShots(),5);
//        assertEquals(ServerMain.getSetMineTime(),2);
//        assertEquals(ServerMain.getMaxWidth(),2);
//        assertEquals(ServerMain.getMaxHeight(),2);
//        assertEquals(ServerMain.getVisibility(),20);
//    }
}
