package bw.effective.java.rule02;

import java.time.LocalDate;

/**
 * @author Byungwook Lee on 2018-04-07
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class Employee01 {
    private long seq;
    private String id;
    private String name;
    private String position;
    private String department;
    private LocalDate birthDay;
    private String sex;

    public Employee01(long seq, String id, String name) {
        this.seq = seq;
        this.id = id;
        this.name = name;
    }

    public Employee01(long seq, String id, String name, String position) {
        this(seq, id, name);
        this.position = position;
    }

    public Employee01(long seq, String id, String name, String position, String department) {
        this(seq, id, name, position);
        this.department = department;
    }

    public Employee01(long seq, String id, String name, String position, String department, LocalDate birthDay) {
        this(seq, id, name, position, department);
        this.birthDay = birthDay;
    }

    public Employee01(long seq, String id, String name, String position, String department, LocalDate birthDay, String sex) {
        this(seq, id, name, position, department, birthDay);
        this.sex = sex;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
