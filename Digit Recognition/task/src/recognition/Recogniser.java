package recognition;

import recognition.network.Layer;
import recognition.network.Network;
import recognition.network.Neuron;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Recogniser {
    private final Memory memory;
    private final DoubleUnaryOperator sigmoid = x -> 1.0 / (1.0 + Math.pow(Math.E, -x));

    public Recogniser(Memory memory) {
        this.memory = memory;
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

    private double calculateValue(Neuron neuron, double[] layerValues) {
        return IntStream.range(0, layerValues.length)
                .mapToDouble(i -> layerValues[i] * neuron.weights[i])
                .sum();
    }

    public int recognize(double[] inputValues) {
        Network network = memory.getNetwork();
        Layer[] layers = network.getLayers();
        network.getInputLayer().setNeuronsValues(inputValues);
        for (int i = 1; i < layers.length; i++) {
            double[] previousValues = layers[i - 1].getNeuronsValues();
            for (Neuron neuron : layers[i].getNeurons()) {
                neuron.value = sigmoid.applyAsDouble(calculateValue(neuron, previousValues));
            }
        }
        return getDigit(network.getOutputLayer().getNeuronsValues());
    }
}
