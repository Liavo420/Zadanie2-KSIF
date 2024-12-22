package Ciphers;//  Naimplementujte transpozičnú metódu preskupenia písmen pomocou číselného hesla.
//• Vstupom je reťazec, ktorý obsahuje znaky telegrafnej abecedy bez medzery (len písmená {a, . . . , z}).

//• Transpozícia je daná číslenou permutáciou dĺžky n na množine {0, 1, . . . , n − 1}.

//• Text sa najprv rozdelí na bloky dĺžky n. Podľa potreby sa na koniec
//  vstupu doplní určitý počet znakov "x", aby bol posledný blok úplný (dĺžky n).

//• Permutácia určuje preusporiadavanie znakov v danom bloku (pri použití sa opakuje po blokoch).

//• Reťazec "abcdef" sa po aplikovaní kľúča {2, 1, 3} zmení na "bacedf".

//• Permutácia sa zadáva buď pomocou čísiel (int []), alebo sa odvodí z
//  frázy (String). V druhom prípade treba použiť funkciu z Úlohy 2.12.


import Helpers.Operations.Bellas;
import Helpers.Operations.Permutations;

public class Transposition {
    Integer[] key;
    Integer[] invKey;

    private Transposition() {
    }

    ;

    public Transposition(Integer[] key) {
        this.key = key;
        this.invKey = Permutations.inverse(key);
    }

    public Transposition(String keyPhrase) {
        this(Bellas.permutationFromPhrase(keyPhrase));
    }

    public String encrypt(String input) {
        if (input.length() % key.length != 0) {
            int missingCharCount = key.length - (input.length() % key.length);
            for (int i = 0; i < missingCharCount; i++) {
                input += 'x';

            }
        }
        return cryptopsy(input, key);

    }

    public String decrypt(String input) {
        return cryptopsy(input, invKey);
    }


    public String cryptopsy(String input, Integer[] key) {
        int blockLength = key.length;
        int blockCount = input.length() / blockLength;
        StringBuilder retString = new StringBuilder();
        for (int multiply = 0; multiply < blockCount; multiply++) {
            int posInString = multiply * blockLength;
            String subStr = input.substring(posInString, posInString + blockLength);
            String permOnSubStr = applyPerm(subStr, key);
            retString.append(permOnSubStr);
        }
        return retString.toString();

    }

    public static String applyPerm(String input, Integer[] perm) {
        char[] output = new char[perm.length];
        for (int i = 0; i < perm.length; i++) {
            output[perm[i]] = input.charAt(i);
        }
        return new String(output);
    }


}
