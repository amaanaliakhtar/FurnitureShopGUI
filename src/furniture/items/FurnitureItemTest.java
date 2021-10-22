/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.items;

import static furnituremain.FurnitureApp.calcPrice;

/**
 *
 * @author Amaan
 */
public class FurnitureItemTest {

    public static void main(String[] args) {
        Furniture[] arr = new Furniture[6];

        Furniture chair1 = new Chair("Chair", 1, "Oak", 3, null, true);  //expected price = 75
        arr[0] = chair1;

        Furniture chair2 = new Chair("Chair", 2, "Walnut", 1, null, false); //expected price = 48.75
        arr[1] = chair2;

        Furniture table1 = new Table("Table", 3, "Walnut", 2, null, 50, "Chrome");  //expected price = 110
        arr[2] = table1;

        Furniture table2 = new Table("Table", 4, "Oak", 2, null, 60, "Wooden");  //expected price = 189
        arr[3] = table2;

        Furniture desk1 = new Desk("Desk", 5, "Oak", 1, null, 50, 120, 3);  //expected price = 265.5
        arr[4] = desk1;

        Furniture desk2 = new Desk("Desk", 6, "Walnut", 1, null, 70, 100, 5);  //expected price = 252.5
        arr[5] = desk2;

        for (Furniture f : arr) {  //Test calcPrice() and toString()
            calcPrice(f);
            System.out.println(f.toString());
        }
    }

}
