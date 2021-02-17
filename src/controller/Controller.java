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
    int membershipNr;

    public Controller(Window window){
        this.w = window;
        db = new Database();
        setUpShoeSearchButtonListener();
        setUpAllCategorySearchListener();
        setUpAddToCartListener();
    }

    public boolean checkCredentials(int _membershipNr, String _password){
        return db.checkCredentials(_membershipNr, _password);
    }

    public void setMembershipNr(int membershipNr){
        this.membershipNr = membershipNr;
    }

    public void setUpShoeSearchButtonListener(){
        w.getAllShoes().addActionListener(l -> {
            ResultSet result = db.displayShoes();

            w.getInfoWindow().setText("Article nr | Brand  | Item name   | Color | Size | In stock\n");
            try {
                while (result.next()) {
                    w.getInfoWindow().append(
                        String.format("%-13s %-9s %-14s %-8s %-7s %-8s \n",
                            result.getString("article_nr"),
                            result.getString("brand"),
                            result.getString("item_name"),
                            result.getString("color"),
                            result.getString("size"),
                            result.getString("in_stock")));
                }
                result.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void setUpAllCategorySearchListener(){
        w.getAllCategories().addActionListener(l -> {
            ResultSet result = db.displayShoesWithCategories();

            w.getInfoWindow().setText("Category | Article nr | Brand  | Item name   | Color | Size | In stock\n");
            try {
                while (result.next()) {
                    w.getInfoWindow().append(
                        String.format("%-11s %-13s %-9s %-14s %-8s %-7s %-8s \n",
                            result.getString("category"),
                            result.getString("article_nr"),
                            result.getString("brand"),
                            result.getString("item_name"),
                            result.getString("color"),
                            result.getString("size"),
                            result.getString("in_stock")));
                }
                result.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void setUpAddToCartListener(){
        w.getAddToCart().addActionListener(l -> {
            int articleNr = Integer.parseInt(w.getArticleNrFieldCartAdd().getText().trim());
            int orderNr = Integer.parseInt(w.getOrderNrFieldCartAdd().getText().trim());
            db.addToCart(1,articleNr,orderNr);

            w.getOrderNrFieldCartAdd().setText(db.getLatestOrderNr());
        });
    }

}
