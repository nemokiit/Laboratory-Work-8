# Java Programming: Laboratory Work 8

This repository contains the solutions for the eighth and final laboratory work in Java programming. It integrates several advanced Java features into a single, cohesive data-processing pipeline.

## Advanced Features Implemented
1. **Custom Annotations:** Implemented `@DataProcessor` to tag specific methods meant for data manipulation dynamically.
2. **Stream API:** Utilized `.stream()`, `.filter()`, `.map()`, and `.collect()` extensively to process collections declaratively (e.g., removing records under a certain price point, manipulating currency rates, and transforming Objects into Strings).
3. **Reflection API:** Used `getClass().getDeclaredMethods()`, `isAnnotationPresent()`, and `invoke()` within `DataManager` to dynamically discover and apply processing methods at runtime.
4. **Concurrency (`java.util.concurrent`):** Employed an `ExecutorService` thread pool to split data into halves and process them concurrently using `Callable` and `Future` objects.

## Application Architecture
- `CryptoRecord.java`: The domain model representing cryptocurrency data.
- `DataProcessor.java`: The custom marker annotation.
- `Processors.java`: Contains filter and transform algorithms utilizing the Stream API.
- `DataManager.java`: The core engine handling I/O (NIO `Files`), multithreading, and reflection.
- `Main.java`: The entry point that orchestrates file creation, processor registration, and the pipeline execution.

## How to Run

1. Clone this repository and navigate to the project directory.
2. Compile all source files:
   ```bash
   javac *.java
   ```
3. 1. Run the main class:
  ```
  java Main
  ```
*(The program will automatically generate input.txt with mock data, process it concurrently, and output the result to output.txt)*
