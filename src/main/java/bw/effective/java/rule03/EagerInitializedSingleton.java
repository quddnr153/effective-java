package bw.effective.java.rule03;

/**
 * @author Byungwook Lee on 2018-04-08
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class EagerInitializedSingleton {
    private static final EagerInitializedSingleton INSTANCE = new EagerInitializedSingleton();

    // private constructor to avoid client applications to use constructor
    private EagerInitializedSingleton() {}

    public static EagerInitializedSingleton getInstance(){
        return INSTANCE;
    }
}
