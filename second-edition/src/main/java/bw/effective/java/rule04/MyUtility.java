package bw.effective.java.rule04;

/**
 * @author Byungwook Lee on 2018-04-08
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public final class MyUtility {
    private MyUtility() {
        throw new AssertionError();
    }

    public static int plus(int x, int y) {
        return x + y;
    }
}
