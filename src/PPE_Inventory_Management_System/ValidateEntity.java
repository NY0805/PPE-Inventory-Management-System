/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ValidateEntity {

    private String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

    public boolean validateID(String id, String filename) {
        FileHandling file = new FileHandling();

        try {
            ArrayList<String[]> data = file.ReadDataFromFile(filename);

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(id)) {
                    JOptionPane.showMessageDialog(null, "Duplicate item code!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unexpected validation error... Please try again!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error reading file: " + ex.getMessage());
            return false;
        }
    }

    public boolean validateName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public boolean validateId(String id) {
        return id != null && !id.trim().isEmpty();
    }

    public boolean validatePassword(String password) {
        return password != null && password.matches(regex);
    }

    public boolean validateUserType(String userType) {
        return userType != null && (userType.equals("Admin") || userType.equals("Staff"));
    }

    public boolean validateEmail(String email) {
        return email != null && (email.contains("@") && email.contains(".com"));
    }

    public boolean validateContact(String contact) {
//         return contact != null && contact.matches("\\d+") && contact.length() == 10;
        return contact != null && contact.matches("\\d{3}-\\d{7}") && contact.length() == 11;
    }

    public boolean validatePrice(String priceStr) {
        try {
            double price = Double.parseDouble(priceStr);
            return priceStr != null && price > 0 && priceStr.matches("^\\d+(\\.\\d{1,2})?$");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateQuantity(String quantityStr) {
        try {
            int quantity = Integer.parseInt(quantityStr);
            return quantity > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


