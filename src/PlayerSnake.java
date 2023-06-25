import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * Snake but controlled by player, developed by KeyAdapter
 */
public class PlayerSnake extends Snake {
    private int[] initialKeys = new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN};
    private int[] keys;
    private KeyAdapter keyAdapter;

    /**
     * init
     */
    public PlayerSnake() {
        super(Color.ORANGE, new Color(220, 140, 0));
        keys = initialKeys;
        keyAdapter = new MovementKeyAdapter();
    }

    /**
     * init wiht param
     * @param initialBodyLength
     * @param initialPosition
     * @param initialDirection
     * @param keyMapping
     * @param headColor
     * @param bodyColor
     */
    public PlayerSnake(int initialBodyLength, Position initialPosition, Direction initialDirection, int[] keyMapping, Color headColor, Color bodyColor) {
        super(initialBodyLength, initialPosition, initialDirection, headColor, bodyColor);
        keys = keyMapping;
        keyAdapter = new MovementKeyAdapter();
    }

    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }

    @Override
    protected void makeMove(Entity[] entities) {
        //
    }
    /**
     * Actual listener of pressed keys, saving result in direction variable of the Snake
     */
    public class MovementKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == keys[0]) {
                setDirection(Direction.LEFT);
            } else if (keyCode == keys[1]) {
                setDirection(Direction.RIGHT);
            } else if (keyCode == keys[2]) {
                setDirection(Direction.UP);
            } else if (keyCode == keys[3]) {
                setDirection(Direction.DOWN);
            }
        }
    }
}


