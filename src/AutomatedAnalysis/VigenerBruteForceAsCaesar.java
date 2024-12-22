package AutomatedAnalysis;

import java.util.Arrays;
import static Helpers.Analysis.Score.scoreText;

public class VigenerBruteForceAsCaesar {
    public record DecryptionResultPart2(String plaintext, String key) {}

    public static String[] segmentCiphertext(String ciphertext, int keyLength) {
        String[] segments = new String[keyLength];
        Arrays.fill(segments, "");

        for (int i = 0; i < ciphertext.length(); i++) {
            int group = i % keyLength;
            segments[group] += ciphertext.charAt(i);
        }
        return segments;
    }

    public static String caesarDecrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            int shifted = (c - 'a' - shift + 26) % 26 + 'a';
            result.append((char) shifted);
        }
        return result.toString();
    }

    public static String reconstructPlaintext(String[] segments, int ciphertextLength) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertextLength; i++) {
            plaintext.append(segments[i % segments.length].charAt(i / segments.length));
        }
        return plaintext.toString();
    }

    // Perform brute-force on the Vigenère cipher (treated as Caesar ciphers for each segment)
    public static DecryptionResultPart2 bruteForceVigenereWithCaesarShifts(String ciphertext, int keyLength) {
        String[] segments = segmentCiphertext(ciphertext, keyLength);
        String[] decryptedSegments = new String[keyLength];
        int[] keyShifts = new int[keyLength];
        double bestScore = Double.MAX_VALUE;

        // Solve each Caesar cipher shift for each segment
        for (int i = 0; i < keyLength; i++) {
            String bestSegmentDecryption = "";
            double bestSegmentScore = Double.MAX_VALUE;
            int bestShift = 0;

            // Try all Caesar shifts from 0 to 25 for this segment
            for (int shift = 0; shift < 26; shift++) {
                String decrypted = caesarDecrypt(segments[i], shift);
                double score = scoreText(decrypted);

                // Update best decryption if the current one has a lower score
                if (score < bestSegmentScore) {
                    bestSegmentScore = score;
                    bestSegmentDecryption = decrypted;
                    bestShift = shift;
                }
            }

            // Store the best decryption and the shift (key component) for this segment
            decryptedSegments[i] = bestSegmentDecryption;
            keyShifts[i] = bestShift;
        }

        // Reconstruct the full decrypted text using the best decryption for each segment
        String plaintext = reconstructPlaintext(decryptedSegments, ciphertext.length());

        // Build the key as a string of characters corresponding to the shifts
        StringBuilder keyBuilder = new StringBuilder();
        for (int shift : keyShifts) {
            keyBuilder.append((char) ('a' + shift));
        }
        String key = keyBuilder.toString();

        return new DecryptionResultPart2(plaintext, key);
    }
}


