package Helpers.Analysis;

import Helpers.TextHandling.Text;
public class L1BigramDist {

    private final double[][] ref;

    public L1BigramDist() {
        ref = (double[][]) Text.readFromFile("src/Helper/Analysis/_bigrams");
    }

    public double evaluate(String decrypted) {
        double[][] m = new double[26][26];
        for (int i = 0; i < decrypted.length() - 1; i++) {
            char a = decrypted.charAt(i);
            char b = decrypted.charAt(i + 1);
            m[a - 'a'][b - 'a']++;
        }
        double sum = 0;

        double div = decrypted.length() - 1;

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                double nv = ref[i][j] - (m[i][j] / div);
                sum += Math.abs(nv);
            }
        }
        return sum;
    }

}
