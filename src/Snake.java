import java.awt.*;
/**
 *  Managing lifecycle of the snake extends Entity
 */
public abstract class Snake extends Entity {
    protected Color headColor;
    protected Color bodyColor;
    private Direction direction;
    private int points;

    /**
     * Setting spawn position.
     * @param headColor
     * @param bodyColor
     */
    public Snake(Color headColor, Color bodyColor) {
        super(6);
        this.headColor = headColor;
        this.bodyColor = bodyColor;
        initializeBlocks(0, 0);
        points = 0;
        direction = Direction.DOWN;
    }

    /**
     * Setting spawn position.
     * @param initialBodyLength 
     * @param initialPosition 
     * @param initialDirection
     * @param headColor
     * @param bodyColor
     */
    public Snake(int initialBodyLength, Position initialPosition, Direction initialDirection, Color headColor, Color bodyColor) {
        super(initialBodyLength);
        this.headColor = headColor;
        this.bodyColor = bodyColor;
        initializeBlocks(initialPosition.row, initialPosition.col);
        points = 0;
        direction = initialDirection;
    }
    /**
     * Makes move of the snake on the board in the given direction,
     * throws CollisionException when move will collide with other Entity
     */
    @Override
    public void update(Entity[] entities) throws CollisionException {
        makeMove(entities);
        move(entities);
    }

    /**
     * 
     * @return points scored by this snake controlled by a player or ai
     */
    public int getPoints() {
        return points;
    }

    
    public Color getColor() {
        return headColor;
    }

    public Direction getDirection() {
        return direction;
    }
    /**
     * changes the direction of the movement if possible
     * @param targetDirection direction to be apply
     */
    public void setDirection(Direction targetDirection) {
        if (targetDirection == Direction.RIGHT && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        } else if (targetDirection == Direction.LEFT && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        } else if (targetDirection == Direction.UP && direction != Direction.DOWN) {
            direction = Direction.UP;
        } else if (targetDirection == Direction.DOWN && direction != Direction.UP) {
            direction = Direction.DOWN;
        } else {
            System.out.println("Unable to change direction");
        }
    }
    /**
     * transfers positions of the blocks forward by before given direction
     * @param entities
     * @throws CollisionException
     */
    protected void move(Entity[] entities) throws CollisionException {
        for (int i = blocks.length - 1; i > 0; i--) {
            Position blockPosition = blocks[i].getPosition();
            Position nexBlockPosition = blocks[i - 1].getPosition();

            blockPosition.row = nexBlockPosition.row;
            blockPosition.col = nexBlockPosition.col;
        }

        Position headPosition = blocks[0].getPosition();
        if (direction == Direction.UP) {
            headPosition.col -= 1;
        } else if (direction == Direction.DOWN) {
            headPosition.col += 1;
        } else if (direction == Direction.RIGHT) {
            headPosition.row += 1;
        } else if (direction == Direction.LEFT) {
            headPosition.row -= 1;
        }

        checkCollisions(entities);
    }

    /**
     * Apllying changes made by fun move()
     * @param entities
     */
    protected abstract void makeMove(Entity[] entities);

    private void initializeBlocks(int row, int col) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block(new Position(row, col), i == 0 ? headColor : bodyColor);
        }
    }

    /**
     * checks two scenarios hitting a border, hitting other entity
     * @param entities
     * @throws CollisionException
     */
    private void checkCollisions(Entity[] entities) throws CollisionException {
        checkBorderCollisions();
        checkEntityCollisions(entities);
    }

    /**
     * Increasing lenght of the snake after eating given eatable
     * @param eatable which is was consumed
     * @param entities
     */
    private void eat(Eatable eatable, Entity[] entities) {
        points += eatable.getPoints();
        Block[] newBlocks = new Block[blocks.length + 1];
        for (int i = 0; i < blocks.length; i++) {
            newBlocks[i] = blocks[i];
        }
        newBlocks[blocks.length] = blocks[blocks.length - 1].copy();

        blocks = newBlocks;
        eatable.refresh(entities);
    }
    /**
     * checking if head of the snake is hitting border
     * @throws CollisionException
     */
    private void checkBorderCollisions() throws CollisionException {
        Position headPos = blocks[0].getPosition();
        if (headPos.col < 0 || headPos.col > Constants.COLS_COUNT - 1 || headPos.row < 0 || headPos.row > Constants.ROWS_COUNT - 1) {
            throw new CollisionException();
        }
    }

    /**
     * checking if head of the snake is another Entity
     * @param entities
     * @throws CollisionException
     */
    private void checkEntityCollisions(Entity[] entities) throws CollisionException {
        Position headPos = blocks[0].getPosition();
        for (Entity entity : entities) {
            int smallestBlock = (entity == this) ? 0 : -1;
            for (int i = entity.getBlocks().length - 1; i > smallestBlock; i--) {
                Position blockPos = entity.getBlocks()[i].getPosition();
                if (headPos.equals(blockPos)) {
                    if (entity instanceof Eatable) {
                        eat((Eatable) entity, entities);
                    } else throw new CollisionException();
                }
            }
        }
    }
}
