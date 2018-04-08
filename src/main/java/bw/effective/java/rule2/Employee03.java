package bw.effective.java.rule2;

import java.time.LocalDate;

/**
 * @author Byungwook Lee on 2018-04-07
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class Employee03 {
    private long seq;
    private String id;
    private String name;
    private String position;
    private String department;
    private LocalDate birthDay;
    private String sex;

    private Employee03(Builder builder) {
        this.seq = builder.seq;
        this.id = builder.id;
        this.name = builder.name;
        this.position = builder.position;
        this.department = builder.department;
        this.birthDay = builder.birthDay;
        this.sex = builder.sex;
    }

    public static class Builder {
        private long seq;
        private String id;
        private String name;
        private String position;
        private String department;
        private LocalDate birthDay;
        private String sex;

        public Builder(final long seq, final String id, final String name) {
            this.seq = seq;
            this.id = id;
            this.name = name;
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

        public Builder withSex(final String sex) {
            this.sex = sex;
            return this;
        }

        public Employee03 build() {
            return new Employee03(this);
        }
    }
}
