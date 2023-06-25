import java.awt.*;

public class Wall extends Entity{
    public Wall() {
        super(new Block[5]);
        initializeBlocks();
    }

    public Wall(int length) {
        super(new Block[length]);
        initializeBlocks();
    }

    @Override
    public void update(Entity[] entities) {
        //
    }

    private Position makeRandomPosition() {
        int row = (int) (Math.random() * (Constants.ROWS_COUNT - 5) + 1);
        int col = (int) (Math.random() * (Constants.COLS_COUNT - blocks.length - 1) + 1);
        return new Position(row, col);
    }

    private void initializeBlocks() {
        Position initPos = makeRandomPosition();
        initializeBlocks(initPos);
    }

    private void initializeBlocks(Position initialPos) {
        for (int i = 0; i < this.blocks.length; i++) {
            Position pos = new Position(initialPos.row, initialPos.col + i);
            blocks[i] = new Block(pos, Color.blue);
        }
    }

}
