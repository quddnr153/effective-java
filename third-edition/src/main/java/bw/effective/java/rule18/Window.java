package bw.effective.java.rule18;

/**
 * @author Byungwook lee on 2018. 6. 8.
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class Window {
    private String name;
    private String type;

    public boolean openTheWindow(String name) {
        return this.name.equals(name);
    }
}
