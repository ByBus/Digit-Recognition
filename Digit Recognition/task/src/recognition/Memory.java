package recognition;

import recognition.network.Layer;
import recognition.network.Network;

import java.io.*;

public class Memory{
    private static final String SERIALIZATION_FILE_NAME =
            "G:\\Projects\\Programming\\KotlinIntelliIde\\Digit Recognition\\Layers.ser";

    private final Network network;

    public Memory(Network network) {
        this.network = network;
    }

    public void load() {
        try {
            Layer[] layers = readFromFile();
            network.setLayers(layers);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void save() {
        try {
            saveToFile(network.getLayers());
        } catch (IOException e) {
            System.out.println("Can't save the file");
        }
    }

    private void saveToFile(Layer[] layers) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(SERIALIZATION_FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {
            oos.writeObject(layers);
        }
    }

    private Layer[] readFromFile() throws IOException {
        Layer[] deserialized = null;
        try (FileInputStream fileIn = new FileInputStream(SERIALIZATION_FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {

            deserialized = (Layer[]) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert deserialized != null;
        return deserialized;
    }
}
