package bw.effective.java.rule11;

import java.util.Objects;

/**
 * @author Byungwook lee on 2018. 5. 14.
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class Employee {
    private int seq;
    private String name;
    private String id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Employee employee = (Employee) obj;
        return seq == employee.seq && Objects.equals(name, employee.name) && Objects.equals(id, employee.id);
    }

    // hashCode method with lazily initialized cached hash code
    // Automatically initialized to 0
    private int hashCode;

    @Override
    public int hashCode() {
        int result = hashCode;

        if (result == 0) {
            result = Integer.hashCode(seq);
            result = 31 * result + name.hashCode();
            result = 31 * result + id.hashCode();
        }

        return result;
    }
}
