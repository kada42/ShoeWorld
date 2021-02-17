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
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/shoe_worlddb2";
    private static final String USER_NAME = "testuser";
    private static final String PASSWORD = "testuser";

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

    public void addToCart(int customerID, String shoeArticleNr, int orderID){

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

    public String getLatestOrderNr(){

        return "";
    }

    public ResultSet displayShoes(){
        try {
            ResultSet results = statement.executeQuery("SELECT article_nr, brand, item_name, color, size, in_stock " +
                    "FROM shoe_search;");

            return results;
            /*
            create or replace view shoe_search as
            select article_nr, b.brand, item_name, color, si.size, in_stock
            from shoes s
            join brands b on b.id = s.brand_id
            join sizes si on si.id = s.size_id
            order by article_nr;
             */
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
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