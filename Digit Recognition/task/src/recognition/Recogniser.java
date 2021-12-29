package recognition;

import recognition.education.Educator;
import recognition.network.Network;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Recogniser {
    private final Network network;

    public Recogniser(Network network) {
        this.network = network;
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
        Educator educator = new Educator(network);
        educator.calculateNetworkValues(inputValues);

        return getDigit(network.getOutputLayer().getNeuronsValues());
    }
}
