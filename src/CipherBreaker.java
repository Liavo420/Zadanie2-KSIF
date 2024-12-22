import AutomatedAnalysis.HillClimbing;
import AutomatedAnalysis.VigenerBruteForceAsCaesar;
import Ciphers.SingleColumnarTransposition;
import Helpers.Analysis.L1BigramDist;

import Helpers.TextHandling.SortAndExtract;

import java.util.*;
import java.io.*;

import static Helpers.TextHandling.Text.readFile;

public class CipherBreaker {

    public static void main(String[] args) {
        File folder = new File("ct");
        List<File> ct1Files = new ArrayList<>();
        List<File> ct2Files = new ArrayList<>();

        File[] files = folder.listFiles();

        List<File> allFiles = new ArrayList<>();
        Collections.addAll(allFiles, files);
        SortAndExtract.sortFilesNumerically(allFiles);

        for (File file : allFiles) {
            if (file.getName().contains("ct1_")) {
                ct1Files.add(file);
            } else if (file.getName().contains("ct2_")) {
                ct2Files.add(file);
            }
        }

        L1BigramDist bigramDist = new L1BigramDist();


        for (int i = 0; i < 50; i++) {
            String ct1Text = readFile(ct1Files.get(i));
            String ct2Text = readFile(ct2Files.get(i));

            HillClimbing.DecryptionResult decryptedCT1 = HillClimbing.decryptMAS(ct1Text, 5000, bigramDist, 5);

            System.out.println("FILE_NAME1: " + ct1Files.get(i).getName());
            System.out.println("PT1: " + decryptedCT1.plaintext());
            System.out.println("CT1: " + ct1Text);
            System.out.println("KEY1: " + decryptedCT1.key());


            String columnarKey = decryptedCT1.plaintext().substring(decryptedCT1.plaintext().length() - 26);
            SingleColumnarTransposition columnarTransposition = new SingleColumnarTransposition(columnarKey);
            String decryptedCT2_part1 = columnarTransposition.decrypt(ct2Text);

            VigenerBruteForceAsCaesar.DecryptionResultPart2 decrytCT2_part2 =
                    VigenerBruteForceAsCaesar.bruteForceVigenereWithCaesarShifts(decryptedCT2_part1, 12);

            System.out.println("FILE_NAME2: " + ct2Files.get(i).getName());
            System.out.println("PT2: " + decrytCT2_part2.plaintext());
            System.out.println("CT2: " + ct2Text);
            System.out.println("KEY2_1: " + columnarKey);
            System.out.println("KEY2_2: " + decrytCT2_part2.key());
        }
    }
}
