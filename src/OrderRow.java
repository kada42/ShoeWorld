/**
 * Created by Karl Danielsson - JAVA 20B
 * Date: 2021-02-08
 * Time: 20:31
 * Project: ShoeWorld
 */
public class OrderRow {

    private int id;
    private int quantity;
    private Order orderID;
    private Shoe shoeArticleNr;

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Order getOrderID() {
        return orderID;
    }

    public Shoe getShoeArticleNr() {
        return shoeArticleNr;
    }
}
