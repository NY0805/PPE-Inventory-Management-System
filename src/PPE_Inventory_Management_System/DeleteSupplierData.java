/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class DeleteSupplierData {
    public void DeleteSupplier (String filename, String supplier_id, JComboBox<String> combobox, 
                           JTextField name, JTextField contact, JTextField email, 
                           JTextArea address, JTextField ppe, JTable table) throws IOException {
        
        FileHandling supplierFile = new FileHandling();
        ArrayList<String[]> suppliers = supplierFile.ReadDataFromFile(filename);
        ArrayList<String[]> updatedSuppliers = new ArrayList<>();
        
        boolean found = false;
        
        for (String[] supplier: suppliers) {
            if (!supplier[0].equals(supplier_id)) {
                updatedSuppliers.add(supplier);
            }else{
                found = true;
            }
        }
        
        if (found) {
            name.setText("");
            contact.setText("");
            email.setText("");
            address.setText("");
            ppe.setText("");

            String[] headers = {"Supplier ID", "Supplier Name", "Supplier Contact", "Supplier Email", "Supplier Address", "PPE Supplies"};
            BufferedWriter writeFile = new BufferedWriter(new FileWriter(filename, false));
            
            for (String[] supplier : updatedSuppliers) {
                for (int i = 0; i < headers.length; i++) {
                    writeFile.write(headers[i] + ": " + supplier[i] + "\n");
                }
                writeFile.write("--------------------------------------------------\n");
            }
            writeFile.close();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(supplier_id)) {
                    model.removeRow(i);
                    break;
                }
            }
            
            combobox.removeAllItems();
            combobox.addItem("Please select");
            for (String[] supplier : updatedSuppliers) {
                combobox.addItem(supplier[0]); // Add supplier ID
            }       

        } else {
            System.out.println("supplier id not found.");
        } 
    }
    
}