package SServer.ClientCommands;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return y the y axis of position
     */
    public int getX() {
        return x;
    }

    /**
     * @return x the x axis of position
     */
    public int getY() {
        return y;
    }

    /**
     * Compares objects are equal
     * @param o any object
     * @return true if 2 objects match else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    /**
     * Checks that we are in the border
     * @param topLeft the topleft corner
     * @param bottomRight the bottomleft corner
     * @return true if we in the border else false
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }
}
