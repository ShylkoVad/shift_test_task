package by.shift;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeProcessor {

    @Getter
    private final List<String> invalidData = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();
    private final Statistics statistics;
    private final ParseSalary parseSalary = new ParseSalary();

    // Метод для обработки строки с сотрудником
    public void processEmployeeLine(String line, List<Manager> managers) {
        String[] parts = line.split(",");
        if (parts.length < 5) {
            invalidData.add(line);
            return; // Пропускаем некорректные строки
        }

        String type = parts[0].trim();
        if (type.equalsIgnoreCase("Employee")) {
            try {
                int id = Integer.parseInt(parts[1].trim());
                String name = parts[2].trim();
                double salary = parseSalary.parseSalary(parts[3].trim());
                int managerId = Integer.parseInt(parts[4].trim());

                if (salary > 0) { // Проверка на положительность зарплаты
                    Employee employee = new Employee(id, name, salary, managerId);
                    employees.add(employee);

                    // Найти департамент менеджера для статистики
                    for (Manager manager : managers) {
                        if (manager.getId() == managerId) {
                            statistics.addEmployee(manager.getDepartment(), salary);
                            break;
                        }
                    }
                } else {
                    invalidData.add(line); // Если зарплата некорректная, добавляем в список
                }
            } catch (NumberFormatException e) {
                invalidData.add(line); // Добавляем некорректные строки
            }
        }
    }
}
