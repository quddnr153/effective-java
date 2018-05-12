package bw.effective.java.rule20;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Byungwook Lee on 2018-04-23
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class StrategyPattern {
    public static void main(String[] args) {
        List<String> books = Arrays.asList("effective java", "refactoring", "design patterns");

        books.sort(Comparator.comparingInt(String::length));

        System.out.println(books);

        books.sort(Comparator.naturalOrder());

        System.out.println(books);
    }
}
