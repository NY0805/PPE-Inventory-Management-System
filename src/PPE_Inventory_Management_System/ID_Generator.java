/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

/**
 *
 * @author user
 */
public class ID_Generator {
    private static int supplier_id = 0;
    private static int hospital_id = 0;
    private static int staff_id = 0;
    
    public static String generate_id(String target) {
        switch (target) {
            case "supplier": {
                supplier_id++;
                return String.format("SP%03d", supplier_id);
            }
            case "hospital": {
                hospital_id++;
                return String.format("HP%03d", hospital_id);
            }
            case "staff": {
                staff_id++;
                return String.format("ST%03d", staff_id);
            }
            default: {return "invalid ID";}
        }
    }
}
