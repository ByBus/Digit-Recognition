package recognition.utils;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReadInput {
    public static double[] read() {
        Scanner sc = new Scanner(System.in);
        return Stream.generate(sc::next)
                .limit(5)
                .flatMapToDouble(line -> Arrays.stream(processLine(line)))
                .toArray();
    }

    private static double[] processLine(String line) {
        return IntStream.range(0, line.length())
                .mapToDouble(i -> line.charAt(i) == 'X' ? 1 : 0)
                .toArray();
    }
}
