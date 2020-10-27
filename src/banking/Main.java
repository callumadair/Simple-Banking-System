package banking;

import java.util.Scanner;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        BankingSystem system = args.length >= 2 && args[1] != null
                ? new BankingSystem(args[1]) : new BankingSystem("card.s3db");

        Scanner scanner = new Scanner(System.in);
        system.systemMenu(scanner);
    }
}