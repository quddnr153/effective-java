package bw.effective.java.rule03;

/**
 * @author Byungwook Lee on 2018-04-08
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class BillPughSingleton {
    private BillPughSingleton() {}

    private static class SingletonHelper{
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance(){
        return SingletonHelper.INSTANCE;
    }
}