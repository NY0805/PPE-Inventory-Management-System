/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import static PPE_Inventory_Management_System.FileHandling.ReadDataFromFile;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author user
 */
public class SupplierDisplay {
    public static void totalSupplier(JLabel totalSupplier, String filename) throws IOException {
        ArrayList<String[]> data = ReadDataFromFile(filename);
        int supplierCount = data.size();
        
        totalSupplier.setText("" + supplierCount);
    }
    
    public static void mostFrequentSupplier(JLabel frequentSupplier, JLabel frequentItem, String filename) throws IOException {
        ArrayList<String[]> data = ReadDataFromFile(filename);
        ArrayList<String> supplierCodeRecord = new ArrayList<>();
        ArrayList<String> itemRecord = new ArrayList<>();
        for (String[] row : data) {
            if (row[0].equals("Receive")) { 
                supplierCodeRecord.add(row[6]);
                itemRecord.add(row[5]);
            }
        }
        int highestCount = 0;
        String supplierCode = "";
        String item = "";
        
        for (int i = 0; i < supplierCodeRecord.size(); i++) {
            String supplier = supplierCodeRecord.get(i);
            int count = 0;
            for (String codePointer: supplierCodeRecord) {
                if (codePointer.equals(supplier)) {
                    count++;
                }
            }
            
            if (count > highestCount) {
                highestCount = count;
                supplierCode = supplier;
                item = itemRecord.get(i);
            }
        }
        
        frequentSupplier.setText(supplierCode);
        frequentItem.setText("(" + item + ")");
    }
    
    public static void activeInactive(JLabel activeSupplier, JLabel inactiveSupplier, String filename) throws IOException {
        ArrayList<String[]> data = ReadDataFromFile(filename);
        int active = 0;
        int inactive = 0;
        
        for (String[] row: data) {
            String ppe = row[5].trim();
            if (ppe.equals("NULL")) {
                inactive++;
            } else {
                active++;
            }
        }
        activeSupplier.setText("" + active);
        inactiveSupplier.setText("" + inactive);
    }
}
