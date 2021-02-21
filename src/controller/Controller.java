package controller;

import database.Database;
import gui.Window;
import models.Review;
import models.ShoeView;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;

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
    int rater = 0;

    public Controller(Window window){
        this.w = window;
        db = new Database();
        setUpShoeSearchButtonListener();
        setUpAllCategorySearchListener();
        setUpAddToCartListener();
        setUpViewCartListener();
        setUpNewOrderListener();
        setUpRateRadioButtons();
        setUpSendGradeListener();
        setUpSearchAveGradeListener();
    }

    public boolean checkCredentials(int _membershipNr, String _password){
        return db.checkCredentials(_membershipNr, _password);
    }

    public void setMembershipNr(int membershipNr){
        this.membershipNr = membershipNr;
    }

    public void setTitleName(){
        String name = db.getNameFromDatabase(membershipNr);
        w.getNameLabel().setText(" Hej " + name +" och välkommen till Shoe World!");
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

    public void setUpAddToCartListener() {
        w.getAddToCart().addActionListener(l -> {
            int affectedRows;
            int articleNr;
            try {
                articleNr = Integer.parseInt(w.getArticleNrFieldCartAdd().getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Article number can only contain numbers.");
                return;
            }

            if (w.getOrderNrFieldCartAdd().getText().isEmpty()) {
                affectedRows = db.addToCart(membershipNr, articleNr, 0);
            } else {
                int orderNr = Integer.parseInt(w.getOrderNrFieldCartAdd().getText().trim());
                affectedRows = db.addToCart(membershipNr, articleNr, orderNr);
            }
            if (affectedRows == 0)
                JOptionPane.showMessageDialog(null, "Could not add to cart.\nPlease contact our service-desk.");
            else JOptionPane.showMessageDialog(null, "Successfully added to cart!");
            w.getOrderNrFieldCartAdd().setText(db.getLatestOrderNr());
        });
    }

    public void setUpViewCartListener(){
        w.getViewCart().addActionListener(l -> {
            int orderNr = Integer.parseInt(w.getOrderNrFieldCartAdd().getText().trim());

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

    public void setUpSendGradeListener(){
        w.getSendGrade().addActionListener(l -> {
            if(rater == 0) {
                JOptionPane.showMessageDialog(null,"You must choose a rating from the 4 options.");
                return;
            }
            int articleNr;
            String comment = w.getCommentGradeArea().getText();
            try{
                articleNr = Integer.parseInt(w.getArticleNrFieldGrade().getText().trim());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Article number can only contain numbers.");
                return;
            }

            int affectedRows = db.sendGrade(comment,articleNr,membershipNr,rater);

            if(affectedRows == 0) JOptionPane.showMessageDialog(null,"Could not send review to the database.\nPlease contact our service-desk.");
            else JOptionPane.showMessageDialog(null,"Successfully submitted review!");
        });
    }

    public void setUpSearchAveGradeListener(){
        w.getSearchAveGrade().addActionListener(l -> {
            int articleNr;
            try{
                articleNr = Integer.parseInt(w.getCheckArticleNrField().getText().trim());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Article number can only contain numbers.");
                return;
            }

            List<Review> list = db.getReviews(articleNr);
            w.getInfoWindow().setText("");
            for(Review r : list){
                w.getInfoWindow().append(r.getGradeID().getGrade() + " - ");
                appendStars(r.getGradeID().getPoints());
                w.getInfoWindow().append(r.getReviewText() + "\n");
                w.getInfoWindow().append("-------------------------------------------------------------------------\n");
            }
            try {
                double averageGrade = list.stream().mapToInt(r -> r.getGradeID().getPoints()).average().getAsDouble();
                if(averageGrade % 1 == 0) w.getAveGradeScore().setText(String.format("%.0f",averageGrade));
                else if(averageGrade % 0.10 == 0) w.getAveGradeScore().setText(String.format("%.1f",averageGrade)); // den räknar ej rätt på modulus här
                else w.getAveGradeScore().setText(String.format("%.2f",averageGrade));
            }catch(NoSuchElementException ignored){ }
        });
    }

    private void appendStars(int points){
        for(int i = 1; i <= points; i++) w.getInfoWindow().append("\u2605"); // full star
        for(int i = 1; i <= 4-points; i++) w.getInfoWindow().append("\u2606"); // empty star
        w.getInfoWindow().append("\n");
    }
}
