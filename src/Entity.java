public abstract class Entity {
    protected Block[] blocks;

    public Entity(int bodyLength) {
        this.blocks = new Block[bodyLength];
    }

    public Entity(Block[] blocks) {
        this.blocks = blocks;
    }

    public abstract void update(Entity[] entities) throws CollisionException;

    public Block[] getBlocks() {
        return blocks;
    }

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

    protected boolean checkIfPositionOutOfBoundries(Position pos) {
        return pos.col < 0 || pos.col > Constants.COLS_COUNT - 1 || pos.row < 0 || pos.row > Constants.ROWS_COUNT - 1;
    }
}
