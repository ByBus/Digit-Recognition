package recognition;

import recognition.education.EducationSet;
import recognition.education.Educator;
import recognition.network.Network;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Recogniser {
    private final Network network;
    private final Educator educator;

    public Recogniser(Network network) {
        this.network = network;
        this.educator = new Educator(network);
    }

    private int getDigit(double[] outputLayer) {
        double max = Arrays.stream(outputLayer)
                .max()
                .orElse(0);
        return Arrays.stream(outputLayer)
                .boxed()
                .collect(Collectors.toList())
                .indexOf(max);
    }

    public int recognise(double[] inputValues) {
        educator.calculateNetworkValues(Arrays.stream(inputValues).map(n -> n / 255).toArray());
        return getDigit(network.getOutputLayer().getNeuronsValues());
    }

    public int recognise(EducationSet education) {
        educator.calculateNetworkValues(education.getInput());
        return getDigit(network.getOutputLayer().getNeuronsValues());
    }
}
