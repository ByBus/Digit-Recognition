package recognition.education;

import recognition.utils.ReadInput;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class TrainData {
    private static final String DATA_DIR = "G:\\Projects\\Programming\\KotlinIntelliIde\\Digit Recognition\\data";
    private static final int MAX_SETS = 70000;

    public static EducationSet[] getEducationalSets() throws IOException {
        EducationSet[] educationSets = new EducationSet[MAX_SETS];
        var randomIndices = getRandomIndices();
        for (int i = 0; i < MAX_SETS; i++) {
            String filename = addLeadingZeroes(i + 1);
            String filePath = DATA_DIR + File.separator + filename;
            File educationalFile = new File(filePath);
            educationSets[randomIndices.get(i)] = dataToEducationalSets(ReadInput.readDataFromFile(educationalFile));
        }
        return educationSets;
    }

    private static List<Integer> getRandomIndices() {
        List<Integer> integers =
                IntStream.range(0, MAX_SETS)
                        .boxed()
                        .collect(Collectors.toList());

        Collections.shuffle(integers);
        return integers;
    }

    private static String addLeadingZeroes(int i) {
        return String.format("%05d.txt", i);
    }

    private static EducationSet dataToEducationalSets(double[] data) {
        double[] input = Arrays.stream(data, 0, data.length - 1)
                .map(num -> num / 255)
                .toArray();
        double[] output = DoubleStream
                .generate(() -> 0.0)
                .limit(10)
                .toArray();
        int outputDigitValue = (int) data[data.length - 1];
        output[outputDigitValue] = 1;
        return new EducationSet(input, output);
    }
}
