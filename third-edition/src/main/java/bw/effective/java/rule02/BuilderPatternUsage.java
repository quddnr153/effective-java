package bw.effective.java.rule02;

import java.time.LocalDate;

/**
 * @author Byungwook lee on 2018. 5. 13.
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class BuilderPatternUsage {
    public static void main(String[] args) {
        Employee employee = Employee.builder("quddnr153", "Byungwook Lee").withSex(Employee.Sex.MALE).build();

        System.out.println(employee);
    }
}

class Employee {
    public enum Sex {
        MALE, FEMALE, NONE
    }

    private long seq;
    private String id;
    private String name;
    private String position;
    private String department;
    private LocalDate birthDay;
    private Sex sex;

    // Getter skip

    private Employee(Builder builder) {
        this.seq = builder.seq;
        this.id = builder.id;
        this.name = builder.name;
        this.position = builder.position;
        this.department = builder.department;
        this.birthDay = builder.birthDay;
        this.sex = builder.sex;
    }

    // id and name are required, others are optional
    static Builder builder(final String id, final String name) {
        return new Builder(id, name);
    }

    public static class Builder {
        private long seq;
        private String id;
        private String name;
        private String position;
        private String department;
        private LocalDate birthDay;
        private Sex sex;

        public Builder(final String id, final String name) {
            this.id = id;
            this.name = name;
        }

        public Builder withSeq(final long seq) {
            this.seq = seq;
            return this;
        }

        public Builder withPosition(final String position) {
            this.position = position;
            return this;
        }

        public Builder withDepartment(final String department) {
            this.department = department;
            return this;
        }

        public Builder withBirthDay(final LocalDate birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public Builder withSex(final Sex sex) {
            this.sex = sex;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee{");
        sb.append("seq=").append(seq);
        sb.append(", id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", position='").append(position).append('\'');
        sb.append(", department='").append(department).append('\'');
        sb.append(", birthDay=").append(birthDay);
        sb.append(", sex=").append(sex);
        sb.append('}');
        return sb.toString();
    }
}