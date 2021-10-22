/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.items;

import javax.swing.ImageIcon;

/**
 *
 * @author Amaan
 */
public class Chair extends Furniture {

    protected boolean hasArmrests;

    public Chair(String furnitureType, int furnitureId, String typeOfWood, int quantity, ImageIcon image, boolean hasArmrests) {
        super(furnitureType, furnitureId, typeOfWood, quantity, image);
        this.hasArmrests = hasArmrests;
    }

    public boolean getHasArmrests() {
        return hasArmrests;
    }

    public void setHasArmrests(boolean hasArmrests) {
        this.hasArmrests = hasArmrests;
    }

    @Override
    public String toString() {
        return "\n" + furnitureType + "\nItem ID : " + furnitureId + "\nWood Type: " + typeOfWood + "\nPrice: £" + String.format("%.2f", price) + "\nQuantity: " + quantity + "\nArmrests: " + hasArmrests;
    }

}
