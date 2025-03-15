/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class EditSelectedInventory {
    
    public static void EditSelectedInventory(JTable inventoryTable, JTable supplierTable, JComboBox<String> comboItemCode, JTextField itemName,
            JComboBox<String> supplierCode, JTextField quantity, JSpinner unitPrice) {


        DefaultTableModel inventoryModel = (DefaultTableModel) inventoryTable.getModel();
        comboItemCode.removeAllItems();
        comboItemCode.addItem("Please Select");
        for (int i = 0; i < inventoryModel.getRowCount(); i++) {
            String itemID = inventoryModel.getValueAt(i, 0).toString();            
            comboItemCode.addItem(itemID);
        }
        
        DefaultTableModel supplierModel = (DefaultTableModel) supplierTable.getModel();
        supplierCode.removeAllItems();
        supplierCode.addItem("Please Select");
        for (int i = 0; i < supplierModel.getRowCount(); i++) {
            String supplierID = supplierModel.getValueAt(i, 0).toString();
            supplierCode.addItem(supplierID);
        }

        comboItemCode.addActionListener((ActionEvent e) -> {
            String selected_id = (String) comboItemCode.getSelectedItem();
            
            if (selected_id != null && !selected_id.equals("Please Select")) {
                for (int i = 0; i < inventoryModel.getRowCount(); i++) {
                    if (inventoryModel.getValueAt(i, 0).toString().equals(selected_id)) {
                        itemName.setText(inventoryModel.getValueAt(i, 1).toString());
                        
                        String selectedSupplier = inventoryModel.getValueAt(i, 2).toString();
                        if (selectedSupplier.equals("NULL")) {
                            supplierCode.setSelectedIndex(0);
                        } else {
                            supplierCode.setSelectedItem(selectedSupplier);
                        }                        
                        
                        quantity.setText(inventoryModel.getValueAt(i, 3).toString());
                        unitPrice.setValue(Double.valueOf(inventoryModel.getValueAt(i, 4).toString()));
                        
                    }
                }
            } else {
                itemName.setText("");
                supplierCode.setSelectedIndex(0);
                quantity.setText("");
                unitPrice.setValue(0);
            }
        });
    }
}
