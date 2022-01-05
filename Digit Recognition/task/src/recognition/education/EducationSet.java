package recognition.education;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class EducationSet {
    private final double[] input;
    private final double[] output;

    public EducationSet(double[] input, double[] output) {
        this.input = input;
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }

    public double[] getOutput() {
        return output;
    }

    public int getValue() {
        return DoubleStream.of(output)
                .boxed()
                .collect(Collectors.toList())
                .indexOf(1.0);
    }

    @Override
    public String toString() {
        return input.length + " " + Arrays.toString(output);
    }
}
