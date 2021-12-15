package recognition.utils;

import java.util.Scanner;
import java.util.stream.IntStream;

public class ReadInput {
    public static void fillFirstLayer(int[] layerOne) {
        Scanner sc = new Scanner(System.in);
        int i = 0;
        while (i < 5) {
            int[] line = processLine(sc.next());
            System.arraycopy(line, 0, layerOne, i * line.length, line.length);
            i++;
        }
    }

    private static int[] processLine(String line) {
        char[] lineChars = line.toCharArray();
        return IntStream.range(0, lineChars.length)
                .map(i -> lineChars[i] == 'X' ? 1 : 0)
                .toArray();
    }
}
