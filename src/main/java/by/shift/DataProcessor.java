package by.shift;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataProcessor {

    Statistics statistics = new Statistics();
    private final ManagerProcessor managerProcessor = new ManagerProcessor(statistics);
    private final EmployeeProcessor employeeProcessor = new EmployeeProcessor(statistics);
    private final DataReader dataReader = new DataReader();

    public void processFile(String filePath) {

        // Сохраняем строки из файла в переменной lines
        List<String> lines = dataReader.readFile(filePath);

        // Сначала обрабатываем менеджеров
        for (String line : lines) {
            managerProcessor.processManagerLine(line);
        }

        // Получаем список менеджеров после обработки
        List<Manager> processedManagers = managerProcessor.getManagers();

        // Затем обрабатываем сотрудников
        for (String line : lines) {
            employeeProcessor.processEmployeeLine(line, processedManagers);
        }
    }

    public void printResults(String sortParameter, String orderParameter, String outputParameter, String outputPath) {
        StringBuilder output = new StringBuilder(); // Используем StringBuilder для накопления результатов
        List<Manager> managers = managerProcessor.getManagers();
        List<Employee> employees = employeeProcessor.getEmployees();

        // Сортируем менеджеров по имени их департамента
        managers.sort(Comparator.comparing(Manager::getDepartment));

        // Если параметр сортировки не указан, выводим данные в исходном порядке
        if (sortParameter == null || sortParameter.isEmpty()) {
            for (Manager manager : managers) {
                output.append(manager.getDepartment()).append("\n");
                output.append(manager).append("\n");

                // Получаем список сотрудников для данного менеджера
                List<Employee> employeesForManager = employees.stream()
                        .filter(e -> e.getManagerId() == manager.getId())
                        .collect(Collectors.toList());

                // Печатаем сотрудников в исходном порядке
                employeesForManager.forEach(e -> output.append(e).append("\n"));
                statistics.printStatisticsForDepartment(manager.getDepartment(), manager.getId(), output);
            }
        } else {
            // Используем EmployeeSorter для сортировки сотрудников
            for (Manager manager : managers) {
                output.append(manager.getDepartment()).append("\n");
                output.append(manager).append("\n");
                List<Employee> sortedEmployees = employees.stream()
                        .filter(e -> e.getManagerId() == manager.getId())
                        .collect(Collectors.toList());

                // Проверяем параметр сортировки и сортируем
                boolean ascending = "asc".equals(orderParameter);
                if ("name".equals(sortParameter)) {
                    EmployeeSorter.sortEmployees(sortedEmployees, EmployeeSorter.SortCriteria.NAME, ascending);
                } else if ("salary".equals(sortParameter)) {
                    EmployeeSorter.sortEmployees(sortedEmployees, EmployeeSorter.SortCriteria.SALARY, ascending);
                } else {
                    output.append("Ошибка: неизвестный параметр сортировки: ").append(sortParameter).append("\n");
                    continue; // Пропускаем текущую итерацию, если параметр неизвестен
                }

                // Печатаем отсортированных сотрудников
                sortedEmployees.forEach(e -> output.append(e).append("\n"));
                statistics.printStatisticsForDepartment(manager.getDepartment(), manager.getId(), output);
            }
        }

        // Вывод некорректных данных
        if (!employeeProcessor.getInvalidData().isEmpty()) {
            List<String> invalidData = employeeProcessor.getInvalidData();
            invalidData.sort(Comparator.comparing(data -> {
                String[] parts = data.split(",");
                return parts.length > 2 ? parts[2] : ""; // Проверяем на наличие имени
            }));

            output.append("Некорректные данные:\n");
            invalidData.forEach(data -> output.append(data).append("\n"));
        }


        // Выводим результаты
        if ("file".equals(outputParameter)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
                writer.write(output.toString());
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
        } else {
            System.out.print(output); // Вывод в консоль
        }
    }
}