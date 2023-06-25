import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Logic and executive section of drawing game board.
 */
public class GameBoard extends JPanel implements ActionListener {
    private static int DELAY = 100;
    private Game game;
    private Timer timer;
    private JButton restartButton;

    /**
     * holds current game and represents it on GameFrame
     * @param game
     */
    public GameBoard(Game game) {
         System.out.println("GameBoard");

        this.game = game;
        timer = new Timer(DELAY, this);
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(game.getColsCount() * Constants.FIELD_SIZE, game.getRowsCount() * Constants.FIELD_SIZE));
        this.setFocusable(true);

        restartButton = new JButton("Restart");
        restartButton.setFocusable(false);
        restartButton.addActionListener(this);
        restartButton.setVisible(false);
        this.add(restartButton);

        initializeKeyAdapters();
        game.start();
        timer.start();
    }
    /**
     * Draws drawBoardLines, drawScore and drawEntities while game is running
     * Draws drawScore, drawGameOver, drawBestScores after game ends
     */ 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent");

        if (game.getIsRunning()) {
            restartButton.setVisible(false);
            drawBoardLines(g);
            drawScore(g);
            drawEntities(g);
        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            restartButton.setVisible(true);
            drawScore(g);
            drawGameOver(g);
            drawBestScores(g);
        }
    }

    /**
     * restart button clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("actionPerformed");

        repaint();
        if (e.getSource() == restartButton) {
            restartGame();
        }
    }

    /**
     * 
     * @param g
     */
    private void drawBoardLines(Graphics g) {
      System.out.println("drawBoardLines");

        for (int i = 0; i < game.getColsCount(); i++) {
            g.drawLine(i * Constants.FIELD_SIZE, 0, i * Constants.FIELD_SIZE, game.getColsCount() * Constants.FIELD_SIZE);
            g.drawLine(0, i * Constants.FIELD_SIZE, game.getRowsCount() * Constants.FIELD_SIZE, i * Constants.FIELD_SIZE);
        }
    }

    /**
     * Draws current score.
     * @param g
     */
    private void drawScore(Graphics g) {
      System.out.println("drawScore");

        int leftPaddingMultiplier = 1;
        for (Snake snake : game.getSnakes()) {
            g.setColor(snake.getColor());
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + snake.getPoints(), ((game.getColsCount() * Constants.FIELD_SIZE - metrics.stringWidth("Score: " + snake.getPoints())) / 4) * leftPaddingMultiplier, g.getFont().getSize());
            leftPaddingMultiplier += 2;
        }
    }
    /**
     * 
     * @param g
     */
    private void drawEntities(Graphics g) {
        System.out.println("drawEntities");

        for (Entity entity : game.getEntities()) {
            for (Block block : entity.getBlocks()) {
                Position pos = block.getPosition();
                int x = pos.row * Constants.FIELD_SIZE;
                int y = pos.col * Constants.FIELD_SIZE;
                g.setColor(block.getColor());
                g.fillRect(x, y, Constants.FIELD_SIZE, Constants.FIELD_SIZE);
            }
        }
    }
    /**
     * Draws game over screen.
     * @param g
     */
    private void drawGameOver(Graphics g) {
        System.out.println("drawGameOver");

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (game.getColsCount() * Constants.FIELD_SIZE - metrics2.stringWidth("GAME OVER")) / 2, game.getRowsCount() * Constants.FIELD_SIZE / 2);
    }
    /**
     * Draws Best Scores.
     * @param g
     */
    private void drawBestScores(Graphics g) {
        System.out.println("drawBestScores");

        g.setColor(Color.GREEN);
        g.setFont(new Font("Ink Free", Font.BOLD, 35));
        FontMetrics metrics = getFontMetrics(g.getFont());
        int height = metrics.getHeight() + 2;
        var scores = game.getTopScores();
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            String text = score.player + ": " + score.points;
            g.drawString(text, (game.getColsCount() * Constants.FIELD_SIZE - metrics.stringWidth(text)) / 2, (game.getRowsCount() * Constants.FIELD_SIZE / 2) + 60 + height * i);
        }
    }
    /**
     * init of the key adapter to the snakes
     */
    private void initializeKeyAdapters() {
        System.out.println("initializeKeyAdapters");

        for (Snake snake : game.getSnakes()) {
            if (snake instanceof PlayerSnake) {
                this.addKeyListener(((PlayerSnake) snake).getKeyAdapter());
            }
        }
    }

    /**
     * removing them
     */
    private void removeKeyAdapters() {
        System.out.println("removeKeyAdapters");

        for (Snake snake : game.getSnakes()) {
            if (snake instanceof PlayerSnake) {
                this.removeKeyListener(((PlayerSnake) snake).getKeyAdapter());
            }
        }
    }

    private GameMode chooseGameMode() {
        System.out.println("chooseGameMode");

        Object[] options = {"Jednoosobowa", "Dwuosobowa", "Z komputerem"};
        int choice = JOptionPane.showOptionDialog(this, "Wybierz tryb gry:", "Wybór trybu gry",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        return GameMode.values()[choice];
    }
    /**
     * starting new game
     */
    private void restartGame() {
        System.out.println("restartGame");

        removeKeyAdapters();
        game = GameManager.createGame(chooseGameMode());
        initializeKeyAdapters();
        game.start();
        timer.start();
    }
}
