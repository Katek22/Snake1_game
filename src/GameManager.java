import java.awt.*;
import java.awt.event.KeyEvent;
/**
 * Defines game modes
 */
public class GameManager {
    public static Game createGame(GameMode mode) {
        switch (mode) {
            case MULTIPLAYER:
                return new Game(new Snake[]{new PlayerSnake(), new PlayerSnake(6, new Position(Constants.COLS_COUNT - 1, 0), Direction.DOWN, new int[]{KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S}, Color.green, new Color(45, 180, 0))});
            case WITH_COMPUTER:
                return new Game(new Snake[]{new PlayerSnake(), new ArtificialSnake(6, new Position(Constants.COLS_COUNT - 1, 0), Direction.DOWN)});
            default:
                // SINGLEPLAYER is default
                return new Game(new Snake[]{new PlayerSnake()});
        }
    }
}
