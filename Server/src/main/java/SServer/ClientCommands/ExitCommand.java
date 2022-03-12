package SServer.ClientCommands;


import SServer.World.Map;

public class ExitCommand extends Command{
    ServerCommands.Command command;
    private Map map;

    public ExitCommand(Map map){
        this.map = map;
    }
    @Override
    public boolean execute() throws Error {
        command = ServerCommands.Command.create("purge ",map);
        command.execute();
        return false;
    }

    @Override
    public String createResponse() {
        return null;
    }
}
