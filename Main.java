import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Prepare mock environment
        createTestFile("input.txt");

        DataManager dataManager = new DataManager();

        // Registering data processing instances
        dataManager.registerDataProcessor(new Processors.FilterProcessor());
        dataManager.registerDataProcessor(new Processors.TransformProcessor());

        // Executing the data pipeline
        dataManager.loadData("input.txt");
        dataManager.processData();
        dataManager.saveData("output.txt");
    }

    // Utility method to generate initial test data
    private static void createTestFile(String filename) {
        List<String> lines = Arrays.asList(
                "Bitcoin,65000.0",
                "Ethereum,3500.0",
                "Solana,140.0",
                "Dogecoin,0.15",
                "Cardano,0.45",
                "BinanceCoin,600.0"
        );
        try {
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
