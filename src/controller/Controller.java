package controller;

import database.Database;
import gui.Window;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-17 <br>
 * Time: 10:16 <br>
 * Project: ShoeWorld <br>
 */
public class Controller {

    Window w;
    Database db;

    public Controller(Window window){
        this.w = window;
        db = new Database();
        setUpShoeSearchButtonListener();
    }

    public void setUpShoeSearchButtonListener(){
        w.getAllShoes().addActionListener(l -> {
            ResultSet result = db.displayShoes();
            w.getInfoWindow().setText("");
            try {
                while (result.next()) {
                    w.getInfoWindow().append(
                            result.getString("article_nr") + " " +
                            result.getString("brand") + " " +
                            result.getString("item_name") + " " +
                            result.getString("color") + " " +
                            result.getString("size") + " " +
                            result.getString("in_stock") + "\n");
                }
                /*
                select article_nr, b.brand, item_name, color, si.size, in_stock
                System.out.println(counter + ": " + results.getString(COLUMN_NAME) + ", " +
                    results.getInt(COLUMN_SCORE));
                 */

            }catch(SQLException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
    }

}
