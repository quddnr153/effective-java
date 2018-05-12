package bw.effective.java.rule03;

/**
 * @author Byungwook Lee on 2018-04-08
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class LazyInitializedSingleton {
    private static LazyInitializedSingleton INSTANCE;

    private LazyInitializedSingleton(){}

    public static LazyInitializedSingleton getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new LazyInitializedSingleton();
        }

        return INSTANCE;
    }
}