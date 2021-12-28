package recognition.education;

import recognition.network.Layer;
import recognition.network.Network;
import recognition.network.Neuron;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class Educator {
    private final DoubleUnaryOperator sigmoid = x -> 1.0 / (1.0 + Math.pow(Math.E, -x));
    private final DoubleUnaryOperator sigmoidDerivative = x -> (1 - x) * x;
    private static final double EDUCATION_FACTOR = 0.5;
    private static final double EPOCHS = 1000;

    private final Network network;

    public Educator(Network network) {
        this.network = network;
    }

    private double checkNetwork(EducationSet education) {
        for (int i = 0; i < network.layerCount(); i++) {
            Layer currentLayer = network.getLayer(i);
            if (currentLayer.getType() == Layer.Type.INPUT) {
                currentLayer.setNeuronsValues(education.getInput());
            } else {
                Layer previousLayer = network.getLayer(i - 1);
                calculateOutput(currentLayer, previousLayer);
            }
        }
        return calculateError(network.getOutputLayer(), education.getOutput());
    }

    private double calculateError(Layer lastLayer, double[] idealOutput) {
        return IntStream.range(0, lastLayer.getSize())
                .mapToDouble(i -> Math.pow(idealOutput[i] - lastLayer.getNeuron(i).getValue(), 2))
                .sum();
    }

    private void calculateOutput(Layer currentLayer, Layer previousLayer) {
        for (int i = 0; i < currentLayer.getSize(); i++) {
            Neuron neuron = currentLayer.getNeuron(i);
            double sum = IntStream.range(0, neuron.inputCount())
                    .mapToDouble(j -> previousLayer.getNeuron(j).getValue() * neuron.getWeight(j))
                    .sum();

            Neuron bias = previousLayer.getBias();
            neuron.setValue(sigmoid.applyAsDouble(sum + bias.getWeight(i))); //+ bias.weights[i]
        }
    }

    private void backPropagation(EducationSet education) {
        double[] deltas = new double[0];
        for (int i = network.layerCount() - 1; i >= 0; i--) {
            Layer layer = network.getLayer(i);
            if (layer.getType() == Layer.Type.OUTPUT) {
                double[] idealOutput = education.getOutput();
                deltas = calculateDelta(idealOutput, layer.getNeuronsValues());
            } else {
                Layer nextLayer = network.getLayer(i + 1);
                updateWeights(layer, nextLayer, deltas);
                deltas = calculateDelta(layer, nextLayer, deltas);
            }
        }
    }

    private void updateWeights(Layer currentLayer, Layer nextLayer, double[] deltasOfNextLayer) {
        for (int i = 0; i < nextLayer.getSize(); i++) {
            Neuron neuron = nextLayer.getNeuron(i);
            int k = i;
            double[] gradients = IntStream.range(0, neuron.inputCount())
                    .mapToDouble(j -> currentLayer.getNeuron(j).getValue() * deltasOfNextLayer[k])
                    .toArray();
            updateNeuronWeights(neuron, gradients);

            Neuron bias = currentLayer.getBias();
            double biasGradient = bias.getValue() * deltasOfNextLayer[i];
            bias.increaseWeight(i, EDUCATION_FACTOR * biasGradient);
        }
    }

    private void updateNeuronWeights(Neuron neuron, double[] gradient) {
        IntStream.range(0, neuron.inputCount())
                .forEach(i -> neuron.increaseWeight(i, EDUCATION_FACTOR * gradient[i]));
    }

    private double[] calculateDelta(Layer currentLayer, Layer nextLayer, double[] deltaNextLayer) {
        double[] deltas = new double[currentLayer.getSize()];
        for (int i = 0; i < currentLayer.getSize(); i++) {
            double sum = 0;
            for (int j = 0; j < nextLayer.getSize(); j++) {
                sum += nextLayer.getNeuron(j).getWeight(i) * deltaNextLayer[j];
            }
            deltas[i] = sigmoidDerivative.applyAsDouble(currentLayer.getNeuron(i).getValue()) * sum;
        }
        return deltas;
    }

    private double[] calculateDelta(double[] idealOutput, double[] actualOutput) {
        return IntStream.range(0, idealOutput.length)
                .mapToDouble(i -> (idealOutput[i] - actualOutput[i])
                        * sigmoidDerivative.applyAsDouble(actualOutput[i]))
                .toArray();
    }

    public void train() {
        EducationSet[] educationSets = TrainData.getEducationalSets();
        for (int epoch = 0; epoch < EPOCHS; epoch++) {
            double mse = 0;
            for (var education : educationSets) {
                mse += checkNetwork(education);
                backPropagation(education);
            }
            System.out.println("MSE: " + mse / educationSets.length);
        }
    }
}
