package banking;

import java.sql.*;
import java.util.HashMap;


/**
 * The type Database.
 */
class Database {
    private String url;


    /**
     * Create new database.
     *
     * @param fileName the file name
     */
    public void createNewDatabase(String fileName) {

        this.url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(this.url)) {
            if (conn != null) {
                System.out.println("Successfully connected to the database");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create new table.
     *
     * @param sqlTableInfo the sql table info
     */
    public void createNewTable(String sqlTableInfo) {

        try (Connection conn = DriverManager.getConnection(this.url);
             Statement statement = conn.createStatement()) {
            // create a new table
            statement.execute(sqlTableInfo);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert.
     *
     * @param idNum   the id num
     * @param cardNum the card num
     * @param pinNum  the pin num
     * @param balance the balance
     */
    public void insert(int idNum, String cardNum, int pinNum, int balance) {
        String sql = "INSERT INTO card (id, number, pin, balance) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, idNum);
            preparedStatement.setString(2, cardNum);
            preparedStatement.setString(3, Integer.toString(pinNum));
            preparedStatement.setInt(4, balance);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Update balance.
     *
     * @param id        the id
     * @param valChange the val change
     */
    public void updateBalance(int id, int valChange) {
        String sql = "UPDATE card SET balance = balance + "
                + valChange + " WHERE id = " + id;

        try (Connection conn = this.connect()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Delete entry.
     *
     * @param id the id
     */
    public void deleteEntry(int id) {
        String sql = "DELETE from card WHERE id = " + id;

        try (Connection conn = this.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets existing data.
     *
     * @return the existing data
     */
    public HashMap<String, Account> getExistingData() {
        HashMap<String, Account> existingData = new HashMap<>();

        String sql = "SELECT * FROM card";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Account newCard = new Account();

                newCard.setIdNum(rs.getInt("id"));
                newCard.setCardNum(rs.getString("number"));
                newCard.setPinNumber(Integer.parseInt(rs.getString("pin")));
                newCard.setBalance(rs.getInt("balance"));

                existingData.put(newCard.getCardNum(), newCard);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return existingData;
    }

    /**
     * Select id row int.
     *
     * @param idNum the id num
     * @return the int
     */
    public int selectIdRow(int idNum) {
        String sql = "SELECT " + idNum + " FROM card";
        int output = 0;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                output += rs.getInt("id");
            }
        } catch (SQLException e) {
            return 0;
        }
        return output;
    }

    /**
     * Select number row account.
     *
     * @param cardNum the card num
     * @return the account
     */
    public Account selectNumberRow(String cardNum) {
        String sql = "SELECT * FROM card WHERE number = " + cardNum;

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Account newAccount = new Account();

            newAccount.setIdNum(rs.getInt("id"));
            newAccount.setCardNum(rs.getString("number"));
            newAccount.setPinNumber(Integer.parseInt(rs.getString("pin")));
            newAccount.setBalance(rs.getInt("balance"));

            return newAccount;
        } catch (SQLException e) {
            return null;
        }
    }
}
