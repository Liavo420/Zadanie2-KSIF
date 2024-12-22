package Helpers.Operations;

import java.util.Arrays;

public class Bellas {


    //V prípade transpozičných šifier je kľúčom zväčša permutácia. V minulosti
    //bolo bežným spôsobom zadávania hesiel k šifrám pomocou fráz, ktoré sa
    //previedli na číslenú permutáciu. Naimplementujte takúto metódu, ktorej
    //autorom je Bellaso. Základná idea spočíva v očíslovaní písmen (zľava) na
    //základe ich poradia v prirodzenom usporiadaní. V prípade, že sa nejaký
    //znak opakuje, poradie sa určuje zľava.
    //Vstupom nech je fráza, ktorá obsahuje znaky telegrafnej abecedy bez medzery (len písmená {a, . . . , z}).
    //Výstupom nech je pole celých čísel, ktoré reprezentuje permutáciu (použite množinu M = {0, 1, . . . , m}).


    public static Integer[] permutationFromPhrase(String phrase) {
        phrase = phrase.toLowerCase();
        Integer[] retPerm = new Integer[phrase.length()];
        char[] sort = phrase.toCharArray();
        Arrays.sort(sort);

        for (int i = 0; i < phrase.length(); i++) {
            for (int j = 0; j < phrase.length(); j++) {
                if (phrase.charAt(i) == sort[j]) {
                    retPerm[i] = j;
                    sort[j] = '_';

                    break;
                }
            }
        }
        return retPerm;
    }
}
