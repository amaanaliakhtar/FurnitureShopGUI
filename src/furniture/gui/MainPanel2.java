/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.gui;

import furniture.addresses.Address;
import furniture.customers.Customer;
import static furnituremain.FurnitureApp.createFrame;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Amaan
 */
public class MainPanel2 extends JPanel {

    public static JFrame frame;

    public MainPanel2(Customer customer, Address address) {
        frame = createFrame();

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        FurniturePanel furniturePanel = new FurniturePanel();
        this.add(furniturePanel);

        OrderDetailsPanel orderPanel = new OrderDetailsPanel(customer, address);
        this.add(orderPanel);

        frame.setVisible(true);
        frame.add(this);

    }

}
