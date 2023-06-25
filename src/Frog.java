import java.awt.*;
import java.util.Random;
/**
 * Represents frog (1 block Entity) 
 */
public class Frog extends Eatable {
    private static int MOVE_SIZE = 1;
    private static int UPDATE_FRAME_RATE = 4;
    private Random random = new Random();
    private int currentUpdate = 0;

    /**
     * create frog with random position
     */
    public Frog() {
        super(new Block[]{new Block(Position.random(), Color.cyan)}, 2);
    }
    /**
     * generating new random position
     */
    @Override
    public void refresh(Entity[] entities) {
        blocks = new Block[]{new Block(generateNewPosition(entities), Color.cyan)};
    }
    /**
     * checking for collsions
     */
    @Override
    public void update(Entity[] entities) throws CollisionException {
        if (currentUpdate != UPDATE_FRAME_RATE) {
            currentUpdate++;
            return;
        } else {
            currentUpdate = 0;
        }

        Position newPos = generateNewPosition();
        while (checkIfPositionOccupied(newPos, entities) || checkIfPositionOutOfBoundries(newPos)) {
            newPos = generateNewPosition();
        }
        blocks = new Block[]{new Block(newPos, Color.cyan)};
    }
    /**
     * generating new given position
     * @return
     */
    private Position generateNewPosition() {
        Position curPos = blocks[0].getPosition();
        Direction randomDirection = Direction.values()[random.nextInt(4)];
        switch (randomDirection) {
            case DOWN:
                return new Position(curPos.row + MOVE_SIZE, curPos.col);
            case LEFT:
                return new Position(curPos.row, curPos.col - MOVE_SIZE);
            case RIGHT:
                return new Position(curPos.row, curPos.col + MOVE_SIZE);
            default: // UP IS DEFAULT
                return new Position(curPos.row - MOVE_SIZE, curPos.col);
        }
    }
}
