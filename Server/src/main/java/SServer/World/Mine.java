package SServer.World;

import SServer.ClientCommands.Position;

public class Mine {

    private final int x;
    private final int y;

    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getMineX() { return this.x; }

    public int getMineY() { return this.y; }

    /**
     * Check if the robot is on-top of the mine
     * @param position Position of the robot
     */
    public boolean blocksPosition(Position position) {
        if (position.getX() == x && position.getY() == y) return true;
        return false;
    }

    /**
     * Check if the robot passes over a mine
     * @param currentPosition Starting position of the robot
     * @param newPosition Ending position of the robot
     * @return
     */
    public boolean blocksPath(Position currentPosition, Position newPosition) {

        if ((currentPosition.getX() < this.x && this.x < newPosition.getX())
                || (currentPosition.getX() >= this.x && this.x >= newPosition.getX())) {
            if (newPosition.getY() <= (this.y + 4) && newPosition.getY() >= this.y) {
                return true;
            }
        }
        if ((currentPosition.getY() <= this.y && this.y <= newPosition.getY())
                || (currentPosition.getY() >= this.y && this.y >= newPosition.getY())) {
            if (newPosition.getX() <= (this.x + 4) && newPosition.getX() >= this.x) {
                return true;
            }
        }
        return false;
    }

    public int getX() {
            return x;
    }

    public int getY() {
        return y;
    }
}
