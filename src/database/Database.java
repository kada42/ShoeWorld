package database;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import models.*;

import javax.swing.*;
import java.io.FileInputStream;
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

    private static String CONNECTION_STRING;
    private static String USER_NAME;
    private static String PASSWORD;

    /**
     * Constructor that reads in database login credentials from a properties file.
     */
    public Database(){
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("src/Properties.properties"));

            CONNECTION_STRING = p.getProperty("CONNECTION_STRING","");
            USER_NAME = p.getProperty("USER_NAME","");
            PASSWORD = p.getProperty("PASSWORD","");

        } catch(IOException e){
            System.out.println("******************ERROR********************");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("******************ERROR********************");
        }
    }

    /**
     * Method that check in the database if the password is correct for a given membership number
     * @param membershipNr selfexplanatory
     * @param password selfexplanatory
     * @return boolean isCorrect
     */
    public boolean checkCredentials(int membershipNr, String password){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT isPasswordCorrect(?,?);")){

            statement.setInt(1,membershipNr);
            statement.setString(2,password);
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

    public boolean doesShoeExist(int articleNr){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT doesShoeExist(?);")){

            statement.setInt(1,articleNr);
            ResultSet result = statement.executeQuery();

            result.next();
            boolean exists = result.getBoolean(1);

            result.close();
            return exists;

        }catch(SQLException e){
            return false;
        }
    }

    /**
     * Method that fetches the name from customers depending on membership_nr
     * @param membershipNr int that represents a membership_nr
     * @return String with corresponding full name of that customer
     */
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

    /**
     * Method that calls the function addToCart in database
     * @param membershipNr int clients membership_nr
     * @param shoeArticleNr int a shoes article_nr
     * @param orderID int an orders order_nr
     * @return int indicating amount of rows affected
     */
    public int addToCart(int membershipNr, int shoeArticleNr, int orderID){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             CallableStatement cstmt = connection.prepareCall("Call addToCart(?,?,?);")) {

            cstmt.setInt(1, membershipNr);
            cstmt.setInt(2, shoeArticleNr);
            cstmt.setInt(3, orderID);

            return cstmt.executeUpdate();

        }catch(SQLException e){
            return 0;
        }
    }

    /**
     * Method that loads a list with from the database with ShoeViews objects
     * which are included in the current order
     * @param orderNr int current order nr
     * @return List<ShoeView> list
     */
    public List<ShoeView> displayCart(int orderNr){
        List<ShoeView> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM show_client_order where orderNr = ?;")) {

            statement.setInt(1,orderNr);
            ResultSet r = statement.executeQuery();
            while(r.next()){
                list.add(new ShoeView(
                        r.getInt("article_nr"),
                        r.getString("brand"),
                        r.getString("item_name"),
                        r.getString("color"),
                        r.getInt("size"),
                        r.getString("order_date")));
            }
            r.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * Method that sends a review to the database
     * @param text String containing the review text
     * @param shoeArticleNr int that contains the shoe article nr
     * @param customerID int containing the customers membership nr
     * @param rate int the contains the current choice of rate point
     * @return int affected rows in the database
     */
    public int sendGrade(String text, int shoeArticleNr, int customerID, int rate){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             CallableStatement cstmt = connection.prepareCall("Call rate(?,?,?,?);")) {

            cstmt.setString(1, text);
            cstmt.setInt(2, shoeArticleNr);
            cstmt.setInt(3, customerID);
            cstmt.setInt(4, rate);

            int rows = cstmt.executeUpdate();

            return rows;

        } catch (MysqlDataTruncation e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Comment can only be 512 characters long.");
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Method that loads a list with reviews from a specific shoe
     * @param shoeArticleNr int containing the shoe article nr
     * @return List<Review>
     */
    public List<Review> getReviews(int shoeArticleNr){
        List<Review> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM shoe_reviews where shoe_article_nr = ?;")) {

            statement.setInt(1,shoeArticleNr);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                list.add(new Review(rs.getString("review_text"),
                        new Grade(rs.getString("grade"),rs.getInt("points")),
                        new Customer(rs.getString("first_name"),rs.getString("last_name"))));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Method that checks a membership_nr towards a given password
     * @return String of the order_nr for easier input into a textfield
     */
    public String getLatestOrderNr(){
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT getLatestOrderNr();")){

            ResultSet result = statement.executeQuery();
            result.next();
            return result.getString(1);

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "0"; // Wrong input that can be changed on later try
        }
    }

    /**
     * Method that loads a list from the database with ShoeViews objects
     * to display in a textarea without the categories
     * @return List<ShowView> list
     */
    public List<ShoeView> displayShoes(){
        List<ShoeView> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet r = statement.executeQuery("SELECT * FROM shoe_search;");

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
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * Method that loads a list from the database with ShoeViews objects
     * to display in a textarea with the categories to view
     * @return List<ShowView> list
     */
    public List<ShoeView> displayShoesWithCategories(){
        List<ShoeView> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement()){

            ResultSet result = statement.executeQuery("SELECT * FROM category_search;");

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
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return list;
    }

}
