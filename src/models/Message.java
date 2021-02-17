package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-17 <br>
 * Time: 11:40 <br>
 * Project: ShoeWorld <br>
 */
public class Message {

    private double rating;
    private List<String> comments;

    public Message(){
        comments = new ArrayList<>();
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void addComment(String comment){
        comments.add(comment);
    }

    public List<String> getComments() {
        return comments;
    }
}
