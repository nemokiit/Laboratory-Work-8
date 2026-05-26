import java.util.List;
import java.util.stream.Collectors;

public class Processors {
    public static class FilterProcessor {
        // Marked with custom annotation; filters out records with price < 100.0 using Stream API
        @DataProcessor
        public List<CryptoRecord> filterCheap(List<CryptoRecord> data) {
            return data.stream().filter(record -> record.getPrice() >= 100.0).collect(Collectors.toList());
        }
    }

    public static class TransformProcessor {
        // Marked with custom annotation; maps records to new instances with a simulated currency conversion
        @DataProcessor
        public List<CryptoRecord> convertToRubles(List<CryptoRecord> data) {
            return data.stream().map(record -> new CryptoRecord(record.getName(),
                    record.getPrice() * 72.77)).collect(Collectors.toList());
        }
    }
}
