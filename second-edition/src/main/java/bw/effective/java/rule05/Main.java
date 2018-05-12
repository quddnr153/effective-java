package bw.effective.java.rule05;

import java.util.stream.IntStream;

/**
 * @author Byungwook Lee on 2018-04-08
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(java8Sum(1, 10));
    }

    private static int inefficientSum(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from must be less than to");
        }

        Integer sum = 0;

        for (int i = from; i <= to; i++) {
            sum += i;
        }

        return sum;
    }

    private static int efficientSum(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from must be less than to");
        }

        int sum = 0;

        for (int i = from; i<= to; i++) {
            sum += i;
        }

        return sum;
    }

    private static int java8Sum(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from must be less than to");
        }

        return IntStream.range(from, to + 1).sum();
    }
}
