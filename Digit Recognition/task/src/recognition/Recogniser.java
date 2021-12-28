package recognition;

import recognition.network.Layer;
import recognition.network.Network;
import recognition.network.Neuron;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Recogniser {
    private final Network network;
    private final DoubleUnaryOperator sigmoid = x -> 1.0 / (1.0 + Math.pow(Math.E, -x));

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

    private double calculateValue(Neuron neuron, Layer prevLayer) {
        return IntStream.range(0, prevLayer.getSize())
                .mapToDouble(i -> prevLayer.getNeuron(i).value * neuron.getWeight(i))
                .sum();
    }

    public int recognise(double[] inputValues) {
        network.getInputLayer().setNeuronsValues(inputValues);
        for (int i = 1; i < network.layerCount(); i++) {
            Layer currentLayer = network.getLayer(i);
            Layer previousLayer = network.getLayer(i - 1);
            Neuron bias = previousLayer.getBias();

            for (int j = 0; j < currentLayer.getSize(); j++) {
                Neuron neuron = currentLayer.getNeuron(j);
                double biasWeight = bias.getWeight(j);
                neuron.value = sigmoid.applyAsDouble(calculateValue(neuron, previousLayer) + biasWeight);
            }
        }
        return getDigit(network.getOutputLayer().getNeuronsValues());
    }
}
