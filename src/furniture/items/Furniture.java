/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.items;

import furniture.addresses.Address;
import furniture.customers.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Amaan
 */
public abstract class Furniture {

    protected String furnitureType;
    protected int furnitureId;
    protected String typeOfWood;
    protected double price;
    protected int quantity;
    protected ImageIcon image;
    protected List<Furniture> orderedFurniture = new ArrayList<>();
    public boolean itemAdded;

    public Furniture(String furnitureType, int furnitureId, String typeOfWood, int quantity, ImageIcon image) {
        this.furnitureType = furnitureType;
        this.furnitureId = furnitureId;
        this.typeOfWood = typeOfWood;
        this.quantity = quantity;
        this.image = image;

    }

    public String getFurnitureType() {
        return furnitureType;
    }

    public int getFurnitureId() {
        return furnitureId;
    }

    public String getTypeOfWood() {
        return typeOfWood;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public ImageIcon getImage() {
        return image;
    }

    public List<Furniture> getOrderedFurniture() {
        return orderedFurniture;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTypeOfWood(String typeOfWood) {
        this.typeOfWood = typeOfWood;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static void insertFurnitureDb(Furniture f) {
        try {

            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Inersting to customer table
            PreparedStatement ps = connection.prepareStatement("INSERT INTO [furniture] (wood_type, furniture_type, armrests, base_material, diameter, depth, width, drawer_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            //Insert data into corresponding columns
            ps.setString(1, f.getTypeOfWood());
            ps.setString(2, f.getFurnitureType());

            //check what type of furniture then cast to access subclass methods
            switch (f.getFurnitureType()) {
                case "Chair":
                    Chair c = (Chair) f;
                    ps.setString(3, Boolean.toString(c.getHasArmrests()));
                    break;

                case "Table":
                    Table t = (Table) f;
                    ps.setString(4, t.getBaseMaterial());
                    ps.setInt(5, t.getDiameter());
                    break;

                case "Desk":
                    Desk d = (Desk) f;
                    ps.setInt(6, d.getDepth());
                    ps.setInt(7, d.getWidth());
                    ps.setInt(8, d.getNumberOfDrawers());
                    break;

                default:
                    break;
            }

            ps.executeUpdate();

            ps.close();
            connection.close();

        }
        catch (SQLException e) {
            Logger.getLogger(Furniture.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void createOrder(Address a, Customer c) {
        try {

            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Inersting to customer table
            PreparedStatement ps = connection.prepareStatement("INSERT INTO [order] (customer_id, address_postcode, address_name_number) VALUES (?, ?, ?)");

            //Insert data into corresponding columns
            ps.setInt(1, c.getCustomerId());
            ps.setString(2, a.getPostcode());
            ps.setString(3, a.getNameOrNumber());

            ps.executeUpdate();

            ps.close();
            connection.close();

        }
        catch (SQLException e) {
            Logger.getLogger(Furniture.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void createOrderDetails(Furniture f) {
        try {

            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Inersting to customer table
            PreparedStatement ps = connection.prepareStatement("INSERT INTO [order-details] (order_id, furniture_id, quantity) VALUES (?, ?, ?)");

            //Insert data into corresponding columns
            ps.setInt(1, getOrderId());
            ps.setInt(2, getDbFurnitureId());
            ps.setInt(3, f.getQuantity());

            ps.executeUpdate();

            ps.close();
            connection.close();

        }
        catch (SQLException e) {
            Logger.getLogger(Furniture.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static int getDbFurnitureId() {
        int itemId = 0;

        try {
            //Connect to DB
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Prepare SQL Statement
            Statement statement = connection.createStatement();

            //Query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM furniture ORDER BY furniture_id DESC LIMIT 1;");

            itemId = resultSet.getInt("furniture_id");

            resultSet.close();
            connection.close();
        }
        catch (SQLException e) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
        }

        return itemId;
    }

    public static int getOrderId() {
        int itemId = 0;

        try {
            //Connect to DB
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

            //Prepare SQL Statement
            Statement statement = connection.createStatement();

            //Query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM [order] ORDER BY order_id DESC LIMIT 1;");

            itemId = resultSet.getInt("order_id");

            resultSet.close();
            connection.close();
        }
        catch (SQLException e) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
        }

        return itemId;
    }

    @Override
    public String toString() {
        return "\n" + furnitureType + "\nItem ID:" + furnitureId + "\nWood Type: " + typeOfWood + "\nPrice: £" + String.format("%.2f", price) + "\nQuantity: " + quantity;
    }

}
