package SServer.World;

import java.util.Random;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    /**
     * Picks a random direction to spawn the robot in
     * @return Direction random direction
     */
    public static Direction getRandomDirection() {
        Random random = new Random();
        Direction[] values = Direction.values();
        return values[random.nextInt(values.length)];
    }
}
