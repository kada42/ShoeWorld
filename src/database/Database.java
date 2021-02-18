package database;

import models.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-17 <br>
 * Time: 11:05 <br>
 * Project: ShoeWorld <br>
 */
public class Database {

    private static final String DB_NAME = "shoe_worlddb2";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/shoe_worlddb2?serverTimezone=UTC&useSSL=false";
    private static final String USER_NAME = "testuser";
    private static final String PASSWORD = "testuser";

    // Test data
    int membershipNrTest = 1;
    int articleNrTest = 2021040;
    int orderNrTest = 13;

    Connection connection;
    Statement statement;

    public Database(){
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
            statement = connection.createStatement();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean checkCredentials(int _membershipNr, String _password){

        try{
            ResultSet result = statement.executeQuery("SELECT password FROM customers WHERE membership_nr = " +_membershipNr);
            return result.getString("password").equalsIgnoreCase(_password);

        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return true;
        }
    }

    public void addToCart(int customerID, int shoeArticleNr, int orderID){
        try {
            // AddToCart(customerID int, shoeID int, orderID int)
            CallableStatement cstmt = connection.prepareCall("{? = call AddToCart(?,?,?)}");
            cstmt.setInt(1, membershipNrTest);
            cstmt.setInt(2, articleNrTest);
            cstmt.setInt(3, orderNrTest);

            cstmt.execute();

        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String> checkCart(){
        List<String> list = new ArrayList<>();

        return list;
    }

    public void sendGrade(String shoeArticleNr, String comment){

    }

    public Message getAverageGrade(String shoeArticleNr){
        Message message = new Message();


        return message;
    }

    public String getLatestOrderNr(){   // måste testas i samband med "addToCart"
        try{
//            Preparing a CallableStatement to call a function
//            CallableStatement cstmt = con.prepareCall("{? = call getDob(?)}");
            CallableStatement cstmt = connection.prepareCall("{? = call getLatestOrderNr()}");

//            Registering the out parameter of the function (return type)
//            cstmt.registerOutParameter(1, Types.DATE);
            cstmt.registerOutParameter(1, Types.INTEGER);

//            Setting the input parameters of the function
//            cstmt.setString(2, "Amit");

//            Executing the statement
//            cstmt.execute();
            cstmt.execute();

//            System.out.print("Date of birth: "+cstmt.getDate(1));
            System.out.println("Latest order id = " + cstmt.getInt(1));
            return cstmt.getString(1);

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "1337"; // Test siffra
        }
    }

    public ResultSet displayShoes(){
        try {
            ResultSet results = statement.executeQuery("SELECT article_nr, brand, item_name, color, size, in_stock " +
                    "FROM shoe_search;");

            return results;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet displayShoesWithCategories(){
        try{
            ResultSet results = statement.executeQuery("SELECT category, article_nr, brand, item_name, color, size, in_stock " +
                    "FROM category_search;");
            return results;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
/*
ResultSet results = statement.executeQuery("SELECT " + COLUMN_NAME + ", " + COLUMN_SCORE +
                " FROM " + TABLE_NAME +
                " ORDER BY " + COLUMN_SCORE +
                " LIMIT 5;");

        System.out.println("\nWorld leaderboard (fewest turns): ");
        while (results.next()) {
            System.out.println(counter + ": " + results.getString(COLUMN_NAME) + ", " +
                    results.getInt(COLUMN_SCORE));
            counter++;
        }
        results.close();
 */