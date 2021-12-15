package recognition.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    private static final int B = 1;
    private static final int W = 0;
    private static final Map<Integer, int[][]> IDEAL_VALUES_OF_DIGITS = new HashMap<>();

    static {
        int[][] zero = {{B, B, B}, {B, W, B}, {B, W, B}, {B, W, B}, {B, B, B}};
        IDEAL_VALUES_OF_DIGITS.put(0, zero);
        int[][] one = {{W, B, W}, {W, B, W}, {W, B, W}, {W, B, W}, {W, B, W}};
        IDEAL_VALUES_OF_DIGITS.put(1, one);
        int[][] two = {{B, B, B}, {W, W, B}, {B, B, B}, {B, W, W}, {B, B, B}};
        IDEAL_VALUES_OF_DIGITS.put(2, two);
        int[][] three = {{B, B, B}, {W, W, B}, {B, B, B}, {W, W, B}, {B, B, B}};
        IDEAL_VALUES_OF_DIGITS.put(3, three);
        int[][] four = {{B, W, B}, {B, W, B}, {B, B, B}, {W, W, B}, {W, W, B}};
        IDEAL_VALUES_OF_DIGITS.put(4, four);
        int[][] five = {{B, B, B}, {B, W, W}, {B, B, B}, {W, W, B}, {B, B, B}};
        IDEAL_VALUES_OF_DIGITS.put(5, five);
        int[][] six = {{B, B, B}, {B, W, W}, {B, B, B}, {B, W, B}, {B, B, B}};
        IDEAL_VALUES_OF_DIGITS.put(6, six);
        int[][] seven = {{B, B, B}, {W, W, B}, {W, W, B}, {W, W, B}, {W, W, B}};
        IDEAL_VALUES_OF_DIGITS.put(7, seven);
        int[][] eight = {{B, B, B}, {B, W, B}, {B, B, B}, {B, W, B}, {B, B, B}};
        IDEAL_VALUES_OF_DIGITS.put(8, eight);
        int[][] nine = {{B, B, B}, {B, W, B}, {B, B, B}, {W, W, B}, {B, B, B}};
        IDEAL_VALUES_OF_DIGITS.put(9, nine);
    }

    public static int[] getValuesOfDigit(int digit) {
        return Arrays.stream(IDEAL_VALUES_OF_DIGITS.get(digit))
                .flatMapToInt(Arrays::stream)
                .toArray();
    }
}
