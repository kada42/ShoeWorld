package models;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-18 <br>
 * Time: 12:54 <br>
 * Project: ShoeWorld <br>
 */
public class ShoeView {

    private final String category;
    private final int articleNr;
    private final String brand;
    private final String itemName;
    private final String color;
    private final int size;
    private final int currentStock;
    private final String date;

    public ShoeView(String category, int articleNr, String brand, String itemName, String color, int size, int currentStock) {
        this.category = category;
        this.articleNr = articleNr;
        this.brand = brand;
        this.itemName = itemName;
        this.color = color;
        this.size = size;
        this.currentStock = currentStock;
        this.date = null;
    }

    public ShoeView(int articleNr, String brand, String itemName, String color, int size, int currentStock) {
        this.category = null;
        this.articleNr = articleNr;
        this.brand = brand;
        this.itemName = itemName;
        this.color = color;
        this.size = size;
        this.currentStock = currentStock;
        this.date = null;
    }

    public ShoeView(int articleNr, String brand, String itemName, String color, int size, String date) {
        this.category = null;
        this.articleNr = articleNr;
        this.brand = brand;
        this.itemName = itemName;
        this.color = color;
        this.size = size;
        this.currentStock = 0;
        this.date = date;
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

    public String getDate() {
        return date;
    }
}
