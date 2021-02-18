package models;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-18 <br>
 * Time: 12:54 <br>
 * Project: ShoeWorld <br>
 */
public class View {

    private String category;
    private int articleNr;
    private String brand;
    private String itemName;
    private String color;
    private int size;
    private int currentStock;

    public View(String category, int articleNr, String brand, String itemName, String color, int size, int currentStock) {
        this.category = category;
        this.articleNr = articleNr;
        this.brand = brand;
        this.itemName = itemName;
        this.color = color;
        this.size = size;
        this.currentStock = currentStock;
    }

    public View(int articleNr, String brand, String itemName, String color, int size, int currentStock) {
        this.articleNr = articleNr;
        this.brand = brand;
        this.itemName = itemName;
        this.color = color;
        this.size = size;
        this.currentStock = currentStock;
    }

    public String getCategory() {
        return category;
    }

    public int getArticleNr() {
        return articleNr;
    }

    public String getBrand() {
        return brand;
    }

    public String getItemName() {
        return itemName;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public int getCurrentStock() {
        return currentStock;
    }
}
