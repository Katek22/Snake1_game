import java.awt.*;
/**
 * Defines Block.
 */
public class Block {
    private final Position position;
    private final Color color;

    public Block(Position position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public Block copy() {
        return new Block(new Position(position.row, position.col), color);
    }
}
