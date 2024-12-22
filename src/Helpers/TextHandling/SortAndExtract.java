package Helpers.TextHandling;

import java.io.File;
import java.util.List;

public class SortAndExtract {

    public static void sortFilesNumerically(List<File> files) {
        files.sort((f1, f2) -> {
            int num1 = extractNumber(f1.getName());
            int num2 = extractNumber(f2.getName());
            return Integer.compare(num1, num2);
        });
    }

    private static int extractNumber(String fileName) {
        String[] parts = fileName.split("_|\\.");
        for (String part : parts) {
            if (part.matches("\\d+")) {
                return Integer.parseInt(part);
            }
        }
        return 0;
    }
}
