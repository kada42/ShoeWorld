package models;

import java.time.LocalDate;

/**
 * Created by Karl Danielsson - JAVA 20B
 * Date: 2021-02-08
 * Time: 20:29
 * Project: ShoeWorld
 */
public class Order {

    private int id;
    private Customer customer;
    private LocalDate orderDate;

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
