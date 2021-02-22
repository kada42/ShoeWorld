package models;

/**
 * Created by Karl Danielsson - JAVA 20B
 * Date: 2021-02-05
 * Time: 15:57
 * Project: ShoeWorld
 */
public class Customer {

    private int membershipNr;
    private final String firstName;
    private final String lastName;
    private String streetAddress;
    private String zipCode;
    private String city;
    private String password;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public int getMembershipNr() {
        return membershipNr;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getPassword() {
        return password;
    }
}
