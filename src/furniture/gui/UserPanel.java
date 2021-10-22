/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.gui;

import furniture.addresses.Address;
import furniture.customers.Customer;
import static furniture.customers.Customer.checkCustomerExists;
import static furniture.customers.Customer.convertTitleId;
import static furniture.customers.Customer.findCustomerAddress;
import static furniture.customers.Customer.findCustomerById;
import static furniture.customers.Customer.insertIntoDatabase;
import static furniture.customers.Customer.returnLastId;
import furniture.customers.NonTrade;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Amaan
 */
public class UserPanel extends JPanel implements FocusListener, ActionListener {

    JPanel userDetailsPanel;

    JLabel heading;

    JComboBox titleInput;

    JTextField firstNameInput;
    JTextField lastNameInput;
    JTextField postcodeInput;
    JTextField nameOrNumberInput;
    JTextField addressLine1Input;
    JTextField addressLine2Input;
    JTextField cityInput;
    JTextField countyInput;

    JButton createCustomerBtn;
    JButton existingCustomerBtn;

    Font font = new Font("Calibri", Font.PLAIN, 20); //Helvetica Nueue

    Color fontColor = Color.decode("#9BA8FF");

    Color background = Color.decode("#2C3D54");

    Border border = BorderFactory.createMatteBorder(0, 0, 5, 0, Color.decode("#8FA0BB"));  //creares line under text fields

    public UserPanel() {
        this.setLayout(new BorderLayout());

        //USER LOGIN DETAILS
        userDetailsPanel = new JPanel();

        userDetailsPanel.setBackground(background);

        userDetailsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL; //makes textfields equal size
        gbc.insets = new Insets(0, 0, 20, 0);  //30px below each textfield

        heading = new JLabel("Enter New Customer:");
        heading.setFont(font);
        heading.setForeground(fontColor);

        gbc.gridy = 0;

        userDetailsPanel.add(heading, gbc);

        //TITLE INPUT
        String[] options = new String[]{"Mr.", "Mrs.", "Miss", "Dr.", "Ms."};

        titleInput = new JComboBox(options);
        titleInput.setFont(font);

        gbc.gridy = 1;

        userDetailsPanel.add(titleInput, gbc);

        //FIRSTNAME INPUT
        firstNameInput = new JTextField("First Name", 20);
        customTextField(firstNameInput);

        gbc.gridy = 2;
        userDetailsPanel.add(firstNameInput, gbc);

        //LASTNAME INPUT
        lastNameInput = new JTextField("Last Name");
        customTextField(lastNameInput);

        gbc.gridy = 3;
        userDetailsPanel.add(lastNameInput, gbc);

        //POSTCODE INPUT
        postcodeInput = new JTextField("Postcode");
        customTextField(postcodeInput);

        gbc.gridy = 4;
        userDetailsPanel.add(postcodeInput, gbc);

        //NAME OR NUMBER INPUT
        nameOrNumberInput = new JTextField("Name or Number");
        customTextField(nameOrNumberInput);

        gbc.gridy = 5;
        userDetailsPanel.add(nameOrNumberInput, gbc);

        //ADDRESS INPUT
        addressLine1Input = new JTextField("Address Line 1");
        customTextField(addressLine1Input);

        gbc.gridy = 6;
        userDetailsPanel.add(addressLine1Input, gbc);

        addressLine2Input = new JTextField("Address Line 2");
        customTextField(addressLine2Input);

        gbc.gridy = 7;
        userDetailsPanel.add(addressLine2Input, gbc);

        //CITY INPUT
        cityInput = new JTextField("City");
        customTextField(cityInput);

        gbc.gridy = 8;
        userDetailsPanel.add(cityInput, gbc);

        countyInput = new JTextField("County");
        customTextField(countyInput);

        gbc.gridy = 9;
        userDetailsPanel.add(countyInput, gbc);

        //Butttons
        createCustomerBtn = new JButton("Create Customer");

        Color btnColor = Color.decode("#5271FF");

        LineBorder btnBorder = new LineBorder(btnColor, 7);

        Font buttonFont = new Font("Calibri", Font.PLAIN, 18);

        createCustomerBtn.setBackground(btnColor);
        createCustomerBtn.setFont(buttonFont);
        createCustomerBtn.setForeground(Color.WHITE);
        createCustomerBtn.setBorder(btnBorder);

        createCustomerBtn.addActionListener(this);

        gbc.gridy = 10;

        gbc.fill = GridBagConstraints.NONE;

        userDetailsPanel.add(createCustomerBtn, gbc);

        existingCustomerBtn = new JButton("Existing Customer?");

        existingCustomerBtn.setBackground(btnColor);
        existingCustomerBtn.setFont(buttonFont);
        existingCustomerBtn.setForeground(Color.WHITE);
        existingCustomerBtn.setBorder(btnBorder);

        existingCustomerBtn.addActionListener(this);

        gbc.gridy = 11;

        userDetailsPanel.add(existingCustomerBtn, gbc);

        this.add(userDetailsPanel, BorderLayout.CENTER); // Add the user input panel to center of larger panel
    }

    public void customTextField(JTextField textfield) {
        textfield.setFont(font);
        textfield.setOpaque(true);  // background non-transparent
        textfield.setBackground(background);
        textfield.setBorder(border);
        textfield.setForeground(fontColor);
        textfield.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField selectedField = (JTextField) e.getSource();
        String fieldText = selectedField.getText();

        // Remove placeholder text when text field is clicked and unchanged
        if (selectedField == firstNameInput && fieldText.equals("First Name")) {
            selectedField.setText("");
        }
        else if (selectedField == lastNameInput && fieldText.equals("Last Name")) {
            selectedField.setText("");
        }
        else if (selectedField == postcodeInput && fieldText.equals("Postcode")) {
            selectedField.setText("");
        }
        else if (selectedField == addressLine1Input && fieldText.equals("Address Line 1")) {
            selectedField.setText("");
        }
        else if (selectedField == addressLine2Input && fieldText.equals("Address Line 2")) {
            selectedField.setText("");
        }
        else if (selectedField == nameOrNumberInput && fieldText.equals("Name or Number")) {
            selectedField.setText("");
        }
        else if (selectedField == cityInput && fieldText.equals("City")) {
            selectedField.setText("");
        }
        else if (selectedField == countyInput && fieldText.equals("County")) {
            selectedField.setText("");
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField selectedField = (JTextField) e.getSource();
        String fieldText = selectedField.getText();

        // Return placeholder text if text field is empty
        if (selectedField == firstNameInput && fieldText.equals("")) {
            selectedField.setText("First Name");
        }
        else if (selectedField == lastNameInput && fieldText.equals("")) {
            selectedField.setText("Last Name");
        }
        else if (selectedField == postcodeInput && fieldText.equals("")) {
            selectedField.setText("Postcode");
        }
        else if (selectedField == addressLine1Input && fieldText.equals("")) {
            selectedField.setText("Address Line 1");
        }
        else if (selectedField == addressLine2Input && fieldText.equals("")) {
            selectedField.setText("Address Line 2");
        }
        else if (selectedField == nameOrNumberInput && fieldText.equals("")) {
            selectedField.setText("Name or Number");
        }
        else if (selectedField == cityInput && fieldText.equals("")) {
            selectedField.setText("City");
        }
        else if (selectedField == countyInput && fieldText.equals("")) {
            selectedField.setText("County");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createCustomerBtn) {

            String title = titleInput.getSelectedItem().toString();
            int titleId = convertTitleId(title);

            String firstName = firstNameInput.getText();

            if (firstName.equals("First Name") || !firstName.matches("[a-zA-Z ]+")) {  //regex - only letters and spaces
                JOptionPane.showMessageDialog(userDetailsPanel, "First name is required must only contain letters");
                firstNameInput.setText("First Name");
                return;
            }

            String lastName = lastNameInput.getText();

            if (lastName.equals("Last Name") || !lastName.matches("[a-zA-Z ]+")) { //regex - only letters and spaces
                JOptionPane.showMessageDialog(userDetailsPanel, "Last name is required must only contain letters");
                lastNameInput.setText("Last Name");
                return;
            }

            String postcode = postcodeInput.getText();

            if (postcode.equals("Postcode") || !postcode.matches("[a-zA-Z0-9 ]+") || postcode.length() > 7) { //regex - letters, numbers, spaces
                JOptionPane.showMessageDialog(userDetailsPanel, "Postcode is required must be less than 8 characters and contain only letters and numbers");
                postcodeInput.setText("Postcode");
                return;
            }

            String nameOrNumber = nameOrNumberInput.getText();

            if (nameOrNumber.equals("Name or Number") || !nameOrNumber.matches("[a-zA-Z0-9 ]+")) { //regex - letters, numbers, spaces
                JOptionPane.showMessageDialog(userDetailsPanel, "Name or Number is required and must contain only letters and numbers");
                nameOrNumberInput.setText("Name or Number");
                return;
            }

            String address1 = addressLine1Input.getText();

            if (address1.equals("Address Line 1") || !address1.matches("[a-zA-Z ]+")) { //regex - letters, numbers, spaces
                JOptionPane.showMessageDialog(userDetailsPanel, "Address Line 1 is required must only contain letters");
                addressLine1Input.setText("Address Line 1");
                return;
            }

            String address2 = addressLine2Input.getText();

            if (address2.equals("Address Line 2")) { //regex - letters, numbers, spaces
                address2 = null;
            }
            else if (!address2.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(userDetailsPanel, "Address Line 2 must only contain letters");
                addressLine2Input.setText("Address Line 2");
                return;
            }

            String city = cityInput.getText();

            if (city.equals("City") || !city.matches("[a-zA-Z ]+")) { //regex - letters, numbers, spaces
                JOptionPane.showMessageDialog(userDetailsPanel, "City is required must only contain letters");
                cityInput.setText("City");
                return;
            }

            String county = countyInput.getText();

            if (county.equals("County")) { //regex - letters, numbers, spaces
                county = null;
            }
            else if (!county.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(userDetailsPanel, "County must only contain letters");
                countyInput.setText("County");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(userDetailsPanel, "Is this the customers primary address?", "Primary Address", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            int customerId = returnLastId();

            Customer customer = new NonTrade(customerId, titleId, firstName, lastName);

            Address customerAddress = new Address(postcode, nameOrNumber, address1, address2, city, county);

            if (confirm == 0) {
                customerAddress.setIsPrimaryAddress(1);  //1 = primary address
            }
            else {
                customerAddress.setIsPrimaryAddress(2); //2 = non primary address
            }

//            System.out.println(customerAddress.toString());
            MainPanel2 mp2 = new MainPanel2(customer, customerAddress); //send details to next window
            //customer.addAddress(customerAddress);

            insertIntoDatabase(customer, customerAddress);
        }

        else if (e.getSource() == existingCustomerBtn) {
            String customerIdString = JOptionPane.showInputDialog(userDetailsPanel, "Please Enter Existing Customer ID");

            if (customerIdString == null) {
                return;
            }
            else if (!customerIdString.matches("[0-9]+") || !checkCustomerExists(Integer.parseInt(customerIdString))) {
                JOptionPane.showMessageDialog(userDetailsPanel, "Customer ID must exist in the database and contain only numbers", "Invalid Customer ID", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int customerId = Integer.parseInt(customerIdString);

            Customer customer = findCustomerById(customerId);

            Address address = findCustomerAddress(customerId);

            MainPanel2 mp2 = new MainPanel2(customer, address);  //Open order frame
        }
    }

}

/*
TODO
 - if existing customer id not found
 - new window
 */
