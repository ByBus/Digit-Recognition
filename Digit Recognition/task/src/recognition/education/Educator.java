package recognition.education;

import recognition.Recogniser;
import recognition.network.Layer;
import recognition.network.Network;
import recognition.network.Neuron;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class Educator {
    private static final double EPOCHS = 10;
    private static final double LEARNING_RATE = 0.7;
    private final DoubleUnaryOperator sigmoid = x -> 1.0 / (1.0 + Math.pow(Math.E, -x));
    private final DoubleUnaryOperator sigmoidDerivative = x -> (1 - x) * x;

    private final Network network;

    public Educator(Network network) {
        this.network = network;
    }

    private void updateNetwork(EducationSet education) {
        for (int i = 0; i < network.layerCount(); i++) {
            Layer currentLayer = network.getLayer(i);
            if (currentLayer.getType() == Layer.Type.INPUT) {
                currentLayer.setNeuronsValues(education.getInput());
            } else {
                Layer previousLayer = network.getLayer(i - 1);
                calculateOutputs(currentLayer, previousLayer);
            }
        }
    }

    private double calculateError(EducationSet education) {
        Layer lastLayer = network.getOutputLayer();
        double[] idealOutput = education.getOutput();
        return IntStream.range(0, lastLayer.getSize())
                .mapToDouble(i -> Math.pow(idealOutput[i] - lastLayer.getNeuron(i).getValue(), 2))
                .sum();
    }

    private void calculateOutputs(Layer currentLayer, Layer previousLayer) {
        for (int i = 0; i < currentLayer.getSize(); i++) {
            Neuron neuron = currentLayer.getNeuron(i);
            double value = calculateValue(neuron, previousLayer);

            Neuron bias = previousLayer.getBias();
            double biasValue = bias.getValue() * bias.getWeight(i);
            neuron.setValue(sigmoid.applyAsDouble(value + biasValue));
        }
    }

    private double calculateValue(Neuron neuron, Layer previousLayer) {
        return IntStream.range(0, neuron.inputsCount())
                .mapToDouble(j -> previousLayer.getNeuron(j).getValue() * neuron.getWeight(j))
                .sum();
    }

    private void backPropagation(EducationSet education) {
        double[] deltas = new double[0];
        for (int i = network.layerCount() - 1; i >= 0; i--) {
            Layer layer = network.getLayer(i);
            if (layer.getType() == Layer.Type.OUTPUT) {
                double[] idealOutput = education.getOutput();
                deltas = calculateDeltas(idealOutput, layer.getNeuronsValues());
            } else {
                Layer nextLayer = network.getLayer(i + 1);
                updateWeights(layer, nextLayer, deltas);
                deltas = calculateDeltas(layer, nextLayer, deltas);
            }
        }
    }

    private void updateWeights(Layer currentLayer, Layer nextLayer, double[] deltasOfNextLayer) {
        for (int i = 0; i < nextLayer.getSize(); i++) {
            Neuron neuron = nextLayer.getNeuron(i);
            double[] gradients = calculateGradients(currentLayer, deltasOfNextLayer[i]);
            updateNeuronWeights(neuron, gradients);

            Neuron bias = currentLayer.getBias();
            double biasGradient = bias.getValue() * deltasOfNextLayer[i];
            bias.increaseWeight(i, LEARNING_RATE * biasGradient);
        }
    }

    private double[] calculateGradients(Layer currentLayer, double delta) {
        return IntStream.range(0, currentLayer.getSize())
                .mapToDouble(j -> currentLayer.getNeuron(j).getValue() * delta)
                .toArray();
    }

    private void updateNeuronWeights(Neuron neuron, double[] gradients) {
        IntStream.range(0, neuron.inputsCount())
                .forEach(i -> neuron.increaseWeight(i, LEARNING_RATE * gradients[i]));
    }

    private double[] calculateDeltas(Layer currentLayer, Layer nextLayer, double[] deltaNextLayer) {
        double[] deltas = new double[currentLayer.getSize()];
        for (int i = 0; i < currentLayer.getSize(); i++) {
            int k = i;
            double sumWeightsDeltas = IntStream.range(0, nextLayer.getSize())
                    .mapToDouble(j -> nextLayer.getNeuron(j).getWeight(k) * deltaNextLayer[j])
                    .sum();
            deltas[i] = sigmoidDerivative.applyAsDouble(currentLayer.getNeuron(i).getValue()) * sumWeightsDeltas;
        }
        return deltas;
    }

    private double[] calculateDeltas(double[] idealOutput, double[] actualOutput) {
        return IntStream.range(0, idealOutput.length)
                .mapToDouble(i -> (idealOutput[i] - actualOutput[i])
                        * sigmoidDerivative.applyAsDouble(actualOutput[i]))
                .toArray();
    }

    public void train() {
        try {
            EducationSet[] educationSets = TrainData.getEducationalSets();

            long start = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(start);
            System.out.println("Education start: " + timestamp);

            for (int epoch = 0; epoch < EPOCHS; epoch++) {
                for (var batch = 0; batch < 70; batch++) {
                    double mse = 0; // Mean squared error
                    for (var k = 0; k < 1000; k++) {
                        var education = educationSets[batch * 1000 + k];
                        updateNetwork(education);
                        mse += calculateError(education);
                        backPropagation(education);
                    }
                    System.out.println("MSE: " + mse / 1000);
                }
            }

            Duration duration = Duration.ofMillis(System.currentTimeMillis() - start);
            System.out.printf("Duration: %02d:%02d:%02d%n",
                    duration.toHours(),
                    duration.toMinutesPart(),
                    duration.toSecondsPart());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateNetworkValues(double[] inputValues) {
        EducationSet input = new EducationSet(inputValues, new double[0]);
        updateNetwork(input);
    }

    public int[] calculateAccuracy() throws IOException {
        EducationSet[] educationalSets = TrainData.getEducationalSets();
        Recogniser recogniser = new Recogniser(network);
        int counter = (int) Arrays.stream(educationalSets)
                .filter(education ->
                        recogniser.recognise(education) == education.getValue())
                .count();
        return new int[]{counter, educationalSets.length};
    }
}