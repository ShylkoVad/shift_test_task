package by.shift;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeSorter {

    public enum SortCriteria {
        NAME,
        SALARY
    }

    public static void sortEmployees(List<Employee> employees, SortCriteria criteria, boolean ascending) {
        Comparator<Employee> comparator;

        switch (criteria) {
            case NAME:
                comparator = Comparator.comparing(Employee::getName);
                break;
            case SALARY:
                comparator = Comparator.comparingDouble(Employee::getSalary);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный критерий сортировки: " + criteria);
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        Collections.sort(employees, comparator);
    }
}
