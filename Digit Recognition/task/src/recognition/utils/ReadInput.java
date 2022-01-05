package recognition.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ReadInput {
    public static double[] readDataFromFile(File file) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines()
                    .flatMapToDouble(line -> Arrays.stream(line.split("\\s"))
                            .mapToDouble(Double::valueOf))
                    .toArray();
        }
    }

    public static double[] readFile(String fileName) throws IOException {
        File testFile = new File(fileName);
        return readDataFromFile(testFile);
    }
}
