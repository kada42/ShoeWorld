package controller;

import database.Database;
import gui.Window;
import models.ShoeView;

import java.util.List;

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
            List<ShoeView> list = db.displayShoes();

            w.getInfoWindow().setText("Article nr | Brand  | Item name   | Color | Size | In stock\n");
            w.getInfoWindow().append("-----------------------------------------------------------\n");
                for (ShoeView i : list){
                    w.getInfoWindow().append(
                        String.format("%-12d %-8s %-13s %-7s %-6d %-6d \n",
                            i.getArticleNr(),
                            i.getBrand(),
                            i.getItemName(),
                            i.getColor(),
                            i.getSize(),
                            i.getCurrentStock()));
                }
        });
    }

    public void setUpAllCategorySearchListener(){
        w.getAllCategories().addActionListener(l -> {
            List<ShoeView> list = db.displayShoesWithCategories();

            w.getInfoWindow().setText("Category | Article nr | Brand  | Item name   | Color | Size | In stock\n");
            w.getInfoWindow().append("----------------------------------------------------------------------\n");
                for (ShoeView i : list) {
                    w.getInfoWindow().append(
                        String.format("%-10s %-12s %-8s %-13s %-7s %-6s %-6s \n",
                            i.getCategory(),
                            i.getArticleNr(),
                            i.getBrand(),
                            i.getItemName(),
                            i.getColor(),
                            i.getSize(),
                            i.getCurrentStock()));
                }
        });
    }

    public void setUpAddToCartListener(){
        w.getAddToCart().addActionListener(l -> {
            int articleNr = Integer.parseInt(w.getArticleNrFieldCartAdd().getText().trim());
            if(w.getOrderNrFieldCartAdd().getText().isEmpty()){
                db.addToCart(membershipNr,articleNr,0);
            }
            else {
                int orderNr = Integer.parseInt(w.getOrderNrFieldCartAdd().getText().trim());
                db.addToCart(membershipNr, articleNr, orderNr);
            }

            w.getOrderNrFieldCartAdd().setText(db.getLatestOrderNr());
        });
    }

}
