package SServer.World;

import DataBase.DbConnect;
import SServer.ClientCommands.Position;
import SServer.ClientCommands.Robot;
import ServerCommands.ServerArguments;

import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Map {
    private ArrayList<Object> obstacles;
    private Position TOP_LEFT;
    private Position BOTTOM_RIGHT;
    private ServerArguments serverArguments;
    int maxWidth;
    int maxHeight;
    JSONArray obsList = new JSONArray();
    JSONArray mineData = new JSONArray();
    JSONArray pitList = new JSONArray();
    JSONObject Objects = new JSONObject();
    private Initialize init;
    private final List<Position> possibleLocations = new ArrayList<>();
    private JSONObject sizeList = new JSONObject();
    private static Hashtable<String, Robot> clients = new Hashtable<>();
    private static List<String> clientsNames = new ArrayList<>();
    private DbConnect dbConnect;

    public Map(ServerArguments serverArguments) {
        init = new Initialize("config.properties", serverArguments);
        this.serverArguments = serverArguments;
        this.maxWidth = init.getMaxWidth();
        this.maxHeight = init.getMaxHeight();
        obstacles = new ArrayList<>();
        TOP_LEFT = new Position(-maxWidth / 2, maxHeight / 2);
        BOTTOM_RIGHT = new Position(maxWidth / 2, -maxHeight / 2);
        this.handleObstacles();
        this.handlePits();
        this.setPositions();
        dbConnect = new DbConnect();
    }

    /**
     * Creates obstacles using obstacle class
     * obstacle parameters is:
     * first topLeft of obstacle
     * second bottomRight of obstacle
     */
    private void generateObstacles() {
        obstacles.add(new Obstacle(new Position(-2, 2), new Position(2, -2)));
        obstacles.add(new Obstacle(new Position(-12, 17), new Position(-4, 12)));
        obstacles.add(new Obstacle(new Position(4, 17), new Position(12, 12)));
        obstacles.add(new Obstacle(new Position(-12, 11), new Position(-8, 4)));
        obstacles.add(new Obstacle(new Position(8, 11), new Position(12, 4)));
        obstacles.add(new Obstacle(new Position(-12, -12), new Position(-4, -17)));
        obstacles.add(new Obstacle(new Position(-12, -4), new Position(-8, -11)));
        obstacles.add(new Obstacle(new Position(4, -12), new Position(12, -17)));
        obstacles.add(new Obstacle(new Position(8, -4), new Position(12, -11)));
    }

    private void generatePits() {
        JSONObject pits = new JSONObject();
        JSONObject pitsData = new JSONObject();

        obstacles.add(new Pits(new Position(5, 30), new Position(11, 24)));
        pits.put("position", "" + 5 + "," + 30);
        pits.put("size", "" + 1 + "," + 1);
        pitsData.put((new Pits(new Position(5, 30), new Position(11, 24)).topLeft), pits);

        obstacles.add(new Pits(new Position(-11, -24), new Position(-5, -30)));
        pits.put("position", "" + -11 + "," + -24);
        pits.put("size", "" + 1 + "," + 1);
        pitsData.put(new Pits(new Position(-11, -24), new Position(-5, -30)).toString(), pits);

        this.pitList.add(pitsData);
    }

    private void generateArgPits(List<int[]> args) {
        Pits pits;
        for (int i = 0; i < args.size(); i++) {
            JSONObject pitsObject = new JSONObject();
            JSONObject pitData = new JSONObject();

            pitsObject.put("position", new int[]{args.get(i)[0], args.get(i)[1]});

            pitsObject.put("size", new int[]{1, 1});
            pits = new Pits(new Position(args.get(i)[0], args.get(i)[1]), new Position(args.get(i)[0], args.get(i)[1]));
            pitData.put(pits.toString(), pitsObject);
            this.pitList.add(pitData);
            obstacles.add(pits);
        }
    }

    public Hashtable<String, Robot> getClients() {
        return clients;
    }

    public List<String> getClientsNames() {
        return clientsNames;
    }

    private void generateArgObstacles(List<int[]> args) {
        Obstacle obs;
        for (int i = 0; i < args.size(); i++) {
            JSONObject obstacle = new JSONObject();
            JSONObject obsData = new JSONObject();

            obstacle.put("position", "" + args.get(i)[0] + "," + args.get(i)[1] + "");
            obstacle.put("size", "" + 1 + "," + 1);
            obs = new Obstacle(new Position(args.get(i)[0], args.get(i)[1]), new Position(args.get(i)[0], args.get(i)[1]));
            obsData.put(obs.toString(), obstacle);
            this.obsList.add(obsData);

            obstacles.add(obs);
        }
    }

    public Initialize getInitialize() {
        return init;
    }


    public void setPositions() {
        if ((maxHeight + maxWidth) > 2) {
            for (int i = -maxHeight/2 ; i <= maxHeight/2 ; i++) {
                for (int j = -maxWidth/2 ; j <= maxWidth/2 ; j++) {
                    Position pos = new Position(i, j);
                    if (!positionBlocked(pos)) {
                        if (!notAvailable(pos)) {
                            possibleLocations.add(pos);
                        }
                    }
                }
            }
        } else {
            possibleLocations.add(new Position(0, 0));
        }
    }

    public List<Position> getPossibleLocations() {
        return possibleLocations;
    }

    //this should be inside robot
    public static boolean notAvailable(Position position) {
        for (Robot cl : clients.values())
            if (cl.getPosition().equals(position)) return true;
        return false;
    }

    private boolean positionBlocked(Position position) {
        ArrayList<Object> obstacles = getObstacles();
        for (Object obs : obstacles) {
            switch (obs.getClass().getName().toLowerCase()) {
                case "sserver.world.pits":
                case "sserver.world.obstacle":
                    if (((Obstacle) obs).blocksPosition(position)) {
                        return true;
                    }
            }
        }
        return false;
    }

    private void handleObstacles() {
        if (serverArguments.getObs().size() != 0) {
            generateArgObstacles(serverArguments.getObs());
        }
    }

    private void handlePits() {
        if (serverArguments.getPits().size() != 0) {
            generateArgPits(serverArguments.getPits());
        }
    }


    public ArrayList<Object> getObstacles() {
        return obstacles;
    }

    public void addMines(Mine mine) {
        this.obstacles.add(mine);
    }

    public void removeMine(Object obs) {
        this.obstacles.removeIf(obj -> obj.equals(obs));
        this.mineData.removeIf(obj -> obj.equals(obs));
    }

    public void setMineData(JSONArray mineData) {
        this.mineData = mineData;
    }

    private void getWorldSize() {
        this.sizeList.put("length", this.maxWidth);
        this.sizeList.put("width", this.maxHeight);
    }

    public JSONObject getObjects() {
        getWorldSize();
        this.Objects.put("obstacles", this.obsList);
        this.Objects.put("mines", this.mineData);
        this.Objects.put("pits", this.pitList);
        this.Objects.put("worldSize", sizeList);
        return this.Objects;
    }

    public void restoreWorld(JSONObject Data) {
        this.obstacles.clear();
        setWorldSize((JSONObject) Data.get("worldSize"));
        restoreOBs((JSONArray) Data.get("obstacles"));
        restorePits((JSONArray) Data.get("pits"));
        restoreMines((JSONArray) Data.get("mines"));

    }

    private void setWorldSize(JSONObject worldSize) {
        //"worldSize":{"length":2,"width":2}
        int worldLength = (int) worldSize.get("length");
        int worldWidth = (int) worldSize.get("width");
    }

    private void restoreOBs(JSONArray obs) {
        //{"size":"1,1","x":-1,"y":-5}
        for (Object objects :
                obs) {
            JSONObject obstacle = (JSONObject) objects;
            int x = (int) obstacle.get("x");
            int y = (int) obstacle.get("y");
            String[] size = obstacle.get("size").toString().split(",");

            Obstacle Obs = new Obstacle(new Position(x, y), new Position(Integer.parseInt(size[0]) + x, Integer.parseInt(size[1]) + y));
            obstacles.add(Obs);
        }
    }

    private void restorePits(JSONArray pits) {
        for (Object objects :
                pits) {
            JSONObject pit = (JSONObject) objects;
            int x = (int) pit.get("x");
            int y = (int) pit.get("y");
            String[] size = pit.get("size").toString().split(",");

            Pits Pit = new Pits(new Position(x, y), new Position(Integer.parseInt(size[0]) + x, Integer.parseInt(size[1]) + y));
            obstacles.add(Pit);
        }

    }

    private void restoreMines(JSONArray mines) {
        for (Object objects :
                mines) {
            JSONObject mine = (JSONObject) objects;
            int x = (int) mine.get("x");
            int y = (int) mine.get("y");
            String[] size = mine.get("size").toString().split(",");

            Mine Mine = new Mine(x, y);
            obstacles.add(Mine);
        }
    }

    public DbConnect getDBConnect() {
        return this.dbConnect;
    }

    public void addObstacles(JSONObject object) {
        int sizeX = Integer.parseInt(object.get("size").toString().split(",")[0]);
        int sizey = Integer.parseInt(object.get("size").toString().split(",")[1]);
        int posX = Integer.parseInt(object.get("position").toString().split(",")[0]);
        int posY = Integer.parseInt(object.get("position").toString().split(",")[1]);
        String type = object.get("type").toString();
        JSONObject obsValues = new JSONObject();
        JSONObject obsData = new JSONObject();

        if (type.equalsIgnoreCase("mine")) {

            Mine mine = new Mine(posX, posY);
            obsValues.put("position", "" + posX + "," + posY + "");
            obsValues.put("size", "" + sizeX + "," + sizey);

            obsData.put(mine.toString(), obsValues);
            this.mineData.add(obsData);
            obstacles.add(mine);
        } else if (type.equalsIgnoreCase("obstacle")) {

            Obstacle obstacle = new Obstacle(new Position(posX, posY), new Position(posX + sizeX, posY + sizey));
            obsValues.put("position", "" + posX + "," + posY + "");
            obsValues.put("size", "" + sizeX + "," + sizey);

            obsData.put(obstacle.toString(), obsValues);
            obsList.add(obsData);
            obstacles.add(obstacle);
        } else if (type.equalsIgnoreCase("pit")) {

            Pits pits = new Pits(new Position(posX, posY), new Position(posX + sizeX, posY + sizey));
            obsValues.put("position", "" + posX + "," + posY + "");
            obsValues.put("size", "" + sizeX + "," + sizey);

            obsData.put(pits.toString(), obsValues);
            pitList.add(obsData);
            obstacles.add(pits);
        }

    }

    public void removeObstacles(JSONObject Data) {

        int posX = Integer.parseInt(Data.get("position").toString().split(",")[0]);
        int posY = Integer.parseInt(Data.get("position").toString().split(",")[1]);
        String type = Data.get("type").toString();
        boolean x;
        boolean y;


        for (int i = 0; i < obstacles.size(); i++) {
            Object object = obstacles.get(i);
            if (type.equalsIgnoreCase("obstacle")) {
                if (object.getClass().toString().equals("class SServer.World.Obstacle")) {
                    Obstacle obstacle = (Obstacle) object;
                    x = obstacle.getX() == posX;
                    y = obstacle.getY() == posY;
                    if (x && y) {
                        int index = obstacles.indexOf(obstacle);
                        obstacles.remove(index);
                        i -= 1;
                    }
                }
            } else if (type.equalsIgnoreCase("mine")) {
                if (object.getClass().toString().equals("class SServer.World.Mine")) {
                    Mine mine = (Mine) object;
                    x = mine.getX() == posX;
                    y = mine.getY() == posY;
                    if (x && y) {
                        int index = obstacles.indexOf(mine);
                        obstacles.remove(index);
                        i -= 1;
                    }
                }
            } else if (type.equalsIgnoreCase("pit")) {
                if (object.getClass().toString().equals("class SServer.World.Pits")) {
                    Pits pit = (Pits) object;
                    x = pit.getX() == posX;
                    y = pit.getY() == posY;
                    if (x && y) {
                        int index = obstacles.indexOf(pit);
                        obstacles.remove(index);
                        i -= 1;
                    }
                }
            }
        }
    }

    public void purgeRobot(String name){
        clients.remove(name);
    }

    public ServerArguments getServerArguments(){
        return  this.serverArguments;
    }
}
