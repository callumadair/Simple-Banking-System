package banking;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Models a simple electronic banking system.
 *
 * @author Cal
 * @version 1.4
 */
class BankingSystem {
  private final HashMap<String, Account> cards = new HashMap<>();
  private Database database;

  public BankingSystem(String databaseName) {
    this.database = new Database();
    this.database.createNewDatabase(databaseName);
    this.database.createNewTable(
        "CREATE TABLE IF NOT EXISTS card ("
            + "\n	id INTEGER,"
            + "\n number TEXT,"
            + "\n pin TEXT,"
            + "\nbalance INTEGER DEFAULT 0"
            + "\n);");
    cards.putAll(this.database.getExistingData());
  }

  public HashMap<String, Account> getCards() {
    return this.cards;
  }

  public void systemMenu(Scanner scanner) {
    System.out.println("1. Create an account\n2. Log into account\n0. Exit");
    int menuOption = scanner.nextInt();
    if (menuOption == 1) {
      createAccount(scanner);
    } else if (menuOption == 2) {
      System.out.println("Enter your card number:");
      String cardNumber = scanner.next();
      System.out.println("Enter your PIN:");
      int pinNumber = scanner.nextInt();
      accountLogin(cardNumber, pinNumber, scanner);
    } else if (menuOption == 0) {
      exitProgram();
    }
  }

  public void createAccount(Scanner scanner) {
    Account newAccount = new Account();

    if (this.cards.containsKey(newAccount.getCardNum())
        || this.database.selectIdRow(newAccount.getIdNum()) > 0) {
      createAccount(scanner);
    }

    this.cards.put(newAccount.getCardNum(), newAccount);

    this.database.insert(
        newAccount.getIdNum(),
        newAccount.getCardNum(),
        newAccount.getPinNumber(),
        newAccount.getBalance());

    System.out.println(
        "Your card has been created"
            + "\nYour card number:"
            + "\n"
            + newAccount.getCardNum()
            + "\nYour card PIN:\n"
            + newAccount.getPinNumber());
    systemMenu(scanner);
  }

  public void accountLogin(String cardNumber, int pinNumber, Scanner scanner) {

    if (this.cards.containsKey(cardNumber)
        && this.cards.get(cardNumber).getPinNumber() == pinNumber) {
      Account curAccount = this.cards.get(cardNumber);

      System.out.println(
          "You have successfully logged in!"
              + "\n1. Balance"
              + "\n2. Add income"
              + "\n3. Do transfer"
              + "\n4. Close Account"
              + "\n5. Log out"
              + "\n0. Exit");

      int option = scanner.nextInt();
      switch (option) {
        case 1:
          System.out.println(curAccount.getBalance());
          accountLogin(cardNumber, pinNumber, scanner);
          break;
        case 2:
          System.out.println("Enter income:");
          int income = scanner.nextInt();

          curAccount.changeBalance(+income);
          this.database.updateBalance(curAccount.getIdNum(), +income);

          accountLogin(cardNumber, pinNumber, scanner);
          break;
        case 3:
          System.out.println("Transfer\nEnter card number:");
          String transferAccountNumber = scanner.next();

          if (curAccount.getCardNum().equals(transferAccountNumber)) {
            System.out.println("You can't transfer money to the same account!");

          } else if (!Account.isLuhn(transferAccountNumber)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");

          } else if (this.database.selectNumberRow(transferAccountNumber) != null) {
            Account transferAccount = this.cards.get(transferAccountNumber);
            System.out.println("Enter how much money you want to transfer:");
            int transferAmount = scanner.nextInt();

            if (this.database.selectNumberRow(curAccount.getCardNum()).getBalance()
                >= transferAmount) {
              curAccount.changeBalance(-transferAmount);
              this.database.updateBalance(curAccount.getIdNum(), -transferAmount);

              transferAccount.changeBalance(+transferAmount);
              this.database.updateBalance(transferAccount.getIdNum(), +transferAmount);
              System.out.println("Success!");
            } else {
              System.out.println("Not enough money!");
            }
          } else {
            System.out.println("Such a card does not exist.");
          }
          accountLogin(cardNumber, pinNumber, scanner);
          break;
        case 4:
          this.database.deleteEntry(curAccount.getIdNum());
          this.cards.remove(curAccount.getCardNum());
          System.out.println("The account has been closed.");

          systemMenu(scanner);
          break;
        case 5:
          System.out.println("You have successfully logged out.");
          systemMenu(scanner);
          break;
        case 0:
          exitProgram();
          break;
        default:
          System.out.println("Invalid option!");
          accountLogin(cardNumber, pinNumber, scanner);
          break;
      }
    } else {
      System.out.println("Wrong card number or PIN!");
      systemMenu(scanner);
    }
  }

  /** Exits the program. */
  public void exitProgram() {
    System.out.println("Bye!");
    System.exit(0);
  }
}
