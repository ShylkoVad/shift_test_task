package by.shift;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ManagerProcessor {

    @Getter
    private final List<Manager> managers = new ArrayList<>();
    private final Statistics statistics;
    private final List<String> invalidData = new ArrayList<>();
    private final ParseSalary parseSalary = new ParseSalary();

    public void processManagerLine(String line) {
        String[] parts = line.split(",");

        if (parts.length < 5) {
            invalidData.add(line);
            return; // Пропускаем некорректные строки
        }

        String type = parts[0].trim(); // Удаляем пробелы вокруг типа
        if (type.equalsIgnoreCase("Manager")) {
            try {
                int id = Integer.parseInt(parts[1].trim());
                String name = parts[2].trim();
                double salary = parseSalary.parseSalary(parts[3].trim());
                String department = parts[4].trim();

                if (salary > 0) { // Проверка на положительность зарплаты
                    Manager manager = new Manager(id, name, salary, department);
                    managers.add(manager);
                    statistics.addEmployee(department, salary);
                } else {
                    invalidData.add(line); // Если зарплата некорректная, добавляем в список
                }
            } catch (NumberFormatException e) {
                invalidData.add(line);
            }
        }
    }

}
