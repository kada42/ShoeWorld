/**
 * Created by Karl Danielsson - JAVA 20B
 * Date: 2021-02-08
 * Time: 20:32
 * Project: ShoeWorld
 */
public class Review {

    private int id;
    private String reviewText;
    private Customer customerID;
    private Grade gradeID;

    public int getId() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public Grade getGradeID() {
        return gradeID;
    }
}
