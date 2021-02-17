package models;

/**
 * Created by Karl Danielsson - JAVA 20B
 * Date: 2021-02-08
 * Time: 20:28
 * Project: ShoeWorld
 */
public class BelongsToCategory {

    private int id;
    private Shoe shoe;
    private Category category;

    public int getId() {
        return id;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public Category getCategory() {
        return category;
    }
}
