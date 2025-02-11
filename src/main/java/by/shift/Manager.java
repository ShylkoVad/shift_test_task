package by.shift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Manager {

    private int id;
    private String name;
    private double salary;
    private String department;

    @Override
    public String toString() {
        return String.format("Manager,%d, %s, %.1f", id, name, salary);
    }
}
