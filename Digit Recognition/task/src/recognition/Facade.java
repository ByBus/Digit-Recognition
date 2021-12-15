package recognition;

import recognition.utils.ReadInput;

import java.io.IOException;

public class Facade {
    int[] layerOne = new int[5 * 3];
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
        educator.learn();
        memory.saveToFile();
        System.out.println("Done! Saved to the file.");
    }

    public void recognise() {
        ReadInput.fillFirstLayer(layerOne);
        int guessedDigit = recogniser.recognise(layerOne);
        System.out.println("This number is " + guessedDigit);
    }
}
