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
public class Table extends Furniture {

    protected int diameter;
    protected String baseMaterial;

    public Table(String furnitureType, int furnitureId, String typeOfWood, int quantity, ImageIcon image, int diameter, String baseMaterial) {
        super(furnitureType, furnitureId, typeOfWood, quantity, image);
        this.diameter = diameter;
        this.baseMaterial = baseMaterial;
    }

    public int getDiameter() {
        return diameter;
    }

    public String getBaseMaterial() {
        return baseMaterial;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public void setBaseMaterial(String baseMaterial) {
        this.baseMaterial = baseMaterial;
    }

    @Override
    public String toString() {
        return "\n" + furnitureType + "\nItem ID : " + furnitureId + "\nWood Type: " + typeOfWood + "\nPrice: £" + String.format("%.2f", price) + "\nQuantity: " + quantity + "\nDiameter: " + diameter + "\nBase Material: " + baseMaterial;
    }

}
