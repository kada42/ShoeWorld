package models;

/**
 * Created by Karl Danielsson - JAVA 20B
 * Date: 2021-02-08
 * Time: 20:32
 * Project: ShoeWorld
 */
public class Review {

    private int id;
    private final String reviewText;
    private Shoe shoeArticleNr;
    private Customer customerID;
    private final Grade gradeID;

    public Review(String reviewText, Grade grade){
        this.reviewText = reviewText;
        this.gradeID = grade;
    }

    public int getId() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Shoe getShoeArticleNr() {
        return shoeArticleNr;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public Grade getGradeID() {
        return gradeID;
    }
}
