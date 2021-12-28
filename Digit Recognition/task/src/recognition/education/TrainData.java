package recognition.education;

import java.util.stream.IntStream;

public class TrainData {
    private static final double B = 1;
    private static final double W = 0;

    private static final double[][] IDEAL_INPUTS = {
            {W, B, W, W, B, W, B, B, W, B, B, W, W, B, B},
            {B, B, W, W, W, B, W, W, B, B, W, W, B, B, B},
            {B, B, B, B, W, B, W, W, B, W, W, B, W, W, B},
            {B, B, B, B, W, B, B, B, B, W, W, B, W, B, B},
            {B, B, B, B, W, B, B, W, B, B, W, B, W, B, B},
            {B, B, B, W, W, B, B, B, B, W, W, B, W, B, B},
            {B, W, B, W, W, B, B, B, B, W, W, B, W, W, B},
            {B, B, B, B, W, W, B, B, B, W, W, B, B, B, W},
            {B, B, B, B, W, B, B, B, B, B, W, B, B, B, W},
            {W, B, B, B, W, W, B, B, B, B, W, B, B, B, B},
            {B, B, B, B, W, B, B, W, B, B, W, B, B, B, B},
            {W, B, W, W, B, W, W, B, W, W, B, W, W, B, W},
            {B, B, B, W, W, B, B, B, B, B, W, W, B, B, B},
            {B, B, B, W, W, B, B, B, B, W, W, B, B, B, B},
            {B, W, B, B, W, B, B, B, B, W, W, B, W, W, B},
            {B, B, B, B, W, W, B, B, B, W, W, B, B, B, B},
            {B, B, B, B, W, W, B, B, B, B, W, B, B, B, B},
            {B, B, B, W, W, B, W, W, B, W, W, B, W, W, B},
            {B, B, B, B, W, B, B, B, B, B, W, B, B, B, B},
            {B, B, B, B, W, B, B, B, B, W, W, B, B, B, B},
    };

    private static final double[][] IDEAL_OUTPUTS = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    };

    public static EducationSet[] getEducationalSets() {
        return IntStream.range(0, IDEAL_INPUTS.length)
                .mapToObj(i -> new EducationSet(IDEAL_INPUTS[i], IDEAL_OUTPUTS[i]))
                .toArray(EducationSet[]::new);
    }
}
