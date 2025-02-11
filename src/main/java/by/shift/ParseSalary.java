package by.shift;

public class ParseSalary {

    public double parseSalary(String salaryStr) {
        if (salaryStr.isEmpty()) return 0.0; // Если зарплата не указана, считаем 0
        try {
            double salary = Double.parseDouble(salaryStr);
            return salary > 0 ? salary : 0.0; // Если зарплата отрицательная, считаем 0
        } catch (NumberFormatException e) {
            return 0.0; // Если не удалось распарсить, считаем 0
        }
    }
}
