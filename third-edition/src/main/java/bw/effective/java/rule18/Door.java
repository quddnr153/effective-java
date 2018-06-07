package bw.effective.java.rule18;

/**
 * @author Byungwook lee on 2018. 6. 8.
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class Door {
    private String name;

    public Door(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean openTheDoor(String name) {
        return this.name.equals(name);
    }
}
