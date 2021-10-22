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
public class Trade extends Customer {

    protected String companyName;

    public Trade(int customerId, int titleId, String firstName, String lastName, String companyName) {
        super(customerId, titleId, firstName, lastName);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String toString() {
        return "\n" + firstName + " " + lastName + "\nCustomer ID: " + customerId + "\nTitle ID: " + titleId + "\nCompany Name: " + companyName;
    }

}
