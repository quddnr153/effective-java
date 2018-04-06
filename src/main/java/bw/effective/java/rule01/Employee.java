package bw.effective.java.rule01;

/**
 * @author Byungwook Lee on 2018-04-06
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class Employee {
    private String name;
    private String position;

    private Employee() {}

    private Employee(final String name, final String position) {
        this.name = name;
        this.position = position;
    }

    public static Employee getDeveloper(final String name) {
        return new Employee(name, "developer");
    }

    public static Employee getDesigner(final String name) {
        return new Employee(name, "designer");
    }
}
