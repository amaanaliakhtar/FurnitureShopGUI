/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.addresses;

/**
 *
 * @author Amaan
 */
public class AddressTest {

    public static void main(String[] args) {
        Address[] arr = new Address[2];

        Address a1 = new Address("TS1 6EH", "123", "Number Street", "null", "Middlesbrough", "North Yorkshire");
        arr[0] = a1;

        Address a2 = new Address("TS5 6AH", "1", "Downing Street", "null", "London", "Greater London");
        arr[1] = a2;

        for (Address a : arr) {
            System.out.println(a.toString());
            System.out.println("-----------------");
        }
    }

}
