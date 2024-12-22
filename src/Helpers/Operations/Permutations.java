package Helpers.Operations;

import java.util.Arrays;
import java.util.Random;


public class Permutations {
    public static Integer[] inverse(Integer[] perm){
        Integer[] inv = new Integer[perm.length];
        for (int i = 0; i < perm.length; i++) {
            inv[perm[i]] = i;
        }
        return inv;
    }

    public static Integer[] permFromPhrase(String phrase){
        phrase = phrase.toLowerCase();
        Integer[] retVal = new Integer[phrase.length()];

        char[] sorted = phrase.toCharArray();
        Arrays.sort(sorted);

        int len = phrase.length();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++){
                if(phrase.charAt(i) == sorted[j]){
                    retVal[i] = j;
                    sorted[j] = '_';
                    break;
                }
            }

        }
        return retVal;

    }

    public static void rndPerm(Object[] input){
        Random rnd = new Random(System.currentTimeMillis());
        int size = input.length;
        for (int i = 0; i < (size - 1); i++) {
            int j = rnd.nextInt(size - i) + i;

            Object tmp = input[i];
            input[i] = input[j];
            input[j] = tmp;
        }

    }

}
