import java.awt.*;
/**
 * Snake controlled by AI
 */
public class ArtificialSnake extends Snake{
    public ArtificialSnake(int initialBodyLength, Position initialPosition, Direction initialDirection) {
        super(initialBodyLength, initialPosition, initialDirection, Color.GREEN, new Color(45, 180, 0));
    }
    /**
     * generating position of the new move 
     */
    @Override
    protected void makeMove(Entity[] entities) {
        Position pos = getClosestEatablePosition(entities);
        Position nextPos = Position.random();
        Direction suggestedDirection = Direction.UP;

        suggestedDirection = suggestDirection(pos);
        nextPos = getNextPosition(suggestedDirection);
        if (checkIfPositionOccupiedByWall(nextPos, entities)) {
            System.out.println("Wall encountered");
            suggestedDirection = getCurvedDirection(suggestedDirection);
        }

        setDirection(suggestedDirection);
    }
    /**
     * 
     * @param entities
     * @return position to be reached 
     */
    private Position getClosestEatablePosition(Entity[] entities) {
        double smallestDiff = 1000.0;
        Position headPos = blocks[0].getPosition();
        Position closestPos = new Position(0, 0);
        for (Entity entity : entities) {
            if (!(entity instanceof Eatable)) {
                continue;
            }
            Position pos = entity.getBlocks()[0].getPosition();
            int xDiff = Math.abs(headPos.row - pos.row);
            int yDiff = Math.abs(headPos.col - pos.col);
            double diff = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            if (diff < smallestDiff) {
                smallestDiff = diff;
                closestPos = pos;
            }
        }

        return closestPos;
    }
    /**
     * make move without changing direction
     * @return
     */
    private Position getNextPosition() {
        return getNextPosition(getDirection());
    }

    /**
     *  make move with changing direction
     * @param direction
     * @return
     */
    private Position getNextPosition(Direction direction) {
        Position curPos = blocks[0].getPosition();
        switch (direction) {
            case DOWN:
                return new Position(curPos.row + 1, curPos.col);
            case UP:
                return new Position(curPos.row - 1, curPos.col);
            case LEFT:
                return new Position(curPos.row, curPos.col - 1);
            default:
                return new Position(curPos.row, curPos.col + 1);
        }
    }
    /**
     * Change direction to get to the eatable by least moves 
     * @param eatablePosition
     * @return direction to take
     */
    private Direction suggestDirection(Position eatablePosition) {
        Position headPos = blocks[0].getPosition();
        if (eatablePosition.row > headPos.row) {
            return Direction.RIGHT;
        } else if (eatablePosition.row < headPos.row) {
            return Direction.LEFT;
        } else if (eatablePosition.col < headPos.col) {
            return Direction.UP;
        } else if (eatablePosition.col > headPos.col) {
            return Direction.DOWN;
        }
        return Direction.UP;
    }
    /**
     * turning of the snake
     * @param direction
     * @return
     */
    private Direction getCurvedDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                return Direction.LEFT;
            case LEFT:
                return Direction.UP;
            case RIGHT:
                return Direction.DOWN;
            default:
                return Direction.RIGHT;
        }
    }
}
