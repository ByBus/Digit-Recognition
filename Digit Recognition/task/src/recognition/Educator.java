package recognition;

import recognition.utils.Constants;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class Educator {
    private final DoubleUnaryOperator sigmoid = x -> 1.0 / (1.0 + Math.pow(Math.E, -x));
    private final Memory memory;
    private final Neuron[] neurons;

    public Educator(Memory memory) {
        this.memory = memory;
        neurons = memory.getReadNeurons();
        if (Arrays.stream(neurons).anyMatch(Objects::isNull)) {
            generateNeurons();
        }
    }

    public void generateNeurons() {
        for (int i = 0; i < 10; i++) {
            neurons[i] = new Neuron(i);
        }
        memory.addNeurons(neurons);
    }

    public void learn() {
        IntStream.range(0, 1000)
                .forEach(i -> generateWeights());
    }

    private void generateWeights() {
        for (Neuron neuron : neurons) {
            double[] deltaWs = new double[15];
            double[] deltaWsMeans = new double[15];
            for (int digit = 0; digit < 10; digit++) {
                int[] testDigitInputs = Constants.getValuesOfDigit(digit);
                generateNeuronValue(neuron, testDigitInputs);
                calculateDeltaWs(neuron, testDigitInputs, digit, deltaWs);
                calculateDeltaWsMeans(deltaWs, deltaWsMeans);
            }
            calculateNewWeights(neuron, deltaWsMeans);
        }
    }

    private void calculateDeltaWsMeans(double[] deltaW, double[] mean) {
        for (int j = 0; j < mean.length; j++) {
            mean[j] += deltaW[j] / 10;
        }
    }

    private void calculateDeltaWs(Neuron neuron, int[] digitWeights, int digit, double[] deltaW) {
        int idealValue = neuron.number == digit ? 1 : 0;
        //int[] ideal = Constants.getValuesOfDigit(neuron.number);
        for (int a = 0; a < 15; a++) {
            deltaW[a] = 0.5 * digitWeights[a] * (idealValue - neuron.value);
        }
    }

    private void calculateNewWeights(Neuron neuron, double[] weightsMean) {
        for (int i = 0; i < 15; i++) {
            neuron.weights[i] += weightsMean[i];
        }
    }

    private void generateNeuronValue(Neuron neuron, int[] idealWeights) {
        double sum = IntStream.range(0, idealWeights.length)
                .mapToDouble(i -> idealWeights[i] * neuron.weights[i])
                .sum();
        neuron.value = sigmoid.applyAsDouble(sum);
    }

    public Memory getMemory() {
        return memory;
    }
}
