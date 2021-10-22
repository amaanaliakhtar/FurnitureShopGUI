/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.addresses;

import furniture.customers.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amaan
 */
public class Address {

    private String postcode;
    private String nameOrNumber;
    private String line1;
    private String line2;
    private String townOrCity;
    private String county;
    private int isPrimaryAddress;
    private List<Customer> customers = new ArrayList<>();

    public Address(String postcode, String nameOrNumber, String line1, String line2, String townOrCity, String county) {
        this.postcode = postcode;
        this.nameOrNumber = nameOrNumber;
        this.line1 = line1;
        this.line2 = line2;
        this.townOrCity = townOrCity;
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getNameOrNumber() {
        return nameOrNumber;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getTownOrCity() {
        return townOrCity;
    }

    public String getCounty() {
        return county;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public int getIsPrimaryAddress() {
        return isPrimaryAddress;
    }

    public void setIsPrimaryAddress(int isPrimaryAddress) {
        this.isPrimaryAddress = isPrimaryAddress;
    }

    @Override
    public String toString() {
        return postcode + "\n" + nameOrNumber + "\n" + line1 + "\n" + line2 + "\n" + townOrCity + "\n" + county + "\n";
    }

}
