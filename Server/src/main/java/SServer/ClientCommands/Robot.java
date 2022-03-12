package SServer.ClientCommands;


import ServerCommands.ServerArguments;
import SServer.World.Direction;
import SServer.World.Map;
import SServer.World.Mine;
import SServer.World.Obstacle;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Robot {
    private String name;
    private Position position;
    private Direction currentDir;
    private int shots;
    private String status;
    private int shields;
    private int distance;
    private int reloadTime;
    private int repairTime;
    private int setMineTime;
    private int visibility;
    private Position minePos;
    private Map map;
    private int MAX_WIDTH;
    private int MAX_HEIGHT;
    private int MAX_SHIELDS;
    private int MAX_SHOTS;
    private Response response = Response.DONE;
    private ServerArguments serverArguments;

    public Robot(String name, int shields, int shots, Map map) throws Error {
        this.serverArguments = map.getServerArguments();
        this.name = name;
        this.map = map; // CALL MAP  DIRECTLY
        this.MAX_WIDTH = map.getInitialize().getMaxWidth();
        this.MAX_HEIGHT = map.getInitialize().getMaxHeight();
        makePosition();
        this.currentDir = Direction.getRandomDirection();
        this.shots = shots;
        this.MAX_SHOTS = shots;
        this.MAX_SHIELDS = shields;
        this.shields = shields;
        this.status = "NORMAL";
        setStats();

    }

    /**
     * Sets the robots stats based on server configs
     */
    private void setStats() {
        this.reloadTime = map.getInitialize().getReloadTime();
        this.repairTime = map.getInitialize().getRepairTime();
        this.setMineTime = map.getInitialize().getSetMineTime();
        this.visibility = map.getInitialize().getVisibility();
        //y = mx + c to calculate the distance the robot can shoot from the number of shots it has
        this.distance = (-1 * this.shots) + 6;
    }

    /**
     * Creates a random spawn position
     *
     * @return Position position=
     */
    private void makePosition() throws Error {
        List<Position> pos = map.getPossibleLocations();
        if (!serverArguments.getTesting()) {
            int n =0;
            while (n<pos.size()) {
                Random rand = new Random();
                int index = rand.nextInt(pos.size());
                Position randomElement = pos.get(index);
                if (!notAvailable(randomElement, this.getName())) {
                    this.position = randomElement;
                    break;
                }
                n++;
            }
        } else {
            for (Position Pos : pos) {
                if (notAvailable(Pos, this.getName())) {
                    continue;
                }
                this.position = Pos;
                break;
            }
        }

        if (map.getClientsNames().size() == pos.size()) {
            throw new Error("No more space in this world");
        }
    }

    public boolean notAvailable(Position position, String name) {
        for (Robot cl : map.getClients().values())
            if (!cl.getName().equals(name) &&
                    cl.getPosition().equals(position)) return true;
        return false;
    }

    /**
     * Checks if position is blocked when generating the random spawn
     *
     * @param position the position we want to spawn at
     * @return boolean false if position is available
     */
    public boolean blocksPosition(Position position) {
        int topLeftX = this.getPosition().getX();
        //int bottomRightX = this.bottomRight.getX();
        int topLeftY = this.getPosition().getY();
        //int bottomRightY = this.bottomRight.getY();
        boolean xOnObstacle = topLeftX == position.getX();
        boolean yOnObstacle = topLeftY == position.getY();
        return xOnObstacle && yOnObstacle;
    }

//    private boolean positionBlocked(Position position) {
//        ArrayList<Object> obstacles = map.getObstacles();
//        for (Object obs : obstacles) {
//            switch (obs.getClass().getName().toLowerCase()) {
//                case "world.pits":
//                case "world.obstacle":
//                    if (((Obstacle) obs).blocksPosition(position)) {
//                        return true;
//                    }
//            }
//        }
//        return false;
//    }

    /**
     * @return this robot's position
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * @param shots the new number of shots
     */
    public void setShots(int shots) {
        this.shots = shots;
    }

    /**
     * @return this robots shots
     */
    /**
     * @return this robots shots
     */
    public int getShots() {
        return this.shots;
    }

    /**
     * @return the distance the robot can shoot
     */
    public int getDistance() {
        return this.distance;
    }

    /**
     * @return this robots status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @return this robot's direction
     */
    public Direction getDirection() {
        return this.currentDir;
    }

    public void setDirection(Direction direction) {
        currentDir = direction;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public int getShields() {
        return this.shields;
    }

    public String getName() {
        return this.name;
    }

    public int getReloadTime() {
        return this.reloadTime;
    }

    public int getRepairTime() {
        return this.repairTime;
    }

    public int getMineTime() {
        return this.setMineTime;
    }

    public int getVisibility() {
        return this.visibility;
    }

    public Response getResponse() {
        return response;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMAX_SHIELDS() {
        return MAX_SHIELDS;
    }

    public int getMAX_SHOTS() {
        return MAX_SHOTS;
    }

    /**
     * @return this updated robot position based on orientation
     */
    public boolean updatePosition(int nrSteps) {
        int newX = this.getPosition().getX();
        int newY = this.getPosition().getY();

        if (Direction.NORTH.equals(this.currentDir))
            newY += nrSteps;
        else if (Direction.SOUTH.equals(this.currentDir))
            newY -= nrSteps;
        else if (Direction.EAST.equals(this.currentDir))
            newX += nrSteps;
        else
            newX -= nrSteps;

        Position newPosition = new Position(newX, newY);


        if (checkPosition(newPosition, this.position)) {
            if (this.getResponse().equals(Response.MINE)) {
                this.position = minePos;
                return true;
            }
            this.position = newPosition;
            return true;
        }

        return false;
    }

    /**
     * Checks the position the user wants to move to
     *
     * @param newPosition the position we want to move to
     * @param currentPos  the current position of the user
     * @return false if the path or position is blocked
     */
    private boolean checkPosition(Position newPosition, Position currentPos) {
        if (!newPosition.isIn(new Position(-MAX_WIDTH / 2, MAX_HEIGHT / 2),
                new Position(MAX_WIDTH / 2, -MAX_HEIGHT / 2))) {

            setResponse(Response.OBSTRUCTED);
            return false;
        }

        for (Object obs : map.getObstacles()) {
            String className = obs.getClass().getName().toLowerCase();
            if (className.equalsIgnoreCase("world.obstacle")) {
                if (((Obstacle) obs).blocksPath(currentPos, newPosition)) {
                    setResponse(Response.OBSTRUCTED);
                    return false;
                }
//            } else if (className.equalsIgnoreCase("network.clientthreadhandler")) {
//                if ( (((Obstacle) obs).blocksPath(currentPos,newPosition,this.getName()))) {
//                    setResponse(Response.OBSTRUCTED);
//                    return false;
//                }
            } else if (className.equalsIgnoreCase("world.pits")) {
                if (((Obstacle) obs).blocksPath(currentPos, newPosition)) {
                    setResponse(Response.FELL);
                    return true;
                }
            } else if (className.equalsIgnoreCase("world.mine")) {
                if (((Mine) obs).blocksPath(currentPos, newPosition)) {
                    minePos = new Position(((Mine) obs).getMineX(), ((Mine) obs).getMineY());
                    map.removeMine(obs);
                    setResponse(Response.MINE);
                    return true;
                }
            }
        }
        return true;
    }

    public void setResponse(Response res) {
        response = res;
    }

    public ArrayList<Object> getObstacles() {
        return map.getObstacles();
    }

    public void addObstacle(Mine mine) {
        map.addMines(mine);
    }

    public void setMineData(JSONArray mineData) {
        map.setMineData(mineData);
    }

}
