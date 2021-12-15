package recognition;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Recogniser {
    private final Memory memory;

    public Recogniser(Memory memory) {
        this.memory = memory;
    }

    private int getDigit(double[] layerTwo) {
        double max = Arrays.stream(layerTwo)
                .max()
                .orElse(0);
        return Arrays.stream(layerTwo)
                .boxed()
                .collect(Collectors.toList())
                .indexOf(max);
    }

    private double calculateWeights(Neuron neuron, int[] layerOne) {
        return IntStream.range(0, layerOne.length)
                .mapToDouble(i -> layerOne[i] * neuron.weights[i])
                .sum();
    }

    public int recognise(int[] layerOne) {
        double[] layer2 = IntStream.rangeClosed(0, 9)
                .mapToDouble(i -> calculateWeights(memory.getNeurons()[i], layerOne))
                .toArray();
        return getDigit(layer2);
    }
}
