package ServerCommands;

import SServer.World.Map;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class Command {
    private final String name;
    private String arg;

    public Command(String name) {
        this.name = name.trim().toLowerCase();
        this.arg = "";
    }

    public Command(String name, String arg) {
        this.name = name;
        this.arg = arg;
    }

    public abstract boolean execute();

    public String getName() {
        return name;
    }

    /**
     * Creates a command for the specific command we send in
     *
     * @param cmd the command we want to create
     * @return the specific command if it exists
     */
    public static Command create(String cmd, Map map) {
        String[] args = cmd.toLowerCase().trim().split(" ");
        switch (args[0]) {
            case "robots":
                return new RobotsCommand();
            case "quit":
                return new CloseServer();
            case "purge":
                if (args.length > 1)
                    return new PurgeClient(args[1]);
            case "save":
                String world;
                if (args.length != 2) {
                    world = getWorldName();
                } else {
                    world = args[1];
                }
                return new SaveCommand(world, map, false);
            case "restore":
                return new RestoreCommand(args[1], map);
            default:
                throw new IllegalArgumentException();
        }
    }

    public String getArg() {
        return arg;
    }

    static String getWorldName() {

        String world = "";
        try {
            while (true) {
                System.out.println("Please enter world name!");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                world = br.readLine();
                if (world.length() == 0) {
                    continue;
                }
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return world;
    }
}
