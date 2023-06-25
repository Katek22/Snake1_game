import java.awt.*;
import java.util.Random;

public class Frog extends Eatable {
    private static int MOVE_SIZE = 1;
    private static int UPDATE_FRAME_RATE = 4;
    private Random random = new Random();
    private int currentUpdate = 0;

    public Frog() {
        super(new Block[]{new Block(Position.random(), Color.cyan)}, 2);
    }

    @Override
    public void refresh(Entity[] entities) {
        blocks = new Block[]{new Block(generateNewPosition(entities), Color.cyan)};
    }

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
