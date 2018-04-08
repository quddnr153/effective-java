package bw.effective.java.rule03;

/**
 * @author Byungwook Lee on 2018-04-08
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public enum EnumSingleton {
    INSTANCE;

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
