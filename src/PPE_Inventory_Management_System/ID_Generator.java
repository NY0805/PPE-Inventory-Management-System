/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.util.Random;

/**
 *
 * @author user
 */
public class ID_Generator {
    private static final Random random_num = new Random();
    
    public static String generate_id(String target) {
        int randomID = 1000 + random_num.nextInt(9000);
        
        switch (target) {
            case "supplier": {
                return String.format("SP%d", randomID);
            }
            case "hospital": {
                return String.format("HP%d", randomID);
            }
            case "staff": {
                return String.format("ST%d", randomID);
            }
            case "transaction": {
                return String.format("TR%d", randomID);
            }
            default:
                return "Invalid ID";
        }
    }
}
