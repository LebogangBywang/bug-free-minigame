package SServer.Network;

import ServerCommands.Command;
import SServer.World.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandListener implements Runnable{
    Command command;
    private Map map;

    public CommandListener(Map map){
        this.map = map;
    }

    /**
     * The input thread that runs for the server
     * Overrides the default run on runnable
     */
    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean shouldContinue = true;

        while (shouldContinue) {
            String cmd = "";
            try {
                cmd = br.readLine();
                if (cmd == null) continue;
                command = Command.create(cmd, this.map);
                shouldContinue = command.execute();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println("Unsupported command: " + cmd);
            }
        }
        System.exit(-1);
    }

    public String getName(){
        return command.getName();
    }

}
