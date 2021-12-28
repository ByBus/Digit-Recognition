package recognition.network;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;

public class Neuron implements Serializable {
    private static final long serialVersionUID = 6982537706976038625L;
    public double[] weights;
    public int number;
    public double value;

    public Neuron(int inputs) {
        weights = initialValues(inputs);
    }

    private double[] initialValues(int connections) {
        Random rand = new Random();
        return DoubleStream.generate(rand::nextGaussian)
                .limit(connections)
                .toArray();
    }

    @Override
    public String toString() {
        return "Neuron output: " + value + " weights:" + Arrays.toString(weights);
    }
}
