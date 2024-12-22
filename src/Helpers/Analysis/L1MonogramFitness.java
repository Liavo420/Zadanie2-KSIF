package Helpers.Analysis;

public class L1MonogramFitness {
    static final double[] ref = {0.08167, 0.01492, 0.02782,
            0.04253, 0.12702, 0.02228, 0.02015, 0.06094, 0.06966,
            0.00153, 0.00772, 0.04025, 0.02406, 0.06749, 0.07507,
            0.01929, 0.00095, 0.05987, 0.063269, 0.0905599, 0.02758,
            0.00978, 0.0236, 0.0015, 0.01974, 0.00074
    };

    public double evaluate(String decrypted){
        double[] m = countStatistics(decrypted);
        double sum = 0;
        for (int i = 0; i < 26; i++) {
            double nv = ref[i] - m[i];
            sum+= Math.abs(nv);
        }
        return sum;
    }

    public double[] countStatistics(String in){
        double[] m = new double[26];
        for (int i = 0; i < in.length(); i++) {
            char a = in.charAt(i);
            m[a-'a']++;
        }
        double div = in.length();
        for (int i = 0; i < 26; i++) {
            m[i] /= div;
        }
        return m;

    }
}
