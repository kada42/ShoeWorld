package controller;

import database.Database;
import gui.Window;
import models.ShoeView;

import javax.swing.*;
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
    int rater;

    public Controller(Window window){
        this.w = window;
        db = new Database();
        setUpShoeSearchButtonListener();
        setUpAllCategorySearchListener();
        setUpAddToCartListener();
        setUpViewCartListener();
        setUpNewOrderListener();
        setUpRateRadioButtons();
    }

    public boolean checkCredentials(int _membershipNr, String _password){
        return db.checkCredentials(_membershipNr, _password);
    }

    public void setMembershipNr(int membershipNr){
        this.membershipNr = membershipNr;
    }

    public void setTitleName(){
        String name = db.getNameFromDatabase(membershipNr);
        w.getNameLabel().setText("Hej " + name);
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
            int affectedRows;
            int articleNr;
            try{
                articleNr = Integer.parseInt(w.getArticleNrFieldCartAdd().getText().trim());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Article number can only contain numbers.");
                return;
            }

            if(w.getOrderNrFieldCartAdd().getText().isEmpty()){
                affectedRows = db.addToCart(membershipNr,articleNr,0);
            }
            else {
                int orderNr = Integer.parseInt(w.getOrderNrFieldCartAdd().getText().trim());
                affectedRows = db.addToCart(membershipNr, articleNr, orderNr);
            }
            if(affectedRows == 0) JOptionPane.showMessageDialog(null,"Could not add to cart.\nPlease contact our service-desk.");
            else JOptionPane.showMessageDialog(null,"Successfully added to cart!");
            w.getOrderNrFieldCartAdd().setText(db.getLatestOrderNr());
        });
    }

    public void setUpViewCartListener(){
        w.getViewCart().addActionListener(l -> {
            int orderNr = Integer.parseInt(w.getOrderNrFieldCartAdd().getText());
            List<ShoeView> list = db.checkCart(orderNr);

            w.getInfoWindow().setText("Row | Article nr | Brand  | Item name   | Color | Size | Order date\n");
            w.getInfoWindow().append("-------------------------------------------------------------------\n");
            int row = 1;
            for (ShoeView i : list){
                w.getInfoWindow().append(
                        String.format("%-5d %-12d %-8s %-13s %-7s %-6d %-10s \n",
                            row,
                            i.getArticleNr(),
                            i.getBrand(),
                            i.getItemName(),
                            i.getColor(),
                            i.getSize(),
                            i.getDate()));
                row++;
            }
        });
    }

    public void setUpNewOrderListener(){
        w.getNewOrder().addActionListener(l -> {
            w.getOrderNrFieldCartAdd().setText("");
        });
    }

   public void setUpRateRadioButtons(){
        for(JRadioButton rb : w.getRateButtons()){
            rb.addActionListener(l -> {
                switch (rb.getText()) {
                    case "Very satisfied" -> rater = 1;
                    case "Satisfied" -> rater = 2;
                    case "A little satisfied" -> rater = 3;
                    default -> rater = 4;
                }
            });
        }
   }

}
