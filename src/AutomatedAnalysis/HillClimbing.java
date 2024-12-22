package AutomatedAnalysis;

import Helpers.Analysis.L1BigramDist;
import java.util.*;
import static Helpers.Analysis.FrequencyAnalysis.calculateCharacterFrequency;


public class HillClimbing {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static double temperature = 1.0;

    public record DecryptionResult(String plaintext, String key) {
    }

    public static DecryptionResult decryptMAS(String ciphertext, int iterations, L1BigramDist bigramDist, int restarts) {
        String bestPlaintext = "";
        String bestKey = "";
        double bestScore = Double.MAX_VALUE;

        for (int j = 0; j < restarts; j++) {
            String initialKey = generateInitialKey(ciphertext);
            String decrypted = hillClimbWithKey(ciphertext, iterations, bigramDist, initialKey);
            double currentScore = bigramDist.evaluate(decrypted);
            if (currentScore < bestScore) {
                bestPlaintext = decrypted;
                bestKey = initialKey;
                bestScore = currentScore;
            }
        }

        return new DecryptionResult(bestPlaintext, bestKey);
    }

    private static String hillClimbWithKey(String ciphertext, int iterations, L1BigramDist bigramDist, String initialKey) {
        char[] key = initialKey.toCharArray();
        String bestKey = new String(key);
        String bestPlaintext = decryptWithKey(ciphertext, bestKey);
        double bestScore = bigramDist.evaluate(bestPlaintext);

        for (int i = 0; i < iterations; i++) {
            char[] newKey = bestKey.toCharArray();
            if (i % 100 == 0) {
                occasionalLargePerturbation(newKey);
            } else {
                mutateKey(newKey, i, iterations);
            }

            String newPlaintext = decryptWithKey(ciphertext, new String(newKey));
            double newScore = bigramDist.evaluate(newPlaintext);

            double scoreDifference = bestScore - newScore;
            if (scoreDifference > 0 || Math.random() < Math.exp(scoreDifference / temperature)) {
                bestKey = new String(newKey);
                bestPlaintext = newPlaintext;
                bestScore = newScore;
            }

            if (i < iterations / 2) {
                temperature *= 0.991;
            } else {
                temperature -= 0.001;
                if (temperature < 0) {
                    temperature = 0;
                }
            }

        }

        return bestPlaintext;
    }

    private static String decryptWithKey(String ciphertext, String key) {
        Map<Character, Character> keyMap = new HashMap<>();
        for (int i = 0; i < alphabet.length(); i++) {
            keyMap.put(key.charAt(i), alphabet.charAt(i));
        }
        StringBuilder plaintext = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            plaintext.append(keyMap.getOrDefault(c, c));
        }
        return plaintext.toString();
    }

    private static void swapRandom(char[] array) {
        Random rand = new Random();
        int i = rand.nextInt(array.length);
        int j;
        do {
            j = rand.nextInt(array.length);
        } while (i == j);
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void occasionalLargePerturbation(char[] array) {
        Random rand = new Random();
        int swaps = rand.nextInt(array.length / 2);
        for (int k = 0; k < swaps; k++) {
            swapRandom(array);
        }
    }

    private static void mutateKey(char[] array, int iteration, int totalIterations) {
        Random rand = new Random();
        int choice = rand.nextInt(3);

        switch (choice) {
            case 0 -> swapRandom(array); // Single swap
            case 1 -> { // Reverse a segment
                int start = rand.nextInt(array.length / 2);
                int end = start + rand.nextInt(array.length / 2);
                while (start < end) {
                    char temp = array[start];
                    array[start++] = array[end];
                    array[end--] = temp;
                }
            }
            case 2 -> { // Shuffle a small segment
                int segmentSize = Math.max(2, rand.nextInt(array.length / 4));
                int start = rand.nextInt(array.length - segmentSize);
                for (int k = 0; k < segmentSize; k++) {
                    swapRandom(array);
                }
            }
        }
    }

    private static String generateInitialKey(String ciphertext) {
        // Step 1: Calculate character frequencies of the ciphertext
        Map<Character, Double> cipherCharFreq = calculateCharacterFrequency(ciphertext);

        // Step 2: Sort the characters by frequency in descending order
        List<Map.Entry<Character, Double>> sortedCipherCharFreq = new ArrayList<>(cipherCharFreq.entrySet());
        sortedCipherCharFreq.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue())); // Sort by frequency

        // Step 3: Define reference English frequency order
        String englishAlphabet = "etaoinshrdlcumwfgypbvkjxqz";  // Frequency order of English letters

        // Step 4: Generate initial key by mapping the most frequent ciphertext letters to the most frequent English letters
        char[] initialKey = new char[26];
        Arrays.fill(initialKey, ' ');  // Fill with empty spaces initially

        // Map ciphertext letters to English letters based on frequency order
        for (int i = 0; i < sortedCipherCharFreq.size() && i < englishAlphabet.length(); i++) {
            char cipherChar = sortedCipherCharFreq.get(i).getKey();
            char englishChar = englishAlphabet.charAt(i);
            initialKey[cipherChar - 'a'] = englishChar;
        }

        // Fill remaining letters with unused English letters
        Set<Character> usedChars = new HashSet<>();
        for (char c : initialKey) {
            if (c != ' ') usedChars.add(c);
        }
        List<Character> unusedChars = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            if (!usedChars.contains(c)) unusedChars.add(c);
        }
        int unusedIndex = 0;
        for (int i = 0; i < initialKey.length; i++) {
            if (initialKey[i] == ' ') {
                initialKey[i] = unusedChars.get(unusedIndex++);
            }
        }

        return new String(initialKey);
    }
}
