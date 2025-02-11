package by.shift;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Statistics {

    private final Map<String, Double> departmentSalaries = new HashMap<>();
    private final Map<String, Integer> departmentCounts = new HashMap<>();

    // Метод для добавления информации о сотруднике
    public void addEmployee(String department, double salary) {
        departmentSalaries.put(department, departmentSalaries.getOrDefault(department, 0.00) + salary);
        departmentCounts.put(department, departmentCounts.getOrDefault(department, 0) + 1);
    }

    // Метод для вывода статистики по конкретному департаменту
    public void printStatisticsForDepartment(String department, int idDepartment, StringBuilder output) {
        if (!departmentSalaries.containsKey(department) || !departmentCounts.containsKey(department)) {
            output.append("Статистика для департамента '").append(department).append("' не найдена.\n");
            return;
        }

        double totalSalary = departmentSalaries.get(department);
        int count = departmentCounts.get(department);
        double averageSalary = totalSalary / count;

        // Форматируем вывод с точкой в качестве десятичного разделителя
        output.append(String.format(Locale.US, "%d, %.2f\n\n", idDepartment, averageSalary));
    }
}