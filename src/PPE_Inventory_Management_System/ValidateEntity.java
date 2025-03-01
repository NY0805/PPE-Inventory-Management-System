/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import javax.swing.JLabel;

/**
 *
 * @author User
 */

public abstract class ValidateEntity {
    
    String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

    public boolean validateName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public boolean validateId(String id) {
        return id != null && !id.trim().isEmpty();
    }
//    
//    public boolean validatePasswordWhileKeyup(String password, boolean length, 
//            boolean upper, boolean lower, boolean number, boolean specialChar) {
//        if (password.matches(".*[A-Z].*")) {
//            return upper = true;
//        } else if (password.matches(".*[a-z].*")) {
//            return lower = true;
//        } else if (password.matches(".*\\d.*")) {
//            return number = true;
//        } else if (password.matches(".*[@#$%^&+=!].*")) {
//            return specialChar = true;
//        }
//    }

    public boolean validatePassword(String password) {
        return password != null && password.matches(regex);
    }

    public boolean validateUserType(String userType) {
        return userType != null && (userType.equals("Admin") || userType.equals("User"));
    }
    
    public boolean validateEmail(String email) {
         return email != null && (email.contains("@") && email.contains(".com"));
    }
    
    public boolean validateContact(String contact) {
         return contact != null && contact.matches("\\d+") && contact.length() == 10;
    }

}
