import java.awt.*;

public class Apple extends Eatable {
    public Apple() {
        super(new Block[]{new Block(Position.random(), Color.red)}, 1);
    }

    public Apple(Position initialPosition) {
        super(new Block[]{new Block(initialPosition, Color.red)}, 1);
    }

    @Override
    public void update(Entity[] entities) {
        //
    }

    @Override
    public void refresh(Entity[] entities) {
        blocks = new Block[]{new Block(generateNewPosition(entities), Color.red)};
    }
}
