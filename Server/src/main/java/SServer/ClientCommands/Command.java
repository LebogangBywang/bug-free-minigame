package SServer.ClientCommands;

import SServer.World.Map;
import org.json.simple.parser.ParseException;

import java.util.Locale;

public abstract class Command {

    /**
     * Creates the command a client wants to do
     * @param userInput the input received from the client
     * @param map the specific thread that handles each client
     * @return a command that is available
     * @throws Error if command doesn't exist
     */
    public static Command create(String userInput, Map map) throws Error {
        Convertor creator = new Convertor(userInput);
        Boolean notLaunch = true;
        try {
            creator.convertData();
        } catch (ParseException e) {
            throw new Error("Unsupported command");
        }


        if (creator.getCommand().equals("launch")) {
            return new LaunchCommand(creator, map);
        }

        if (!map.getClientsNames().contains(creator.getName())) {
            throw new Error("Robot does not exist");
        }

        switch (creator.getCommand().toLowerCase(Locale.ROOT)) {
            case "exit":
                return new ExitCommand(map);
            case "turn":
                return new TurnCommand(creator, map);
            case "forward":
                return new ForwardCommand(creator, map);
            case "back":
                return new BackCommand(creator, map);
            case "fire":
                return new FireCommand(creator, map);
            case "mine":
                return new MineCommand(creator, map);
            case "state":
                return new StateCommand(creator, map);
            case "look":
                return new LookCommand(creator, map);
            case "repair":
                return new RepairCommand(creator, map);
            case "reload":
                return new ReloadCommand(creator, map);
            case "shutdown":
                return new ShutdownCommand(creator, map);
            default:
                throw new Error("Unsupported command");
        }
    }

    public abstract boolean execute() throws Error;

    /**
     * Returns the response needed for sending to the client
     * @return a json string
     */
    public abstract String createResponse();
}
