package Ciphers;

public class Vigener {

    static final int modulo = 26;
    String key;

    public Vigener(String keyInput) {
        this.key = keyInput;
    }

    public String encrypt(String pt) {
        StringBuilder ct = new StringBuilder();
        for (int i = 0; i < pt.length(); i++) {
            int inNum = (pt.charAt(i) - 'a');
            inNum += key.charAt(i % key.length()) - 'a';
            inNum %= modulo;
            char inChar = (char) (inNum + 'a');
            ct.append(inChar);
        }
        return ct.toString();
    }

    public String decrypt(String ct) {
        StringBuilder pt = new StringBuilder();
        for (int i = 0; i < ct.length(); i++) {
            int inNum = (ct.charAt(i) - 'a');
            inNum -= key.charAt(i % key.length()) - 'a';
            inNum += modulo;
            inNum %= modulo;
            char inChar = (char) (inNum + 'a');
            pt.append(inChar);
        }
        return pt.toString();
    }
}
