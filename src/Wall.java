import java.awt.*;

/**
 * Represents an obstacle on the game board.
 */

public class Wall extends Entity{
	/**
	 * Declares the  def size of the obstacle.
	 */
    public Wall() {
        super(new Block[5]);
        initializeBlocks();
    }

    /**
     * Declares the setted size of the obstacle.
     * 
     * @param length in blocks
     */
    public Wall(int length) {
        super(new Block[length]);
        initializeBlocks();
    }

    /**
     * 
     */
    @Override
    public void update(Entity[] entities) {
        //
    }

    /**
     * 
     * @return random position on the board.
     */
    private Position makeRandomPosition() {
        int row = (int) (Math.random() * (Constants.ROWS_COUNT - 5) + 1);
        int col = (int) (Math.random() * (Constants.COLS_COUNT - blocks.length - 1) + 1);
        return new Position(row, col);
    }

    private void initializeBlocks() {
        Position initPos = makeRandomPosition();
        initializeBlocks(initPos);
    }

    /**
     * Setting coords of the block of the obstacle
     * @param initialPos pos of the first block
     */
    private void initializeBlocks(Position initialPos) {
        for (int i = 0; i < this.blocks.length; i++) {
            Position pos = new Position(initialPos.row, initialPos.col + i);
            blocks[i] = new Block(pos, Color.blue);
        }
    }

}
