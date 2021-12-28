package recognition.education;

import recognition.Memory;
import recognition.network.Layer;
import recognition.network.Network;
import recognition.network.Neuron;
import recognition.utils.Constants;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class Educator {
    private final DoubleUnaryOperator sigmoid = x -> 1.0 / (1.0 + Math.pow(Math.E, -x));
    private final DoubleUnaryOperator sigmoidDerivative = x -> (1 - x) * x;
    private final Memory memory;

    private final Network network;

    public Educator(Network network, Memory memory) {
        this.memory = memory;
        this.network = network;
    }

    public double checkNetwork(EducationSet education) {
        Layer[] layers = network.getLayers();
        for (int i = 0; i < layers.length; i++) {
            Layer currentLayer = layers[i];
            if (currentLayer.getType() == Layer.Type.INPUT) {
                currentLayer.setNeuronsValues(education.getInput());
            } else {
                Layer previousLayer = layers[i - 1];
                calculateOutput(currentLayer, previousLayer);
            }
        }
        return calculateError(network.getOutputLayer(), education.getOutput());
    }

    private double calculateError(Layer layer, double[] idealOutput) {
        return IntStream.range(0, layer.getSize())
                .mapToDouble(i -> Math.pow(idealOutput[i] - layer.get(i).value, 2))
                .sum();
    }

    private void calculateOutput(Layer currentLayer, Layer previousLayer) {
        for (int i = 0; i < currentLayer.getSize(); i++) {
            Neuron neuron = currentLayer.get(i);
            double[] weights = neuron.weights;
            double sum = IntStream.range(0, weights.length)
                    .mapToDouble(j -> previousLayer.get(j).value * weights[j])
                    .sum();

            Neuron bias = previousLayer.getBias();
            neuron.value = sigmoid.applyAsDouble(sum); //+ bias.weights[i]
        }
    }

    private void backPropagation(EducationSet education) {
        Layer[] layers = network.getLayers();
        double[] deltas = new double[0];
        for (int i = layers.length - 1; i >= 0; i--) {
            Layer layer = layers[i];
            if (layer.getType() == Layer.Type.OUTPUT) {
                double[] idealOutput = education.getOutput();
                deltas = calculateDelta(idealOutput, layer.getNeuronsValues());
            } else {
                updateWeights(layer, layers[i + 1], deltas);
                deltas = calculateDelta(layer, layers[i + 1], deltas);
            }
        }
    }

    private void updateWeights(Layer currentLayer, Layer nextLayer, double[] deltasOfNextLayer) {
        double[] valuesOfCurrentLayer = currentLayer.getNeuronsValues();
        for (int i = 0; i < nextLayer.getSize(); i++) {
            Neuron neuron = nextLayer.getNeurons()[i];
            int k = i;
            double[] gradient = IntStream.range(0, neuron.weights.length)
                    .mapToDouble(j -> valuesOfCurrentLayer[j] * deltasOfNextLayer[k])
                    .toArray();
            updateNeuronWeights(neuron, gradient);
        }
    }

    private void updateNeuronWeights(Neuron neuron, double[] gradient) {
        double[] weights = neuron.weights;
        IntStream.range(0, weights.length)
                .forEach(i -> weights[i] += 0.5 * gradient[i]);
    }

    private double[] calculateDelta(Layer currentLayer, Layer nextLayer, double[] deltaNextLayer) {
        double[] currentLayerValues = currentLayer.getNeuronsValues();
        double[] deltas = new double[currentLayerValues.length];
        for (int i = 0; i < currentLayerValues.length; i++) {
            double sum = 0;
            Neuron[] neurons = nextLayer.getNeurons();
            for (int j = 0; j < neurons.length; j++) {
                sum += neurons[j].weights[i] * deltaNextLayer[j];
            }
            deltas[i] = sigmoidDerivative.applyAsDouble(currentLayerValues[i]) * sum;
        }
        return deltas;
    }

    private double[] calculateDelta(double[] idealOutput, double[] actualOutput) {
        return IntStream.range(0, idealOutput.length)
                .mapToDouble(i -> (idealOutput[i] - actualOutput[i])
                        * sigmoidDerivative.applyAsDouble(actualOutput[i]))
                .toArray();
    }

    public void learn() {
        EducationSet[] educationSets = Constants.getEducationalSets();
        for (int epoch = 0; epoch < 1000; epoch++) {
            double mse = 0;
            for (var education : educationSets) {
                mse += checkNetwork(education);
                backPropagation(education);
            }
            System.out.println("MSE: " + mse / educationSets.length);
        }
    }

    public Memory getMemory() {
        return memory;
    }
}
