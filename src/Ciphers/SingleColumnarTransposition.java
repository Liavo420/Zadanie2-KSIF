package Ciphers;// Vstupom je reťazec, ktorý obsahuje znaky telegrafnej abecedy bez medzery (len písmená {a, . . . , z}).
//• Písmená zo vstupu sa zapisujú do matice po riadkoch a čítajú sa po stĺpcoch.
//• Permutácia sa zadáva buď pomocou čísiel alebo sa odvodí z frázy (String).
//• Permutácia sa skladá z prvkov množiny {0, 1, . . . , n − 1}.
//• V prípade, že dĺžka vstupu nie je násobkom dĺžky kľúča, znaky sa nedopĺňajú.

import Helpers.Operations.Bellas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleColumnarTransposition {
    Integer[] key;

    private SingleColumnarTransposition() {
    }

    public SingleColumnarTransposition(Integer[] key) {
        this.key = key;
    }

    public SingleColumnarTransposition(String keyPhrase) {
        this(Bellas.permutationFromPhrase(keyPhrase));
    }

    public String encrypt(String phrase) {
        int columns = key.length;
        int rows = phrase.length();

        if (phrase.length() % columns > 0) {
            rows++;
        }

        char[][] matrix = new char[rows][columns];

        int i = 0, j = 0;

        for (char s : phrase.toCharArray()) {
            matrix[i][j++] = s;
            if (j == columns) {
                i++;
            }
            j %= columns;
        }

        List<Integer> permList = new ArrayList<>();
        permList.addAll(Arrays.asList(key));

        StringBuilder retVal = new StringBuilder();
        for (int c = 0; c < columns; c++) {
            int colId = permList.indexOf(c);
            for (int r = 0; r < rows; r++) {
                if (matrix[r][colId] != '\u0000') {
                    retVal.append(matrix[r][colId]);
                }
            }
        }
        return retVal.toString();
    }


    public String decrypt(String phrase) {
        int columns = key.length;
        int rows = phrase.length() / columns;
        int incompleteColumns = phrase.length() % columns;
        if (incompleteColumns > 0) {
            rows++;
        }

        List<Integer> permList = new ArrayList<>();
        permList.addAll(Arrays.asList(key));

        char[][] matrix = new char[rows][columns];

        int idx = 0;
        for (int c = 0; c < columns; c++) {
            int colId = permList.indexOf(c);
            int maxRowId = rows;
            if (incompleteColumns > 0 && (colId >= incompleteColumns)) {
                maxRowId--;
            }
            for(int r = 0; r < maxRowId; r++){
                matrix[r][colId] = phrase.charAt(idx++);
            }
        }

        StringBuilder retVal = new StringBuilder();
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                if (matrix[r][c] != '\u0000') {
                    retVal.append(matrix[r][c]);
                }
            }
        }
        return retVal.toString();

    }
}

