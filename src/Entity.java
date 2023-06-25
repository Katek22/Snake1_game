
/**
 * Represents all of the objects on the board (snake, wall, apple, frog)
 */
public abstract class Entity {
    protected Block[] blocks;
    /**
     * number of the blocks used by an Entity 
     * @param bodyLength
     */
    public Entity(int bodyLength) {
        this.blocks = new Block[bodyLength];
    }

    /**
     * Blocks which represents an Entity
     * @param blocks
     */
    public Entity(Block[] blocks) {
        this.blocks = blocks;
    }

    /**
     * Updates entities position
     * @param entities
     * @throws CollisionException when two entities share same block
     */
    public abstract void update(Entity[] entities) throws CollisionException;

    /**
     * 
     * @return blocks used by an Entity.
     */
    public Block[] getBlocks() {
        return blocks;
    }

    /**
     * checks whether this block is occupied by any entity on the board
     * @param pos block to be checked
     * @param entities list of the entites
     * @return true is occupied, false isnt
     */
    protected boolean checkIfPositionOccupied(Position pos, Entity[] entities) {
        for (Entity entity : entities) {
            for (Block block : entity.getBlocks()) {
                if (pos.equals(block.getPosition())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks whether this block is occupied by a Wall on the board
     * @param pos block to be checked
     * @param entities list of the entites
     * @return true is occupied, false isnt
     */
    protected boolean checkIfPositionOccupiedByWall(Position pos, Entity[] entities) {
        for (Entity entity : entities) {
            if (!(entity instanceof Wall)) continue;
            for (Block block : entity.getBlocks()) {
                if (pos.equals(block.getPosition())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks whether this block is out side of the game board
     * @param pos
     * @return
     */
    protected boolean checkIfPositionOutOfBoundries(Position pos) {
        return pos.col < 0 || pos.col > Constants.COLS_COUNT - 1 || pos.row < 0 || pos.row > Constants.ROWS_COUNT - 1;
    }
}
