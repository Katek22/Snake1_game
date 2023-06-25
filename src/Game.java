import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Thread to control game play.
 */
public class Game extends Thread{
    private int colsCount;
    private int rowsCount;
    private Snake[] snakes;
    private Entity[] entities;
    private boolean isRunning;
    private List<Score> topScores;

    /**
     * init game with given snake/s
     * @param snakes
     */
    public Game(Snake[] snakes) {
        this.snakes = snakes;
        colsCount = Constants.COLS_COUNT;
        rowsCount = Constants.ROWS_COUNT;
        isRunning = false;
        topScores = new ArrayList<Score>();
        initializeEntities();
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public int getColsCount() {
        return colsCount;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public Snake[] getSnakes() {
        return snakes;
    }

    public Entity[] getEntities() {
        return entities;
    }

    /**
     *  isRunning = true;
     */
    public void startGame() {
        isRunning = true;
        System.out.println("Starting game");
        update();
    }

    /**
     * saving scores, loading top scores, isRunning = true;
     */
    public void gameOver() {
        saveScores();
        loadTopScores();
        isRunning = false;
        System.out.println("Game over");
    }

    /**
     * updating game play status, checks for collisons and giving speed rate of game play.
     */
    public void update() {
        if (!isRunning) return;

        for (Entity entity : entities) {
            try {
                entity.update(entities);
            } catch (CollisionException e) {
                gameOver();
                return;
            }
        }
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        update();
    }

    public void run() {
        startGame();
    }

    public List<Score> getTopScores() {
        return topScores;
    }
    /**
     * from CSV file
     */
    private void loadTopScores() {
        topScores = ScoreRepository.readScoresFromCSV("scores.csv");
    }

    private void initializeEntities() {
        Entity[] staticEntities = new Entity[]{new Apple(), new Wall(), new Frog()};
        entities = new Entity[snakes.length + staticEntities.length];
        for (int i = 0; i < entities.length; i++) {
            if (i < staticEntities.length) {
                entities[i] = staticEntities[i];
            } else {
                int j = i - staticEntities.length;
                entities[i] = snakes[j];
            }
        }
    }
    /**
     * to CSV file
     */
    private void saveScores() {
        List<Score> scores = new ArrayList<Score>(snakes.length);
        for (int i = 0; i < snakes.length; i++) {
            scores.add(new Score(String.format("Player %d", i), snakes[i].getPoints()));
        }
        ScoreRepository.writeScoresToCSV(scores, "scores.csv");
    }
}
