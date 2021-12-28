package recognition.network;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;

public class Neuron implements Serializable {
    private static final long serialVersionUID = 6982537706976038625L;
    private final double[] weights;
    private double value;

    public Neuron(int inputs) {
        this.weights = initialValues(inputs);
    }

    private double[] initialValues(int connections) {
        Random rand = new Random();
        return DoubleStream.generate(rand::nextGaussian)
                .limit(connections)
                .toArray();
    }

    @Override
    public String toString() {
        return "Neuron output: " + getValue() + " weights:" + Arrays.toString(weights);
    }

    public double getWeight(int index) {
        return weights[index];
    }

    public void increaseWeight(int index, double delta) {
        weights[index] += delta;
    }

    public int inputCount() {
        return weights.length;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
