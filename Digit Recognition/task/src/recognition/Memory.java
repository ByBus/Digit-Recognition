package recognition;

import java.io.*;

public class Memory{
    private static final String SERIALIZATION_FILE_NAME = "G:\\Projects\\Programming\\KotlinIntelliIde\\Digit Recognition\\Neurons.ser";
    private static final int LAYER_SIZE = 10;

    private final Neuron[] neurons = new Neuron[LAYER_SIZE];

    public void addNeurons(Neuron[] neuronsToSave) {
        System.arraycopy(neuronsToSave, 0, neurons, 0, neurons.length);
    }

    public Neuron[] getReadNeurons() {
        try {
            readFromFile();
        } catch (IOException ignored) { }
        return neurons;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void saveToFile() throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(SERIALIZATION_FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {

            oos.writeObject(neurons);
        }
    }

    private void readFromFile() throws IOException {
        Neuron[] deserialized = null;
        try (FileInputStream fileIn = new FileInputStream(SERIALIZATION_FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {

            deserialized = (Neuron[]) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert deserialized != null;
        addNeurons(deserialized);
    }
}
