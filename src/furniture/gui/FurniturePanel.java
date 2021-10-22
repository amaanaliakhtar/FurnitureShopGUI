/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.gui;

import furniture.items.Chair;
import furniture.items.Desk;
import furniture.items.Furniture;
import furniture.items.Table;
import static furnituremain.FurnitureApp.calcPrice;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Amaan
 */
public class FurniturePanel extends JPanel implements ActionListener {

    JPanel furnitureGrid;

    ImageIcon walnutDeskImg;
    JLabel walnutDeskItem;
    JButton addWalnutDeskBtn;
    JLabel walnutDeskLabel;

    ImageIcon oakTableImg;
    JLabel oakTableItem;
    JButton addOakTableBtn;
    JLabel oakTableLabel;

    ImageIcon walnutChairImg;
    JLabel walnutChairItem;
    JButton addWalnutChairBtn;
    JLabel walnutChairLabel;

    ImageIcon oakDeskImg;
    JLabel oakDeskItem;
    JButton addOakDeskBtn;
    JLabel oakDeskLabel;

    ImageIcon walnutTableImg;
    JLabel walnutTableItem;
    JButton addWalnutTableBtn;
    JLabel walnutTableLabel;

    ImageIcon oakChairImg;
    JLabel oakChairItem;
    JButton addOakChairBtn;
    JLabel oakChairLabel;

    public FurniturePanel() {
        this.setLayout(new BorderLayout());

        furnitureGrid = new JPanel();

        furnitureGrid.setBackground(Color.decode("#2C3D54"));

        furnitureGrid.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 0, 5, 0);  //5px space below each grid item

        oakChairImg = new ImageIcon("images/oakchair.png");
        oakChairItem = new JLabel(oakChairImg);
        addOakChairBtn = new JButton("Add to Cart");
        oakChairLabel = new JLabel("Oak Chair");

        gbc.gridx = 0;
        gbc.gridy = 0;

        furnitureGrid.add(oakChairItem, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;  // y++ = new row

        addLabelToGrid(oakChairLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        addBtnToGrid(addOakChairBtn, gbc);

        walnutChairImg = new ImageIcon("images/walnutchair.png");
        walnutChairItem = new JLabel(walnutChairImg);
        addWalnutChairBtn = new JButton("Add to Cart");
        walnutChairLabel = new JLabel("Walnut Chair");

        gbc.gridx = 1;  // x++ = new column
        gbc.gridy = 0;

        furnitureGrid.add(walnutChairItem, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;

        addLabelToGrid(walnutChairLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;

        addBtnToGrid(addWalnutChairBtn, gbc);

        oakDeskImg = new ImageIcon("images/oakdesk.png");
        oakDeskItem = new JLabel(oakDeskImg);
        addOakDeskBtn = new JButton("Add to Cart");
        oakDeskLabel = new JLabel("Oak Desk");

        gbc.gridx = 2;
        gbc.gridy = 0;

        furnitureGrid.add(oakDeskItem, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;

        addLabelToGrid(oakDeskLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;

        addBtnToGrid(addOakDeskBtn, gbc);

        walnutTableImg = new ImageIcon("images/walnuttable.png");
        walnutTableItem = new JLabel(walnutTableImg);
        addWalnutTableBtn = new JButton("Add to Cart");
        walnutTableLabel = new JLabel("Walnut Table");

        gbc.gridx = 0;
        gbc.gridy = 3;

        furnitureGrid.add(walnutTableItem, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;

        addLabelToGrid(walnutTableLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;

        addBtnToGrid(addWalnutTableBtn, gbc);

        oakTableImg = new ImageIcon("images/oaktable.png");
        oakTableItem = new JLabel(oakTableImg);
        addOakTableBtn = new JButton("Add to Cart");
        oakTableLabel = new JLabel("Oak Table");

        gbc.gridx = 1;
        gbc.gridy = 3;

        furnitureGrid.add(oakTableItem, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;

        addLabelToGrid(oakTableLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;

        addBtnToGrid(addOakTableBtn, gbc);

        walnutDeskImg = new ImageIcon("images/walnutdesk.png");
        walnutDeskItem = new JLabel(walnutDeskImg);
        addWalnutDeskBtn = new JButton("Add to Cart");
        walnutDeskLabel = new JLabel("Walnut Desk");

        gbc.gridx = 2;
        gbc.gridy = 3;

        furnitureGrid.add(walnutDeskItem, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;

        addLabelToGrid(walnutDeskLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;

        addBtnToGrid(addWalnutDeskBtn, gbc);

        this.add(furnitureGrid);  //WEST
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Map orderDetails = OrderDetailsPanel.cartItems;
        JLabel cartImage = null;

        if (e.getSource() == addOakChairBtn || e.getSource() == addWalnutChairBtn) {
            Furniture chair = null;

            String[] chairOptions = {"Yes", "No"};

            String chairOption = (String) JOptionPane.showInputDialog(furnitureGrid, "Would you like armrests on the chair?", "Chair Options", JOptionPane.QUESTION_MESSAGE, null, chairOptions, null);

            if (chairOption == null) { //if cancel is clicked
                return;
            }

            Boolean hasArmrests = chairOption.equals("Yes");

            String quantityString = JOptionPane.showInputDialog(furnitureGrid, "How many chairs would you like?", "Quantity", JOptionPane.QUESTION_MESSAGE);

            if (quantityString == null || quantityString.equals("")) {  //if cancel is clicked or nothing is entered
                return;
            }

            int quantity = Integer.parseInt(quantityString);

            if (e.getSource() == addOakChairBtn) {
                chair = new Chair("Chair", 1, "Oak", quantity, new ImageIcon("images/oakchairCart.png"), hasArmrests);
            }
            else {
                chair = new Chair("Chair", 2, "Walnut", quantity, new ImageIcon("images/walnutchairCart.png"), hasArmrests);
            }

            cartImage = new JLabel(chair.getImage());

            orderDetails.put(chair, cartImage);

            calcPrice(chair);

            OrderDetailsPanel.totalCost += chair.getPrice() * quantity;
            OrderDetailsPanel.totalCostLabel.setText("Total Cost: £" + String.format("%.2f", OrderDetailsPanel.totalCost));
        }

        else if (e.getSource() == addOakTableBtn || e.getSource() == addWalnutTableBtn) {
            Furniture table = null;

            String[] baseOptions = {"Wooden", "Chrome"};

            String baseOption = (String) JOptionPane.showInputDialog(furnitureGrid, "Select a table base material", "Table Base Options", JOptionPane.QUESTION_MESSAGE, null, baseOptions, null);

            if (baseOption == null) {
                return;
            }

            String baseDiameterString = JOptionPane.showInputDialog(furnitureGrid, "Enter a diameter (must be >= 50)", "Diameter", JOptionPane.QUESTION_MESSAGE);

            if (baseDiameterString == null || baseDiameterString.equals("") || Integer.parseInt(baseDiameterString) < 50) {
                return;
            }

            int baseDiameter = Integer.parseInt(baseDiameterString);

            String quantityString = JOptionPane.showInputDialog(furnitureGrid, "How many tables would you like?", "Quantity", JOptionPane.QUESTION_MESSAGE);

            if (quantityString == null || quantityString.equals("")) {  //if cancel is clicked or nothing is entered
                return;
            }

            int quantity = Integer.parseInt(quantityString);

            if (e.getSource() == addOakTableBtn) {
                table = new Table("Table", 3, "Oak", quantity, new ImageIcon("images/oaktableCart.png"), baseDiameter, baseOption);
            }
            else {
                table = new Table("Table", 4, "Walnut", quantity, new ImageIcon("images/walnuttableCart.png"), baseDiameter, baseOption);
            }

            cartImage = new JLabel(table.getImage());

            orderDetails.put(table, cartImage);

            calcPrice(table);

            OrderDetailsPanel.totalCost += table.getPrice() * quantity;
            OrderDetailsPanel.totalCostLabel.setText("Total Cost: £" + String.format("%.2f", OrderDetailsPanel.totalCost));
        }

        else if (e.getSource() == addOakDeskBtn || e.getSource() == addWalnutDeskBtn) {
            Furniture desk = null;

            String[] drawerOptions = {"1", "2", "3", "4"};

            String drawerOption = (String) JOptionPane.showInputDialog(furnitureGrid, "Select the number of drawers", "Desk Drawer Options", JOptionPane.QUESTION_MESSAGE, null, drawerOptions, null);

            if (drawerOption == null) {
                return;
            }

            int drawerNumber = Integer.parseInt(drawerOption);

            String depthString = JOptionPane.showInputDialog(furnitureGrid, "Enter desk depth (must be >= 35)", "Depth", JOptionPane.QUESTION_MESSAGE);

            if (depthString == null || depthString.equals("") || Integer.parseInt(depthString) < 35) {
                return;
            }

            int depth = Integer.parseInt(depthString);

            String widthString = JOptionPane.showInputDialog(furnitureGrid, "Enter desk width (must be >= 80)", "Width", JOptionPane.QUESTION_MESSAGE);

            if (widthString == null || widthString.equals("") || Integer.parseInt(widthString) < 80) {
                return;
            }

            int width = Integer.parseInt(widthString);

            String quantityString = JOptionPane.showInputDialog(furnitureGrid, "How many desks would you like?", "Quantity", JOptionPane.QUESTION_MESSAGE);

            if (quantityString == null || quantityString.equals("")) {  //if cancel is clicked or nothing is entered
                return;
            }

            int quantity = Integer.parseInt(quantityString);

            if (e.getSource() == addOakDeskBtn) {
                desk = new Desk("Desk", 5, "Oak", quantity, new ImageIcon("images/oakdeskCart.png"), depth, width, drawerNumber);
            }
            else {
                desk = new Desk("Desk", 6, "Walnut", quantity, new ImageIcon("images/walnutdeskCart.png"), depth, width, drawerNumber);
            }

            cartImage = new JLabel(desk.getImage());

            orderDetails.put(desk, cartImage);

            calcPrice(desk);

            OrderDetailsPanel.totalCost += desk.getPrice() * quantity;
            OrderDetailsPanel.totalCostLabel.setText("Total Cost: £" + String.format("%.2f", OrderDetailsPanel.totalCost));
        }
    }

    public void addLabelToGrid(JLabel label, GridBagConstraints gbc) {
        Color fontColor = Color.decode("#9BA8FF");
        Font f = new Font("Calibri", Font.PLAIN, 17);

        label.setFont(f);
        label.setForeground(fontColor);
        furnitureGrid.add(label, gbc);
    }

    public void addBtnToGrid(JButton btn, GridBagConstraints gbc) {
        Color btnColor = Color.decode("#5271FF");

        btn.setBackground(btnColor);
        btn.setForeground(Color.WHITE);
        btn.addActionListener(this);

        furnitureGrid.add(btn, gbc);
    }

}
