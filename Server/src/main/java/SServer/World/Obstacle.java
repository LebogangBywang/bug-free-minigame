package SServer.World;

import SServer.ClientCommands.Position;

public class Obstacle {
    Position topLeft;
    Position bottomRight;

    public Obstacle(Position topLeft, Position bottomRight) {
        this.bottomRight = bottomRight;
        this.topLeft = topLeft;
    }

    public boolean blocksPosition(Position position) {
        int topLeftX = this.topLeft.getX();
        int bottomRightX = this.bottomRight.getX();
        int topLeftY = this.topLeft.getY();
        int bottomRightY = this.bottomRight.getY();

        boolean xOnObstacle = topLeftX <= position.getX() && position.getX() <= bottomRightX;
        boolean yOnObstacle = topLeftY >= position.getY() && position.getY() >= bottomRightY;
        return xOnObstacle && yOnObstacle;
    }

    public boolean blocksPath(Position a, Position b) {
        if (a.getY() == b.getY()) {
            if (a.getX() > b.getX()) {
                for (int i = b.getX(); i <= a.getX(); i++) {
                    if (blocksPosition(new Position(i, b.getY())))
                        return true;
                }
            }if (a.getX() < b.getX()) {
                for (int i = a.getX(); i <= b.getX(); i++) {
                    if (blocksPosition(new Position(i, b.getY())))
                        return true;
                }
            }
            return false;
        }else if (a.getX() == b.getX()) {
            if (a.getY() > b.getY()) {
                for (int i = b.getY(); i <= a.getY(); i++)
                    if (blocksPosition(new Position(a.getX(), i)))
                        return true;
            }
        }if (a.getY() < b.getY()) {
            for (int j = a.getY(); j <= (b.getY()); j++)
                if (blocksPosition(new Position(a.getX(), j)))
                    return true;

            return false;
        }
        return false;
    }

    public int getX(){
       return this.topLeft.getX();
    }

    public int getY(){
        return this.topLeft.getY();
    }
}
