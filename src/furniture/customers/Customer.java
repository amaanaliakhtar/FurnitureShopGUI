/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.customers;

import furniture.addresses.Address;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amaan
 */
public abstract class Customer {

    protected int customerId;
    protected int titleId;
    protected String firstName;
    protected String lastName;
    protected List<Address> addresses = new ArrayList<>();

    static PreparedStatement preparedStatement;

    public Customer(int customerId, int titleId, String firstName, String lastName) {
        this.customerId = customerId;
        this.titleId = titleId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getTitleId() {
        return titleId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return "\n" + firstName + " " + lastName + "\nCustomer ID: " + customerId + "\nTitle ID: " + titleId;
    }

    public void addAddress(Address a) {
        addresses.add(a);
    }

    public static void insertIntoDatabase(Customer c, Address a) {
        try {

            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Inersting to customer table
            preparedStatement = connection.prepareStatement("INSERT INTO [customers] (title_id, first_name, last_name) VALUES (?, ?, ?)");

            //Insert data into corresponding columns
            preparedStatement.setInt(1, c.getTitleId());
            preparedStatement.setString(2, c.getFirstName());
            preparedStatement.setString(3, c.getLastName());

            preparedStatement.executeUpdate();

            //Inserting to customer table
            preparedStatement = connection.prepareStatement("INSERT INTO [addresses] (postcode, name_or_number, line_1, line_2, town_or_city, county) VALUES (?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, a.getPostcode());
            preparedStatement.setString(2, a.getNameOrNumber());
            preparedStatement.setString(3, a.getLine1());
            preparedStatement.setString(4, a.getLine2());
            preparedStatement.setString(5, a.getTownOrCity());
            preparedStatement.setString(6, a.getCounty());

            preparedStatement.executeUpdate();

            //Inserting to customers-addresses table
            preparedStatement = connection.prepareStatement("INSERT INTO [customers-addresses] (customer_id, address_postcode, address_name_or_number, primary_address) VALUES (?, ?, ?, ?)");

            preparedStatement.setInt(1, c.getCustomerId());
            preparedStatement.setString(2, a.getPostcode());
            preparedStatement.setString(3, a.getNameOrNumber());
            preparedStatement.setInt(4, a.getIsPrimaryAddress());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        }
        catch (SQLException e) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static Customer findCustomerById(int id) {
        try {
            //Connect to DB
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Prepare SQL Statement
            preparedStatement = connection.prepareStatement("SELECT * FROM [customers] WHERE customer_id=?");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Customer customer = null;

            //Assign results to variables
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                int titleId = resultSet.getInt("title_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                customer = new NonTrade(customerId, titleId, firstName, lastName);  //creating customer object from data
//                System.out.println(customer.toString());
            }

            resultSet.close();
            connection.close();

            return customer;
        }
        catch (SQLException e) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static Address findCustomerAddress(int customerId) {
        try {
            //Connect to DB
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Prepare SQL Statement
            preparedStatement = connection.prepareStatement("SELECT * FROM [customers-addresses] WHERE customer_id=?");

            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            String postcode = "";
            String name = "";

            //Assign results to variables
            while (resultSet.next()) {
                postcode = resultSet.getString("address_postcode");

                name = resultSet.getString("address_name_or_number");

//                System.out.println(postcode + name);
            }

            preparedStatement = connection.prepareStatement("SELECT * FROM [addresses] WHERE postcode=? AND name_or_number=?");

            preparedStatement.setString(1, postcode);
            preparedStatement.setString(2, name);
            resultSet = preparedStatement.executeQuery();

            Address address = null;

            while (resultSet.next()) {

                String line1 = resultSet.getString("line_1");
                String line2 = resultSet.getString("line_2");
                String townOrCity = resultSet.getString("town_or_city");
                String county = resultSet.getString("county");

                address = new Address(postcode, name, line1, line2, townOrCity, county);
            }

            resultSet.close();
            connection.close();

            return address;
        }
        catch (SQLException e) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;

    }

    public static int convertTitleId(String title) {
        HashMap<String, Integer> titles = new HashMap<>();

        int titleId = 0;

        titles.put("Mr.", 1);
        titles.put("Mrs.", 2);
        titles.put("Miss", 3);
        titles.put("Dr.", 4);
        titles.put("Ms.", 5);

        for (String i : titles.keySet()) {
            if (i.equals(title)) {
                titleId = titles.get(i);
            }
        }

        return titleId;

    }

    public static int returnLastId() {
        int lastId = 0;

        try {
            //Connect to DB
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Prepare SQL Statement
            Statement statement = connection.createStatement();

            //Query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers ORDER BY customer_id DESC LIMIT 1;"); // top customer from customers in desc order

            lastId = resultSet.getInt("customer_id");

            resultSet.close();
            connection.close();
        }
        catch (SQLException e) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
        }

        return lastId + 1;
    }

    public static boolean checkCustomerExists(int customerId) {
        boolean exists = false;
        try {
            //Connect to DB
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Prepare SQL Statement
            preparedStatement = connection.prepareStatement("SELECT customers.customer_id FROM [customers] WHERE customers.customer_id=? LIMIT 1;");

            preparedStatement.setInt(1, customerId);
            //Query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {  //if the customer exists - only one result should be found
                exists = true;
            }
            resultSet.close();
            connection.close();
        }
        catch (SQLException e) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
        }

        return exists;  //false if customer not found
    }

}
