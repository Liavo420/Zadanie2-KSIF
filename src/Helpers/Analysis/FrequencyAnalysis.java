package Helpers.Analysis;

import java.util.*;

public class FrequencyAnalysis {
    public static Map<Character, Double> calculateCharacterFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int totalChars = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                c = Character.toLowerCase(c);
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
                totalChars++;
            }
        }

        // Convert to relative frequency
        Map<Character, Double> relativeFrequencyMap = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            relativeFrequencyMap.put(entry.getKey(), entry.getValue() / (double) totalChars);
        }

        return relativeFrequencyMap;
    }

    // Reference English letter frequencies (approximate)
    public static final Map<Character, Double> ENGLISH_FREQ = new HashMap<>();

    static {
        ENGLISH_FREQ.put('e', 0.127);
        ENGLISH_FREQ.put('t', 0.0906);
        ENGLISH_FREQ.put('a', 0.0817);
        ENGLISH_FREQ.put('o', 0.0751);
        ENGLISH_FREQ.put('i', 0.0697);
        ENGLISH_FREQ.put('n', 0.0675);
        ENGLISH_FREQ.put('s', 0.0633);
        ENGLISH_FREQ.put('h', 0.0609);
        ENGLISH_FREQ.put('r', 0.0599);
        ENGLISH_FREQ.put('d', 0.0425);
        ENGLISH_FREQ.put('l', 0.0403);
        ENGLISH_FREQ.put('c', 0.0278);
        ENGLISH_FREQ.put('u', 0.0276);
        ENGLISH_FREQ.put('m', 0.0241);
        ENGLISH_FREQ.put('p', 0.0233);
        ENGLISH_FREQ.put('b', 0.0199);
        ENGLISH_FREQ.put('g', 0.0190);
        ENGLISH_FREQ.put('v', 0.0098);
        ENGLISH_FREQ.put('y', 0.0098);
        ENGLISH_FREQ.put('f', 0.0082);
        ENGLISH_FREQ.put('k', 0.0077);
        ENGLISH_FREQ.put('j', 0.0015);
        ENGLISH_FREQ.put('x', 0.0015);
        ENGLISH_FREQ.put('q', 0.0009);
        ENGLISH_FREQ.put('z', 0.0007);
    }
}
