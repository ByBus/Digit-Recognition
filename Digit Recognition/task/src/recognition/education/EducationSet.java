package recognition.education;

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
}
