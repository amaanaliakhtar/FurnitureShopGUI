/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furniture.customers;

/**
 *
 * @author Amaan
 */
public class CustomerTest {

    public static void main(String[] args) {
        Customer[] arr = new Customer[4];

        Customer c1 = new NonTrade(1, 1, "Peter", "Parker");
        arr[0] = c1;

        Customer c2 = new NonTrade(2, 6, "Steve", "Rogers");
        arr[1] = c2;

        Customer c3 = new Trade(3, 8, "Tony", "Stark", "Stark Industries");
        arr[2] = c3;

        Customer c4 = new Trade(4, 3, "Bruce", "Wayne", "Wayne Enterprise");
        arr[3] = c4;

        for (Customer c : arr) {
            System.out.println(c.toString());
        }

        //Customers printed from database
//        List<Customer> customers = findAllCustomers();
//
//        customers.forEach(c -> {
//            System.out.println(c.toString());
//        });
//
//        //Customer c = new NonTrade(1, 13, "B", "Wayne");
//        //insertIntoDatabase(c);
    }
}
