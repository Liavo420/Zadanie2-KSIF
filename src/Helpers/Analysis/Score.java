package Helpers.Analysis;

public class Score {
    public static double scoreText(String text) {
        // Use L1BigramDist to evaluate bigrams
        L1BigramDist bigramDist = new L1BigramDist();
        L1MonogramFitness fit = new L1MonogramFitness();
        double bigramScore = bigramDist.evaluate(text);

        // Additional letter frequency analysis
        double letterFreqScore = fit.evaluate(text);

        // Combine both scores (you can experiment with different weightings)
        return bigramScore * 0.7 + letterFreqScore * 0.3;
    }
}
