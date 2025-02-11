package by.shift;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor();

        // Путь к файлу в resources
        String filePath = "src/main/resources/input.txt";

        // Проверка существования файла
        if (!Files.exists(Paths.get(filePath))) {
            System.out.println("Файл не найден: " + filePath);
            return;
        }

        // Инициализация параметров
        String sortParameter = null;
        String orderParameter = null;
        String outputParameter = null;
        String outputPath = null;

        // Обработка аргументов командной строки
        for (String arg : args) {
            if (arg.startsWith("--sort=") || arg.startsWith("-s=")) {
                sortParameter = arg.split("=")[1];
            } else if (arg.startsWith("--order=") || arg.startsWith("-o=")) {
                orderParameter = arg.split("=")[1];
            } else if (arg.startsWith("--output=") || arg.startsWith("-out=")) {
                outputParameter = arg.split("=")[1];
            } else if (arg.startsWith("--path=")) {
                outputPath = arg.split("=")[1];
            }
        }

        // Отладочные сообщения
        System.out.println("Параметр сортировки: " + sortParameter);
        System.out.println("Порядок сортировки: " + orderParameter);
        System.out.println("Параметр вывода: " + outputParameter);
        System.out.println("Путь к выходному файлу: " + outputPath);

        // Проверка на корректность параметров
        if (sortParameter != null && orderParameter == null) {
            System.out.println("Ошибка: порядок сортировки не указан для параметра сортировки: " + sortParameter);
            return;
        }

        if (orderParameter != null && !orderParameter.equals("asc") && !orderParameter.equals("desc")) {
            System.out.println("Ошибка: неверный параметр порядка сортировки: " + orderParameter);
            return;
        }

        if ("file".equals(outputParameter) && outputPath == null) {
            System.out.println("Ошибка: путь к выходному файлу не указан.");
            return;
        }

        if (outputParameter != null && !outputParameter.equals("console") && !outputParameter.equals("file")) {
            System.out.println("Ошибка: неверный параметр вывода: " + outputParameter);
            return;
        }

        // Обработка файла
        dataProcessor.processFile(filePath);

        // Печать результатов с учетом параметров сортировки
        dataProcessor.printResults(sortParameter, orderParameter, outputParameter, outputPath);
    }
}