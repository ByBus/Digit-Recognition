package recognition;

import java.io.Serializable;
import java.util.Random;
import java.util.stream.DoubleStream;

public class Neuron implements Serializable {
    private static final long serialVersionUID = 6982537706976038625L;
    public double[] weights;
    public final int number;
    public double value;

    public Neuron(int number) {
        this.number = number;
    }

    {
        weights = initialValues();
    }

    private double[] initialValues() {
        Random rand = new Random();
        return DoubleStream.generate(rand::nextGaussian)
                .limit(15)
                .toArray();
    }

    @Override
    public String toString() {
        return "Neuron №" + number;
    }
}
