package bw.effective.java.rule01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Byungwook lee on 2018. 5. 12.
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class ComparatorInterfaceUsage {
    public static void main(String[] args) {
        Employee employee1 = new Employee("abcd", 10, 60);
        Employee employee2 = new Employee("abcd", 11, 50);
        Employee employee3 = new Employee("abcd", 9, 12);

        List<Employee> employees = Arrays.asList(employee1, employee2, employee3);

        System.out.println(employees);

        employees.sort((
                (o1, o2) -> Comparator.comparing(Employee::getName)
                        .thenComparing(Employee::getAge)
                        .thenComparing(Employee::getWeight)
                        .compare(o1, o2)
        ));

        System.out.println(employees);
    }
}

class Employee {
    private String name;
    private int age;
    private int weight;

    public Employee(String name, int age, int weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", weight=").append(weight);
        sb.append('}');
        return sb.toString();
    }
}