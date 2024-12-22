package Helpers.TextHandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;


public class Text {
    public static Object readFromFile(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String readFile(File file) {
        try (Scanner sc = new Scanner(file)) {
            return sc.useDelimiter("\\A").next();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
