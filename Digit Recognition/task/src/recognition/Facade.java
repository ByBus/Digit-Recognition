package recognition;

import recognition.education.Educator;
import recognition.network.Network;
import recognition.utils.ReadInput;

import java.io.IOException;

public class Facade {
    private final Memory memory;
    private final Network network;
    private final Educator educator;

    public Facade(Educator educator, Memory memory, Network network) {
        this.educator = educator;
        this.memory = memory;
        this.network = network;
    }

    public void train() {
        System.out.println("Learning...");
        educator.train();
        memory.save();
        System.out.println("Done! Saved to the file.");
    }

    public void recognise(String inputFileName) {
        try {
            Recogniser recogniser = new Recogniser(network);
            memory.load();
            double[] input = ReadInput.readFile(inputFileName);
            int guessedDigit = recogniser.recognise(input);
            System.out.println("This number is " + guessedDigit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getNetworkPrediction() {
        try {
            System.out.println("Guessing...");
            memory.load();
            int[] accuracy = educator.calculateAccuracy();
            System.out.printf("The network prediction accuracy: %d/%d, %d%%",
                    accuracy[0],
                    accuracy[1],
                    accuracy[0] * 100 / accuracy[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
