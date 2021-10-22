/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.gui;

import furniture.addresses.Address;
import furniture.customers.Customer;
import furniture.items.Chair;
import furniture.items.Desk;
import furniture.items.Furniture;
import static furniture.items.Furniture.createOrder;
import static furniture.items.Furniture.createOrderDetails;
import static furniture.items.Furniture.insertFurnitureDb;
import furniture.items.Table;
import static furnituremain.FurnitureApp.calcPrice;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Amaan
 */
public class OrderDetailsPanel extends JPanel implements ActionListener, MouseListener {

    JFrame cartFrame;
    JPanel orderItems;
    JPanel detailsPanel;

    static Map<Furniture, JLabel> cartItems = new HashMap<>();

    JButton viewAllOrdersBtn;
    JTable orderDetailsTable;

    JButton submitBtn;
    Customer customerOrder;
    Address customerAddressOrder;

    JButton viewBtn;
    JButton clearCartBtn;

    JLabel customerIdLabel;
    JLabel customerNameLabel;
    JLabel customerAddressLabel;

    static JLabel totalCostLabel;
    static double totalCost;

    public OrderDetailsPanel(Customer customer, Address address) {

        customerOrder = customer;
        customerAddressOrder = address;

        this.setLayout(new BorderLayout());

        detailsPanel = new JPanel();

        detailsPanel.setBackground(Color.decode("#8E96CB"));

        detailsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 0, 20, 0);  //30px below each textfield
        gbc.anchor = GridBagConstraints.WEST;

        viewAllOrdersBtn = new JButton("View All Orders");
        customButton(viewAllOrdersBtn);

        viewAllOrdersBtn.addActionListener(this);

        gbc.gridy = 0;
        detailsPanel.add(viewAllOrdersBtn, gbc);

        viewBtn = new JButton("View Cart");
        customButton(viewBtn);

        viewBtn.addActionListener(this);

        gbc.gridy = 1;
        detailsPanel.add(viewBtn, gbc);

        customerIdLabel = new JLabel("Customer ID: " + customer.getCustomerId());
        customLabel(customerIdLabel);

        gbc.gridy = 2;
        detailsPanel.add(customerIdLabel, gbc);

        customerNameLabel = new JLabel("Name: " + customer.getFirstName() + " " + customer.getLastName());
        customLabel(customerNameLabel);

        gbc.gridy = 3;
        detailsPanel.add(customerNameLabel, gbc);

        customerAddressLabel = new JLabel("<html>Address: " + address.getPostcode()
                + " , " + address.getNameOrNumber() + " " + address.getLine1() + ",<br>" + address.getTownOrCity() + "</html>");  //html + br tags used to make new lines

        customLabel(customerAddressLabel);

        gbc.gridy = 4;
        detailsPanel.add(customerAddressLabel, gbc);

        totalCostLabel = new JLabel("Total Cost: £" + String.format("%.2f", totalCost));
        customLabel(totalCostLabel);

        gbc.gridy = 5;
        detailsPanel.add(totalCostLabel, gbc);

        submitBtn = new JButton("Submit Order");
        customButton(submitBtn);
        submitBtn.addActionListener(this);

        gbc.gridy = 6;
        detailsPanel.add(submitBtn, gbc);

        this.add(detailsPanel, BorderLayout.CENTER);

        clearCartBtn = new JButton("Clear Cart");
        clearCartBtn.addActionListener(this);
    }

    public void customLabel(JLabel label) {
        Font font = new Font("Calibri", Font.PLAIN, 20);
        Color fontColor = Color.decode("#2C3D54");

        label.setFont(font);
        label.setForeground(fontColor);
    }

    public void customButton(JButton btn) {
        Color btnColor = Color.decode("#5271FF");
        Font buttonFont = new Font("Calibri", Font.PLAIN, 25);

        btn.setBackground(btnColor);
        btn.setFont(buttonFont);
        btn.setForeground(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewBtn) {
            cartFrame = new JFrame("Cart");

            cartFrame.setSize(600, 600);
            cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cartFrame.setResizable(true);
            cartFrame.getContentPane().setBackground(Color.decode("#2C3D54"));

            cartFrame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 20, 0);  //30px below each textfield

            orderItems = new JPanel();

            orderItems.setLayout(new GridLayout(4, 4, 20, 20));

            orderItems.setBackground(Color.decode("#2C3D54"));

            customButton(clearCartBtn);

            gbc.gridy = 0;
            cartFrame.add(clearCartBtn, gbc);

            for (Furniture f : cartItems.keySet()) { //creating labels for each ordered item
                if (!f.itemAdded) {
                    JLabel item = new JLabel(f.getImage());  //indexOf creates an ID for the item

                    item.addMouseListener(this);

                    cartItems.put(f, item);

                    f.itemAdded = true;  //prevents same item being added multiple times
                }
            }

            for (JLabel l : cartItems.values()) {  //add each furniture item image to cart
                orderItems.add(l);
            }

            gbc.gridy = 1;
            cartFrame.add(orderItems, gbc);

            cartFrame.setVisible(true);
        }

        else if (e.getSource() == clearCartBtn) {
            int confirm = JOptionPane.showConfirmDialog(cartFrame, "Are you sure you want to clear all items?", "Clear Cart", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (confirm == 0) {
                cartItems.clear();
                totalCost = 0;
                totalCostLabel.setText("Total Cost: £" + String.format("%.2f", totalCost));

                orderItems.removeAll();

                orderItems.revalidate(); //refreshes panel
                orderItems.repaint();
            }

        }

        else if (e.getSource() == submitBtn) {
            int confirm = JOptionPane.showConfirmDialog(detailsPanel, "Are you sure you want to submit this order", "Submit Order", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (confirm == 0) {
                if (!cartItems.isEmpty()) {
                    createOrder(customerAddressOrder, customerOrder);  //adds data to table [order]

                    for (Furniture f : cartItems.keySet()) {

                        //information to send to DB
                        insertFurnitureDb(f); //adds data to table [furniture]
                        createOrderDetails(f); //adds data to table [order-details]

                    }

                    JOptionPane.showMessageDialog(cartFrame, "Order successfully added to database", "Order Information", JOptionPane.INFORMATION_MESSAGE);
                    MainPanel2.frame.dispose();  //closes frame when insert to DB is complete

                }
                else {
                    JOptionPane.showMessageDialog(cartFrame, "Cart is emty - cannot submit", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        }

        else if (e.getSource() == viewAllOrdersBtn) {

            String[] columnHeadings = {"Order ID", "Customer ID", "Postcode", "Address Number"};

            DefaultTableModel model = new DefaultTableModel(0, columnHeadings.length); //0 starting rows and 4 columns
            model.setColumnIdentifiers(columnHeadings); //column headers

            JTable table = new JTable(model);

            try {
                //Connect to DB
                Connection connection = DriverManager.getConnection("jdbc:sqlite:db/customers.db");

                //Prepare SQL Statement
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM [order]");

                ResultSet resultSet = preparedStatement.executeQuery();

                //Assign results to variables
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int customerId = resultSet.getInt("customer_id");
                    String postcode = resultSet.getString("address_postcode");
                    String number = resultSet.getString("address_name_number");

                    Object[] row = {orderId, customerId, postcode, number};

                    model.addRow(row);

                }

                resultSet.close();
                connection.close();

            }
            catch (SQLException err) {
                Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, err);
            }

            JOptionPane.showMessageDialog(null, new JScrollPane(table));  //add table to dialog so column names visible

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel clickedLabel = (JLabel) e.getSource();

        if (e.getButton() == MouseEvent.BUTTON1) {  //if the jlabel on the panel is in the array list

            //finding map key from value
            for (Entry<Furniture, JLabel> entry : cartItems.entrySet()) {  //loop through key value pairs

                if (entry.getValue() == clickedLabel) { //if key with desired value is found
                    JOptionPane.showMessageDialog(cartFrame, entry.getKey().toString(), "Item Information", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }

        }
        else if (e.getButton() == MouseEvent.BUTTON2) { //middle button   && cartItems.containsValue((JLabel) e.getSource())

            for (Entry<Furniture, JLabel> entry : cartItems.entrySet()) {  //loop through key value pairs

                if (entry.getValue() == clickedLabel) { //if key with desired value is found

                    Furniture furnitureObject = entry.getKey();
                    String furnitureType = furnitureObject.getFurnitureType();

                    //Create textfields and panel to add to input dialogue
                    JTextField editQuantity = new JTextField(Integer.toString(furnitureObject.getQuantity()));
                    editQuantity.setColumns(3);

                    JPanel editItemPanel = new JPanel();

                    editItemPanel.add(new JLabel("Quantity:"));
                    editItemPanel.add(editQuantity);

                    if (furnitureType.equals("Chair")) {
                        Chair chairObject = (Chair) furnitureObject;

                        String[] armrestOptions = {"Yes", "No"};

                        JComboBox editArmrests = new JComboBox(armrestOptions);

                        if (!chairObject.getHasArmrests()) {   //if chair has armrests selected item stays as 'Yes'
                            editArmrests.setSelectedIndex(1);
                        }

                        editItemPanel.add(new JLabel("Armrests:"));
                        editItemPanel.add(editArmrests);

                        int confirmEdit = JOptionPane.showConfirmDialog(cartFrame, editItemPanel, "Edit Chair", JOptionPane.OK_CANCEL_OPTION);

                        if (confirmEdit == JOptionPane.OK_OPTION) {
                            int currentQuantity = Integer.parseInt(editQuantity.getText());

                            if (currentQuantity < 1) {
                                JOptionPane.showMessageDialog(cartFrame, "Quantity must be at least 1 - if you no longer want this item remove it by right clicking in the cart", "Unable to edit item", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }

                            double currentPrice = chairObject.getPrice() * chairObject.getQuantity(); //price before changes made

                            chairObject.setQuantity(currentQuantity);
                            chairObject.setHasArmrests(editArmrests.getSelectedItem().equals("Yes"));  //converts 'Yes' & 'No' strings to boolean

                            calcPrice(chairObject);
                            double newPrice = chairObject.getPrice() * chairObject.getQuantity();  //price after changes made

                            totalCost = (totalCost - currentPrice) + newPrice; //adds or removes the difference in cost
                            totalCostLabel.setText("Total Cost: £" + String.format("%.2f", totalCost));
                        }
                    }
                    else if (furnitureType.equals("Table")) {
                        Table tableObject = (Table) furnitureObject;

                        String[] baseOptions = {"Wooden", "Chrome"};

                        JComboBox editBaseMaterial = new JComboBox(baseOptions);

                        if (tableObject.getBaseMaterial().equals("Wooden")) {
                            editBaseMaterial.setSelectedIndex(0);
                        }
                        else {
                            editBaseMaterial.setSelectedIndex(1);

                        }
                        editItemPanel.add(new JLabel("Base Material:"));
                        editItemPanel.add(editBaseMaterial);

                        JTextField editDiameter = new JTextField(Integer.toString(tableObject.getDiameter()));
                        editDiameter.setColumns(3);

                        editItemPanel.add(new JLabel("Diameter:"));
                        editItemPanel.add(editDiameter);

                        int confirmEdit = JOptionPane.showConfirmDialog(cartFrame, editItemPanel, "Edit Table", JOptionPane.OK_CANCEL_OPTION);

                        if (confirmEdit == JOptionPane.OK_OPTION) {
                            int diameterAsInt = Integer.parseInt(editDiameter.getText());
                            int currentQuantity = Integer.parseInt(editQuantity.getText());

                            if (diameterAsInt < 50) {
                                JOptionPane.showMessageDialog(cartFrame, "Table diameter must be a minimum of 50cm", "Unable to edit item", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }

                            if (currentQuantity < 1) {
                                JOptionPane.showMessageDialog(cartFrame, "Quantity must be at least 1 - if you no longer want this item remove it by right clicking in the cart", "Unable to edit item", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }

                            double currentPrice = tableObject.getPrice() * tableObject.getQuantity();

                            tableObject.setQuantity(currentQuantity);
                            tableObject.setBaseMaterial(editBaseMaterial.getSelectedItem().toString());
                            tableObject.setDiameter(diameterAsInt);

                            calcPrice(tableObject);
                            double newPrice = tableObject.getPrice() * tableObject.getQuantity();

                            totalCost = (totalCost - currentPrice) + newPrice;
                            totalCostLabel.setText("Total Cost: £" + String.format("%.2f", totalCost));
                        }
                    }
                    else if (furnitureType.equals("Desk")) {
                        Desk deskObject = (Desk) furnitureObject;

                        String[] drawerOptions = {"1", "2", "3", "4"};

                        JComboBox editDrawerNumber = new JComboBox(drawerOptions);

                        editDrawerNumber.setSelectedIndex(deskObject.getNumberOfDrawers() - 1);

                        editItemPanel.add(new JLabel("Number of Drawers:"));
                        editItemPanel.add(editDrawerNumber);

                        JTextField editWidth = new JTextField(Integer.toString(deskObject.getWidth()));
                        editWidth.setColumns(3);

                        editItemPanel.add(new JLabel("Width:"));
                        editItemPanel.add(editWidth);

                        JTextField editDepth = new JTextField(Integer.toString(deskObject.getDepth()));
                        editDepth.setColumns(3);

                        editItemPanel.add(new JLabel("Depth:"));
                        editItemPanel.add(editDepth);

                        int confirmEdit = JOptionPane.showConfirmDialog(cartFrame, editItemPanel, "Edit Desk", JOptionPane.OK_CANCEL_OPTION);

                        if (confirmEdit == JOptionPane.OK_OPTION) {
                            int widthAsInt = Integer.parseInt(editWidth.getText());  //changing from string to int to set as subclass variables
                            int depthAsInt = Integer.parseInt(editDepth.getText());

                            int currentQuantity = Integer.parseInt(editQuantity.getText());

                            if (widthAsInt < 80 || depthAsInt < 35) {
                                JOptionPane.showMessageDialog(cartFrame, "Desk width must be at least 80cm and depth must be at least 35cm", "Unable to edit item", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }

                            if (currentQuantity < 1) {
                                JOptionPane.showMessageDialog(cartFrame, "Quantity must be at least 1 - if you no longer want this item remove it by right clicking in the cart", "Unable to edit item", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }

                            double currentPrice = deskObject.getPrice() * deskObject.getQuantity();

                            deskObject.setQuantity(currentQuantity);
                            deskObject.setNumberOfDrawers(Integer.valueOf((String) editDrawerNumber.getSelectedItem()));  //convert string option to integer
                            deskObject.setWidth(widthAsInt);
                            deskObject.setDepth(depthAsInt);

                            calcPrice(deskObject);
                            double newPrice = deskObject.getPrice() * deskObject.getQuantity();

                            totalCost = (totalCost - currentPrice) + newPrice;
                            totalCostLabel.setText("Total Cost: £" + String.format("%.2f", totalCost));
                        }
                    }

                }
            }

        }
        else if (e.getButton() == MouseEvent.BUTTON3) { //right button

            int confirm = JOptionPane.showConfirmDialog(cartFrame, "Are you sure you want to remove this item?", "Clear Cart", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (confirm == 0) {

                for (Entry<Furniture, JLabel> entry : cartItems.entrySet()) {
                    if (entry.getValue() == clickedLabel) {

                        double costReduction = entry.getKey().getPrice() * entry.getKey().getQuantity();

                        totalCost -= costReduction;
                        totalCostLabel.setText("Total Cost: £" + String.format("%.2f", totalCost));

                        break;
                    }
                }

                cartItems.values().remove(clickedLabel); //removes from hashmap by value

                orderItems.remove(clickedLabel);
                orderItems.revalidate(); //refreshes panel
                orderItems.repaint();
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
