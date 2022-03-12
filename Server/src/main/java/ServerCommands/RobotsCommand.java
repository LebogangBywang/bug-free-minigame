package ServerCommands;

import SServer.ClientCommands.Robot;

import java.util.Vector;

public class RobotsCommand extends Command{

    public RobotsCommand() {
        super("robots");
    }

    @Override
    public boolean execute() {
//        Hashtable<String, ClientThreadHandler> clients = ServerMain.clients;
        Vector<Robot> robots = new Vector<>();

//        for (ClientThreadHandler client: clients.values()) if (!client.getIsRobot()) robots.add(client.getRobot());
        
        String robotsText = robots.size() > 0 ? "These are the robots in the world" +
                "\n==========================" : "There are currently no robots in the world";

        System.out.println(robotsText);

        for (Robot robot: robots) {
            System.out.println("NAME:      " + robot.getName() +
                    "\nPOSITION:  " + robot.getPosition().getX() + "," + robot.getPosition().getY() +
                    "\nDirection: " + robot.getDirection() +
                    "\nSHIELDS:   " + robot.getShields() +
                    "\nSHOTS:     " + robot.getShots() +
                    "\nSTATE:     " + robot.getStatus() +
                    "\n==========================");
        }
        return true;
    }
}
