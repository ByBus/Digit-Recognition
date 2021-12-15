package recognition;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Memory memory = new Memory();
        Educator educator = new Educator(memory);
        Facade facade = new Facade(educator);
        int choice = menu();
        System.out.println("Your choice: " + choice);
        if (choice == 1) {
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
