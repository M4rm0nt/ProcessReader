import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        ProcessBuilder processBuilder = new ProcessBuilder(List.of("ps", "-aux"));
        Process process;

        try {
            process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                logger.error("Prozess beendet mit Fehlercode: {}", exitCode);
            } else {
                logger.info("Prozess erfolgreich beendet mit Code: {}", exitCode);
            }

        } catch (IOException e) {
            logger.error("IOException aufgetreten: ", e);
        } catch (InterruptedException e) {
            logger.error("Prozess wurde unterbrochen: ", e);
            Thread.currentThread().interrupt();
        }
    }
}
