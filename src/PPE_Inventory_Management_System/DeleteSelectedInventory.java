/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class DeleteSelectedInventory {
    public static void deleteSelectedInventory(String filename, JComboBox<String> selectedItemCode, 
            JTextField tfItemName, JComboBox<String> selectedSupplierCode, 
            JTextField tfQuantity, JSpinner spinnerPrice, JTable table) throws IOException {
        
        FileHandling inventoryFile = new FileHandling();
        ArrayList<String[]> inventories = inventoryFile.ReadDataFromFile(filename);
        ArrayList<String[]> updatedInventory = new ArrayList<>();
        
        boolean found = false;
        
        for (String[] inventory: inventories) {
            if (!inventory[0].equals(selectedItemCode.getSelectedItem().toString())) {
                updatedInventory.add(inventory);
            }else{
                found = true;
            
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete Item " + selectedItemCode.getSelectedItem() + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Supplier " + selectedItemCode.getSelectedItem() + " deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        
        if (found) {
            selectedItemCode.setSelectedIndex(0);
            tfItemName.setText("");
            selectedSupplierCode.setSelectedIndex(0);
            tfQuantity.setText("");
            spinnerPrice.setValue(0);

            String[] headers = {"Item Code", "Item Name", "Supplier Code", "Quantity(boxes)", "Price per box(RM)"};
            BufferedWriter writeFile = new BufferedWriter(new FileWriter(filename, false));
            
            for (String[] inventory : updatedInventory) {
                for (int i = 0; i < headers.length; i++) {
                    writeFile.write(headers[i] + ": " + inventory[i] + "\n");
                }
                writeFile.write("--------------------------------------------------\n");
            }
            writeFile.close();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (String[] inventory : updatedInventory) {
                if (inventory.length == headers.length) { 
                    model.addRow(inventory);
                } else {
                    System.err.println("Skipping invalid record: " + Arrays.toString(inventory));
                }
            }

            selectedItemCode.removeAllItems();
            selectedSupplierCode.removeAllItems();
            selectedItemCode.addItem("Please select");
            selectedSupplierCode.addItem("Please select");
            for (String[] inventory : updatedInventory) {
                selectedItemCode.addItem(inventory[0]);
                selectedSupplierCode.addItem(inventory[2]);
            }       

        } else {
            JOptionPane.showMessageDialog(null, "Please select an item to delete.", "Warning", JOptionPane.WARNING_MESSAGE);           
        } 
    }
    
}
