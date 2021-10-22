/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.gui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Amaan
 */
public class MainPanel1 extends JPanel {

    JLabel logoLabel;
    ImageIcon logo;

    public MainPanel1() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));  //places components horizontally

        //USER LOGIN LOGO
        logo = new ImageIcon("images/mainlogo.png");

        logoLabel = new JLabel(logo);

        this.add(logoLabel);

        UserPanel userPanel = new UserPanel();

        this.add(userPanel);

    }

}
