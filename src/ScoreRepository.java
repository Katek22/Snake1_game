import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreRepository {
    public static void writeScoresToCSV(List<Score> scores, String filePath) {
        boolean fileExists = Files.exists(Paths.get(filePath));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if (!fileExists) {
                writer.write("Player,Score");
                writer.newLine();
            }

            for (Score score : scores) {
                writer.write(score.player + "," + score.points);
                writer.newLine();
            }

            System.out.println("Zapisano dane do pliku CSV.");
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania do pliku CSV: " + e.getMessage());
        }
    }

    public static List<Score> readScoresFromCSV(String filePath) {
        List<Score> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length == 2) {
                    Score score = new Score(data[0], Integer.parseInt(data[1]));
                    scores.add(score);
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytywania pliku CSV: " + e.getMessage());
        }

        return scores.stream()
                .sorted(Comparator.comparingInt((Score score) -> score.points).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
