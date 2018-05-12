package bw.effective.java.rule01;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Byungwook lee on 2018. 5. 12.
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class InterfaceWithStaticMethods {
    public static void main(String[] args) {
        // usage of static method 'of' in Stream interface
        List<Integer> numbers = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList());

        System.out.println(numbers);

        // usage of static method 'reverseOrder' in Comparator interface
        numbers.sort(Comparator.reverseOrder());

        System.out.println(numbers);
    }
}