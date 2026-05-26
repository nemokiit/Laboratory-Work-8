import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DataManager {
    private List<Object> processors = new ArrayList<>();
    private List<CryptoRecord> data = new ArrayList<>();

    // Registers processor instances that contain @DataProcessor annotated methods
    public void registerDataProcessor(Object processor) {
        processors.add(processor);
    }

    // Loads data from a file utilizing NIO and Stream API mappings
    public void loadData(String source) {
        try {
            this.data = Files.lines(Paths.get(source)).map(line -> {
                String[] parts = line.split(",");
                return new CryptoRecord(parts[0], Double.parseDouble(parts[1]));
            }).collect(Collectors.toList());
            System.out.println("Данные загружены. Количество записей: " + data.size());
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    // Processes data concurrently using an ExecutorService and Callable tasks
    public void processData() {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        int midpoint = data.size() / 2;
        List<CryptoRecord> part1 = data.subList(0, midpoint);
        List<CryptoRecord> part2 = data.subList(midpoint, data.size());

        Callable<List<CryptoRecord>> task1 = () -> applyProcessors(part1);
        Callable<List<CryptoRecord>> task2 = () -> applyProcessors(part2);

        try {
            // Submitting tasks and waiting for their results using Future.get()
            Future<List<CryptoRecord>> future1 = executor.submit(task1);
            Future<List<CryptoRecord>> future2 = executor.submit(task2);

            List<CryptoRecord> processedData = new ArrayList<>();
            processedData.addAll(future1.get());
            processedData.addAll(future2.get());

            this.data = processedData;
            System.out.println("Данные успешно обработаны.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Gracefully shutting down the thread pool
            executor.shutdown();
        }
    }

    // Applies registered processors to a data chunk using the Reflection API
    @SuppressWarnings("unchecked")
    private List<CryptoRecord> applyProcessors(List<CryptoRecord> chunk) throws Exception {
        List<CryptoRecord> currentData = chunk;

        for (Object processor : processors) {
            for (Method method : processor.getClass().getDeclaredMethods()) {
                // Invoking method dynamically if it has the specific annotation
                if (method.isAnnotationPresent(DataProcessor.class)) {
                    currentData = (List<CryptoRecord>) method.invoke(processor, currentData);
                }
            }
        }
        return currentData;
    }

    // Saves the processed collection back into a text file using NIO
    public void saveData(String destination) {
        try {
            List<String> lines = data.stream().map(CryptoRecord::toString).collect(Collectors.toList());
            Files.write(Paths.get(destination), lines);
            System.out.println("Данные сохранены в файл: " + destination);
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}
