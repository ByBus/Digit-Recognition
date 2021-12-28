package recognition;

import recognition.education.Educator;
import recognition.network.Network;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Network network = new Network();
        Memory memory = new Memory(network);
        Educator educator = new Educator(network);
        Facade facade = new Facade(educator, memory, network);
        int choice = menu();
        System.out.println("Your choice: " + choice);
        if (choice == 1) {
            int[] layersSizes = getLayerSizes();
            network.initLayers(layersSizes);
            facade.train();
        } else {
            facade.recognise();
        }
    }

    private static int menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Learn the network");
        System.out.println("2. Guess a number");
        return sc.nextInt();
    }

    private static int[] getLayerSizes() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the sizes of the layers: ");
        return Arrays.stream(sc.nextLine().trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
