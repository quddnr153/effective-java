package bw.effective.java.rule18;

import java.util.List;

/**
 * @author Byungwook lee on 2018. 6. 8.
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class House {
    private List<Door> doors;
    private List<Window> windows;

    public boolean openTheDoor(String name) {
        return doors.stream()
                .filter(door -> door.getName().equals(name))
                .findFirst()
                .orElseGet(() -> new Door(name))
                .openTheDoor(name);
    }
}
