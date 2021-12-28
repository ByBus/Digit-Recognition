package recognition;
import recognition.education.Educator;
import recognition.network.Network;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Network network = new Network();
        Memory memory = new Memory(network);
        Educator educator = new Educator(network, memory);
        Facade facade = new Facade(educator);
        int choice = menu();
        System.out.println("Your choice: " + choice);
        if (choice == 1) {
            //network.setLayerSizes(new int[]{2, 2, 1});
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the sizes of the layers: ");
            int[] layersSizes = Arrays.stream(sc.nextLine().trim().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            network.toLayers(layersSizes);
            facade.learn();
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
}
