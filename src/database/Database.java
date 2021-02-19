package database;

import models.Message;
import models.ShoeView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-17 <br>
 * Time: 11:05 <br>
 * Project: ShoeWorld <br>
 */
public class Database {

    private static final String DB_NAME = "shoe_worlddb2";
    private static String CONNECTION_STRING;
    private static String USER_NAME;
    private static String PASSWORD;

    // Test data
    int orderNrTest = 13;

    /**
     * Constructor that reads in database login credentials from a properties file.
     */
    public Database(){
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("src/Properties.properties"));

            CONNECTION_STRING = p.getProperty("CONNECTION_STRING","fel1");
            USER_NAME = p.getProperty("USER_NAME","fel2");
            PASSWORD = p.getProperty("PASSWORD","fel3");

        } catch(IOException e){
            System.out.println("******************ERROR********************");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("******************ERROR********************");
        }
    }

    /**
     * Method that check in the database if the password is correct for a given membership number
     * @param _membershipNr selfexplanatory
     * @param _password selfexplanatory
     * @return boolean isCorrect
     */
    public boolean checkCredentials(int _membershipNr, String _password){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT isPasswordCorrect(?,?)")){

            statement.setInt(1,_membershipNr);
            statement.setString(2,_password);
            ResultSet result = statement.executeQuery();

            result.next();
            boolean isCorrect = result.getBoolean(1);

            result.close();
            return isCorrect;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String getNameFromDatabase(int membershipNr){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT first_name, last_name FROM customers WHERE membership_nr = ?;")){

            statement.setInt(1, membershipNr);
            ResultSet results = statement.executeQuery();
            results.next();
            String fName = results.getString("first_name");
            String lName = results.getString("last_name");
            return (fName + " " + lName);

        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    public boolean addToCart(int membershipNr, int shoeArticleNr, int orderID){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             CallableStatement cstmt = connection.prepareCall("{? = call AddToCart(?,?,?)}");) {
            // AddToCart(customerID int, shoeID int, orderID int)

            cstmt.setInt(1, membershipNr);
            cstmt.setInt(2, shoeArticleNr);
            cstmt.setInt(3, orderID);

            boolean action = cstmt.execute();
            System.out.println(action);
            return action;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<String> checkCart(){
        List<String> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement();) {

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public void sendGrade(String shoeArticleNr, String comment){

        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement();) {

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Message getAverageGrade(String shoeArticleNr){
        Message message = new Message();

        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement();) {

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return message;
    }

    // Fixa denna function som login grejen
    public String getLatestOrderNr(){   // måste testas i samband med "addToCart"
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             CallableStatement cstmt = connection.prepareCall("{? = call getLatestOrderNr()}");){
//            Preparing a CallableStatement to call a function
//            CallableStatement cstmt = con.prepareCall("{? = call getDob(?)}");


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

    /**
     * Method that loads a list from the database with ShoeViews objects
     * to display in a textarea without the categories
     * @return List<ShowView> list
     */
    public List<ShoeView> displayShoes(){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet r = statement.executeQuery("SELECT * FROM shoe_search;");

            List<ShoeView> list = new ArrayList<>();

            while(r.next()){
                list.add(new ShoeView(
                        r.getInt("article_nr"),
                        r.getString("brand"),
                        r.getString("item_name"),
                        r.getString("color"),
                        r.getInt("size"),
                        r.getInt("in_stock")));
            }
            r.close();
            return list;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Method that loads a list from the database with ShoeViews objects
     * to display in a textarea with the categories to view
     * @return List<ShowView> list
     */
    public List<ShoeView> displayShoesWithCategories(){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement()){

            ResultSet result = statement.executeQuery("SELECT * FROM category_search;");

            List<ShoeView> list = new ArrayList<>();

            while(result.next()){
                list.add(new ShoeView(
                        result.getString("category"),
                        result.getInt("article_nr"),
                        result.getString("brand"),
                        result.getString("item_name"),
                        result.getString("color"),
                        result.getInt("size"),
                        result.getInt("in_stock")));
            }
            result.close();
            return list;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
