package Helpers.Analysis;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DictionaryNode {
    private char nodeChar;
    private boolean word;
    private List<DictionaryNode> children;
    private int length;

    public DictionaryNode(char nodeChar, boolean word, int length) {
        this.nodeChar = nodeChar;
        this.length = length;
        this.children = new ArrayList<>();
        this.word = word;
    }

    public char getNodeChar() {
        return nodeChar;
    }

    public boolean isWord() {
        return word;
    }

    public void addToChildren(DictionaryNode next) {
        this.children.add(next);
    }

    public boolean containsChild(char c) {
        for (DictionaryNode n : this.children) {
            if (n.nodeChar == c) {
                return true;
            }
        }
        return false;
    }

    public DictionaryNode getChild(char c) {
        for (DictionaryNode n : this.children) {
            if (n.nodeChar == c)
                return n;
        }
        return null;
    }

    public static DictionaryNode loadDictionary(Collection<String> words) {
        DictionaryNode root = new DictionaryNode('*', false, 0);
        for (String word : words) {
            DictionaryNode tmp = root;
            int i = 1;
            for (char c : word.toCharArray()) {
                if (!tmp.containsChild(c)) {
                    DictionaryNode newNode = new DictionaryNode(c, false, i);
                    tmp.addToChildren(newNode);
                    tmp = newNode;
                } else {
                    tmp = tmp.getChild(c);
                }
                i++;
            }
            tmp.word = true;
        }
        return root;
    }

    public static List<String> readDictionaryWords(String path) {
        File f = new File(path);
        List<String> words = new ArrayList<>();
        if (f.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null) {
                    words.add(line.trim().toLowerCase());
                }
            } catch (IOException _) {
            }
        }
        return words;
    }

    public double evaluate(String txt, int minLength, int maxLength) {
        double s = 0;
        for (int i = 0; i < txt.length(); i++) {
            int upTo = i + maxLength;
            if (upTo > txt.length()) upTo = txt.length();
            String ss = txt.substring(i, upTo);
            DictionaryNode tmp = this;
            DictionaryNode maxWord = null;
            for (char c : ss.toCharArray()) {
                if (tmp.containsChild(c)) {
                    tmp = tmp.getChild(c);
                    if (tmp.isWord()) {
                        maxWord = tmp;
                    } else
                        break;
                }

            }
            if (maxWord != null && maxWord.length >= minLength) {
                s += maxWord.length;
                i += maxWord.length - 1;

            }

        }
        return s / (double) txt.length();
    }
}
