package bw.effective.java.rule51;

/**
 * @author Byungwook lee on 2018. 5. 5.
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class StringConcatenation {
    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();
        usePlusConcatenation();
        long end1 = System.currentTimeMillis();

        System.out.println(end1 - start1);

        long start2 = System.currentTimeMillis();
        useStringBuilderConcatenation();
        long end2 = System.currentTimeMillis();

        System.out.println(end2 - start2);
    }

    public static String usePlusConcatenation() {
        String result = "";

        for (int i = 0; i < 100_000; i++) {
            result += i;
        }

        return result;
    }

    public static String useStringBuilderConcatenation() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 100_000; i++) {
            builder.append(i);
        }

        return builder.toString();
    }
}
