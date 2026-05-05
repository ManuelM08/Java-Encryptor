import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Key k = new Key();


        System.out.println("-== Encryptor ==-");

        //---------------------------------------Menu----------------------------------------


        while (true) {
            int resp = -1;
            System.out.println("Choose an option (1-5)");
            System.out.println("0. Change encryption key Randomly\n" +
                    "1. Encrypt with encryption key \n" +
                    "2. work in progress \n" +
                    "3. work in progress \n" +
                    "4. work in progress \n" +
                    "5. work in progress \n");

            while (resp > 5 || resp < 0) {
                resp = sc.nextInt();
            }

            //---------------------------------------Switch--------------------------------------
            switch (resp) {
                case 0:
                    System.out.println("Current keys : ");
                    k.print_hMap();

                    System.out.println("\n New keys : ");
                    k.setAllPairsRandomly();
                    k.print_hMap();

                break;

                case 1:
                    String msg = sc.next();
                    System.out.println(k.generateNewMessage(msg));
                break;

            }
        }

        //-----------------------------------------------------------------------------------
    }
}