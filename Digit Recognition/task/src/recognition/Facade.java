package recognition;

import recognition.education.Educator;
import recognition.network.Network;
import recognition.utils.ReadInput;

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

    public void recognise() {
        Recogniser recogniser = new Recogniser(network);
        memory.load();
        double[] input = ReadInput.read();
        int guessedDigit = recogniser.recognise(input);
        System.out.println("This number is " + guessedDigit);
    }
}
