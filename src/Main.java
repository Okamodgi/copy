import java.io.*;

public class Main {

    public static void main(String[] args) {
        // Исходные файлы и путь для копирования
        String sourceFile1 = "sourceFile1.txt";
        String sourceFile2 = "sourceFile2.txt";
        String destinationPath = "destinationFolder/";

        // Последовательное копирование
        long startTime = System.currentTimeMillis();
        copyFileSequentially(sourceFile1, destinationPath + "copyFile1.txt");
        copyFileSequentially(sourceFile2, destinationPath + "copyFile2.txt");
        long endTime = System.currentTimeMillis();
        long sequentialTime = endTime - startTime;
        System.out.println("Последовательное копирование заняло " + sequentialTime + " миллисекунд");

        // Параллельное копирование
        startTime = System.currentTimeMillis();
        Thread thread1 = new Thread(() -> copyFileSequentially(sourceFile1, destinationPath + "copyFile1.txt"));
        Thread thread2 = new Thread(() -> copyFileSequentially(sourceFile2, destinationPath + "copyFile2.txt"));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        long parallelTime = endTime - startTime;
        System.out.println("Параллельное копирование заняло " + parallelTime + " миллисекунд");
    }

    private static void copyFileSequentially(String sourceFilePath, String destinationFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}