/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furnituremain;

import furniture.gui.MainPanel1;
import furniture.items.Chair;
import furniture.items.Desk;
import furniture.items.Furniture;
import furniture.items.Table;
import javax.swing.JFrame;

/**
 *
 * @author Amaan
 */
public class FurnitureApp {

    /**
     * @param args the command line arguments
     */
    /*

TODO:
- Insert customer addresses to DB
- Validations
- Add remaining fields to form
- link customers to addresses in db
- Empty constructors for classes
     */
    static JFrame frame1;

    public static void main(String[] args) {
        // TODO code application logic here
        frame1 = createFrame();

        MainPanel1 panel = new MainPanel1();

        frame1.add(panel);
        frame1.setVisible(true);
    }

    public static JFrame createFrame() {
        JFrame frame = new JFrame();

        frame.setSize(1200, 685);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        return frame;
    }

    public static void calcPrice(Furniture item) {

        double totalPrice;
        int units;
        double pricePerUnit;

        String itemType = item.getFurnitureType();

        if (item.getTypeOfWood().equals("Oak")) {
            pricePerUnit = 0.04;
        }
        else {
            pricePerUnit = 0.03;
        }

        switch (itemType) {
            case "Chair":  //Calc Price for Chair

                units = 1625;

                if (((Chair) item).getHasArmrests()) {  //item casted to chair to access subclass method
                    units += 250;
                }

                totalPrice = units * pricePerUnit;

                item.setPrice(totalPrice);

                break;

            case "Table":  // Calc Price for Table

                Table table = (Table) item;
                int basePrice;

                int diameter = table.getDiameter();  //TODO validate diameter
                units = diameter * diameter;

                if (table.getBaseMaterial().equals("Wooden")) {
                    basePrice = 45;
                }
                else {
                    basePrice = 35;
                }

                totalPrice = basePrice + (units * pricePerUnit);

                item.setPrice(totalPrice);

                break;

            case "Desk":

                Desk desk = (Desk) item;

                int width = desk.getWidth();
                int depth = desk.getDepth();
                int numberOfDrawers = desk.getNumberOfDrawers();

                totalPrice = (width * depth * pricePerUnit) + (numberOfDrawers * 8.5);

                item.setPrice(totalPrice);

                break;

            default:
                break;
        }
    }

}
