package bw.effective.java.rule03;

/**
 * @author Byungwook Lee on 2018-04-08
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class StaticBlockSingleton {
    private static StaticBlockSingleton INSTANCE;

    private StaticBlockSingleton() {}

    // static block initialization for exception handling
    static{
        try{
            INSTANCE = new StaticBlockSingleton();
        }catch(Exception exception){
            throw new IllegalStateException("Exception occurred in creating singleton instance");
        }
    }

    public static StaticBlockSingleton getInstance(){
        return INSTANCE;
    }
}