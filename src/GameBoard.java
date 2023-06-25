import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JPanel implements ActionListener {
    private static int DELAY = 100;
    private Game game;
    private Timer timer;
    private JButton restartButton;

    public GameBoard(Game game) {
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        if (e.getSource() == restartButton) {
            restartGame();
        }
    }

    private void drawBoardLines(Graphics g) {
        for (int i = 0; i < game.getColsCount(); i++) {
            g.drawLine(i * Constants.FIELD_SIZE, 0, i * Constants.FIELD_SIZE, game.getColsCount() * Constants.FIELD_SIZE);
            g.drawLine(0, i * Constants.FIELD_SIZE, game.getRowsCount() * Constants.FIELD_SIZE, i * Constants.FIELD_SIZE);
        }
    }

    private void drawScore(Graphics g) {
        int leftPaddingMultiplier = 1;
        for (Snake snake : game.getSnakes()) {
            g.setColor(snake.getColor());
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + snake.getPoints(), ((game.getColsCount() * Constants.FIELD_SIZE - metrics.stringWidth("Score: " + snake.getPoints())) / 4) * leftPaddingMultiplier, g.getFont().getSize());
            leftPaddingMultiplier += 2;
        }
    }

    private void drawEntities(Graphics g) {
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

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (game.getColsCount() * Constants.FIELD_SIZE - metrics2.stringWidth("GAME OVER")) / 2, game.getRowsCount() * Constants.FIELD_SIZE / 2);
    }

    private void drawBestScores(Graphics g) {
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

    private void initializeKeyAdapters() {
        for (Snake snake : game.getSnakes()) {
            if (snake instanceof PlayerSnake) {
                this.addKeyListener(((PlayerSnake) snake).getKeyAdapter());
            }
        }
    }

    private void removeKeyAdapters() {
        for (Snake snake : game.getSnakes()) {
            if (snake instanceof PlayerSnake) {
                this.removeKeyListener(((PlayerSnake) snake).getKeyAdapter());
            }
        }
    }

    private GameMode chooseGameMode() {
        Object[] options = {"Jednoosobowa", "Dwuosobowa", "Z komputerem"};
        int choice = JOptionPane.showOptionDialog(this, "Wybierz tryb gry:", "Wybór trybu gry",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        return GameMode.values()[choice];
    }

    private void restartGame() {
        removeKeyAdapters();
        game = GameManager.createGame(chooseGameMode());
        initializeKeyAdapters();
        game.start();
        timer.start();
    }
}
