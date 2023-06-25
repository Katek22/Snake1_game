public abstract class Eatable extends Entity {
    private int points;

    public Eatable(Block[] blocks, int points) {
        super(blocks);
        this.points = points;
    }

    public abstract void refresh(Entity[] entities);

    public int getPoints() {
        return points;
    }

    protected Position generateNewPosition(Entity[] entities) {
        Position newPos = Position.random();
        while (checkIfPositionOccupied(newPos, entities)) {
            newPos = Position.random();
        }
        return newPos;
    }
}
