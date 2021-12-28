package recognition;

import recognition.education.Educator;
import recognition.network.Layer;
import recognition.utils.ReadInput;

import java.io.IOException;

public class Facade {
    Memory memory;
    Educator educator;
    Recogniser recogniser;

    public Facade(Educator educator) {
        this.educator = educator;
        this.memory = educator.getMemory();
        this.recogniser = new Recogniser(memory);
    }

    public void learn() throws IOException {
        System.out.println("Learning...");
        //setTest();
        educator.learn();
        memory.save();
        System.out.println("Done! Saved to the file.");
    }

    private void setTest() {
        Layer[] layers = memory.getNetwork().getLayers();
        layers[1].setWeights(0, new double[]{0.45, -0.12});
        layers[1].setWeights(1, new double[]{0.78, 0.13});

        layers[2].setWeights(0, new double[]{1.5, -2.3});
    }

    public void recognise() {
        memory.load();
        double[] input = ReadInput.read();
        int guessedDigit = recogniser.recognize(input);
        System.out.println("This number is " + guessedDigit);
    }
}
