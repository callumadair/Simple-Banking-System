package banking;

import java.util.Random;


/**
 * The type Account.
 */
class Account {
    private String cardNum;
    private int pinNumber;
    private int balance;
    private int idNum;


    /**
     * Instantiates a new Account.
     */
    public Account() {
        setCardNumber();
        setPinNumber();
        setIdNum();
    }

    private void setCardNumber() {
        Random rand = new Random();
        String newCardNum = "400000" + (100_000_000 + rand.nextInt(899_999_999));
        String checkSum = calculateCheckSum(newCardNum);
        this.cardNum = newCardNum + checkSum;
    }


    /**
     * Sets card num.
     *
     * @param cardNum the card num
     */
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    /**
     * Gets card num.
     *
     * @return the card num
     */
    public String getCardNum() {
        return this.cardNum;
    }


    private String calculateCheckSum(String newCardNumber) {
        int[] intArray = new int[newCardNumber.length()];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(String.valueOf(newCardNumber.charAt(i)));
        }


        for (int j = intArray.length - 1; j >= 0; j -= 2) {
            intArray[j] *= 2;
            if (intArray[j] > 9) {
                intArray[j] = totalDigits(intArray[j]);
            }

        }
        int total = 0;
        for (int ints : intArray) {
            total += ints;
        }

        int checkSum = 10 - (total % 10);
        if (checkSum == 10) {
            return "0";
        } else {
            return Integer.toString(checkSum);
        }
    }

    /**
     * Is luhn boolean.
     *
     * @param accountNumber the account number
     * @return the boolean
     */
    public static boolean isLuhn(String accountNumber) {
        int[] intArray = new int[accountNumber.length()];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(String.valueOf(accountNumber.charAt(i)));
        }

        for (int j = intArray.length - 2; j >= 0; j -= 2) {
            intArray[j] *= 2;
            if (intArray[j] > 9) {
                intArray[j] = totalDigits(intArray[j]);
            }
        }
        int total = 0;
        for (int ints : intArray) {
            total += ints;
        }

        return total % 10 == 0;
    }

    private static int totalDigits(int input) {
        String inputStr = Integer.toString(input);
        int total = 0;
        for (int i = 0; i < inputStr.length(); i++) {
            total += Integer.parseInt(String.valueOf(inputStr.charAt(i)));
        }
        return total;
    }


    private void setPinNumber() {
        Random random = new Random();
        this.pinNumber = 1000 + random.nextInt(8999);
    }


    /**
     * Sets pin number.
     *
     * @param pinNumber the pin number
     */
    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }


    /**
     * Gets pin number.
     *
     * @return the pin number
     */
    public int getPinNumber() {
        return this.pinNumber;
    }


    private void setIdNum() {
        Random rand = new Random();
        this.idNum = rand.nextInt(999999);
    }


    /**
     * Sets id num.
     *
     * @param idNum the id num
     */
    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }


    /**
     * Gets id num.
     *
     * @return the id num
     */
    public int getIdNum() {
        return this.idNum;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }


    /**
     * Gets balance.
     *
     * @return the balance
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * Change balance.
     *
     * @param valChange the val change
     */
    public void changeBalance(int valChange) {
        this.balance += valChange;
    }

}
