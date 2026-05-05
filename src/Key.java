import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Properties;

public class Key {
    private final HashMap<Character, Character> hMap;
    private static final String admittedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    //constructor
    public Key() {
        this.hMap = new HashMap<>();

        loadKeyFromProperties();

        if (hMap.isEmpty()) {
            for (int i = 0; i < admittedChars.length(); i++) {

                hMap.put(admittedChars.charAt(i), admittedChars.charAt(i)); //0 come caricamento di default

            }
        }
    }

    public boolean setSinglePair(char key, char value) {
        if (admittedChars.indexOf(key) == -1) {
            System.out.println("Error: '" + key + "' is not supported.");
            return false;
        }

        if (hMap.containsValue(value) && hMap.get(key) != value) {
            System.out.println("Error: '" + value + "' is already assigned.");
            return false;
        }

        hMap.put(key, value);
        return true;
    }

    public void setAllPairs() {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < admittedChars.length(); i++) {
            char current = admittedChars.charAt(i);
            boolean success = false;

            while (!success) {
                System.out.print("Enter something else '" + current + "': ");
                String input = sc.nextLine();

                if (input.isEmpty()) continue;

                char newC = input.charAt(0);
                success = setSinglePair(current, newC);
            }
        }

        saveKeyToProperties();
    }

    public void setAllPairsRandomly() {
        Random r = new Random();
        ArrayList<Character> s = new ArrayList<>();

        for (char str : admittedChars.toCharArray()) {  //  String "admittedChars" to ArrayList of chars
            s.add(str);
        }

        for (int i = 0; i < admittedChars.length(); i++){
            char current = admittedChars.charAt(i);
            int newIndex = r.nextInt(0,s.size());
            char newValue = s.get(newIndex);   //Random value that is going to be set to the [i] key

            hMap.replace(current, newValue);

            s.remove(newIndex);
        }

        saveKeyToProperties();
    }

    private boolean saveKeyToProperties() {

            Properties p = new Properties();

            for (Character c : hMap.keySet()) {
                p.setProperty(c.toString(), hMap.get(c).toString());
            }

            File folder = new File("saves");
            if (!folder.exists()) {
                folder.mkdir();
            }

            String path = "saves" + File.separator + "keys.properties";

            try (OutputStream os = new FileOutputStream(path)) {    //twr
                p.store(os, "Key saved.");
                return true;
            } catch (IOException e) {
                System.err.println("Error :  " + e.getMessage());
                return false;
            }
    }

    private void loadKeyFromProperties() {
        Properties p = new Properties();
        File file = new File("saves" + File.separator + "keys.properties");

        if (!file.exists()) return;

        try (InputStream is = new FileInputStream(file)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                hMap.put(key.charAt(0), p.getProperty(key).charAt(0));
            }

        } catch (IOException e) {
            System.err.println("Errore nel caricamento delle chiavi: " + e.getMessage());
        }

    }


    public void print_hMap() {
            System.out.println("\n--- KEY CONFIGURATION ---");
            this.hMap.forEach((k, v) -> System.out.print("[" + k + ":" + v + "] "));
            System.out.println("\n-------------------------------------");

    }

    public String generateNewMessage (String msg) {
        try {
            StringBuilder newMsg = new StringBuilder();
            for (int i = 0; i < msg.length(); i++) {
                if (!hMap.containsKey(msg.charAt(i))) {
                    throw new IllegalArgumentException("Not supported Character: " + msg.charAt(i));
                }
                newMsg.append(hMap.get(msg.charAt(i)));
            }
            return newMsg.toString();
        } catch (IllegalArgumentException e) {
            System.err.println("Error during Encryption: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public HashMap<Character, Character> gethMap() {
        return new HashMap<>(hMap);
    }

    public static String getAdmittedChars() {
        return admittedChars;
    }
}
