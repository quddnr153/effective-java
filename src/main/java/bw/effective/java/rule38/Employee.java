package bw.effective.java.rule38;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Byungwook Lee on 2018-04-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class Employee implements Comparable<Employee> {
    private long seq;
    private String id;
    private String name;
    private String email;

    private Employee(Builder builder) {
        this.seq = builder.seq;
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
    }

    public long getSeq() {
        return seq;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {

        private long seq;
        private String id;
        private String name;
        private String email;
        public Builder(long seq, String id) {
            if (seq <= 0) {
                throw new IllegalArgumentException("seq must be bigger than 1.");
            }

            this.seq = seq;
            this.id = id;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(final String email) {
            this.email = email;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Employee)) {
            return false;
        }

        Employee employee = (Employee) o;

        return seq == employee.seq &&
                Objects.equals(id, employee.id) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, id, name, email);
    }

    @Override
    public String toString() {
        return "Employee(seq=" + seq + ", id=" + id + ", name=" + name + ", email=" + email + ")";
    }

    @Override
    public int compareTo(Employee target) {
        return Comparator.comparingLong(Employee::getSeq)
                         .thenComparing(Employee::getId)
                         .thenComparing(Employee::getName)
                         .thenComparing(Employee::getEmail)
                         .compare(this, target);
    }
}
