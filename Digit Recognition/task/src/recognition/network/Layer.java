package recognition.network;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Layer implements Serializable {
    private static final long serialVersionUID = 3091952703577839647L;

    public enum Type {
        INPUT,
        HIDDEN,
        OUTPUT
    }
    private Neuron bias;
    private final Neuron[] neurons;
    private Type type = Type.HIDDEN;

    public Layer(int size, int connections) {
        this.neurons = IntStream.range(0, size)
                .mapToObj(i -> new Neuron(connections))
                .toArray(Neuron[]::new);
    }

    public void addBias(int outputs) {
        this.bias = new Neuron(outputs);
        this.bias.value = 1;
    }

    public void setNeuronValue(int neuronNumber, double value) {
        neurons[neuronNumber].value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Neuron getBias() {
        return bias;
    }

    public int getSize() {
        return neurons.length;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeuronsValues(double[] values) {
        IntStream.range(0, values.length)
                .forEach(i -> neurons[i].value = values[i]);
    }

    public double[] getNeuronsValues() {
        return Arrays.stream(neurons)
                .mapToDouble(n -> n.value)
                .toArray();
    }

    @Override
    public String toString() {
        return Arrays.toString(neurons);
    }

    public void setWeights(int neuronNumber, double[] weights) {
        System.arraycopy(weights, 0, neurons[neuronNumber].weights, 0, weights.length);
    }

    public Neuron get(int index) {
        return neurons[index];
    }
}
