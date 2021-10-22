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
public class Desk extends Furniture {

    protected int depth;
    protected int width;
    protected int numberOfDrawers;

    public Desk(String furnitureType, int furnitureId, String typeOfWood, int quantity, ImageIcon image, int depth, int width, int drawers) {
        super(furnitureType, furnitureId, typeOfWood, quantity, image);
        this.depth = depth;
        this.width = width;
        numberOfDrawers = drawers;
    }

    public int getDepth() {
        return depth;
    }

    public int getWidth() {
        return width;
    }

    public int getNumberOfDrawers() {
        return numberOfDrawers;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setNumberOfDrawers(int numberOfDrawers) {
        this.numberOfDrawers = numberOfDrawers;
    }

    @Override
    public String toString() {
        return "\n" + furnitureType + "\nItem ID : " + furnitureId + "\nWood Type: " + typeOfWood + "\nPrice: £" + String.format("%.2f", price) + "\nQuantity: " + quantity + "\nDepth: " + depth + "\nWidth: " + width + "\nDrawers: " + numberOfDrawers;
    }

}
