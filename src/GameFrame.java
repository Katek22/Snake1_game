import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Oldest parent of the game board, responsible by viewing a window 
 */
public class GameFrame extends JFrame {
    private Game game;

    public GameFrame() {
        game = GameManager.createGame(chooseGameMode());
        this.add(new GameBoard(game));
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private GameMode chooseGameMode() {
        Object[] options = {"Jednoosobowa", "Dwuosobowa", "Z komputerem"};
        int choice = JOptionPane.showOptionDialog(this, "Wybierz tryb gry:", "Wybór trybu gry",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        return GameMode.values()[choice];
    }
}


