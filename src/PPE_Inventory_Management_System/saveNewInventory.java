/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.Container;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class saveNewInventory {
//    private String supplierCode;
//    private String price;
//    private String quantity;
//
//    private JTextField tfPPECode;
//    private JTextField tfPPEName;
//    private JTextField tfSupplierCode;
//    private JTextField tfPPEQuantity;
//    private JSpinner spinnerPrice;
//    private ValidateEntity validator = new ValidateEntity();

    public static void saveNewInventory(boolean isEdit, JTextField tfItemCode, JComboBox<String> selectedItemCode, JTextField tfItemName, 
            JTextField tfSupplierCode, JTextField tfQuantity, JSpinner spinnerPrice, JTable inventoryList) throws IOException {
        
        String itemCode, itemName, supplierCode, quantity, unitPrice;
        
        itemCode = isEdit ? (String)selectedItemCode.getSelectedItem() : tfItemCode.getText();
        itemName = tfItemName.getText();
        supplierCode = tfSupplierCode.getText();
        quantity = tfQuantity.getText();
        Number value = (Number) spinnerPrice.getValue();
        double price = value.doubleValue();
        unitPrice = String.format("%.2f", price);
        
        String[] headers = {"Item Code", "Item Name", "Supplier Code", "Quantity", "Price per box(RM)"};
        String[] data = {itemCode, itemName, supplierCode, quantity, unitPrice};
 
        ValidateEntity validate = new ValidateEntity();
        FileHandling ppeFile = new FileHandling();
        
        if ((!isEdit && tfItemCode.getText().trim().isEmpty()) ||
            (isEdit && (selectedItemCode.getSelectedItem().toString().equals("Please Select"))) ||
            itemName.trim().isEmpty() || supplierCode.trim().isEmpty() || 
            quantity.trim().isEmpty() || unitPrice.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        
        if (!validate.validateQuantity(quantity) || !validate.validatePrice(unitPrice)) {
            JOptionPane.showMessageDialog(null, "Please enter valid information!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        ArrayList<String[]> ppeData = ppeFile.ReadDataFromFile("ppe.txt");
        // update
        if (isEdit) {
            for (int i = 0; i < ppeData.size(); i++) {
                if (ppeData.get(i)[0].equals(selectedItemCode.getSelectedItem())) {
                    ppeData.set(i, data);
                    break;
                }
            }            
            selectedItemCode.setSelectedIndex(0);
            tfSupplierCode.setText("");
            tfQuantity.setText("");
        }else{
            // add
            ppeData.add(data);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("ppe.txt"));
        writer.close();
        for (String[] ppe: ppeData) {
            ppeFile.WriteDataToFile("ppe.txt", headers, ppe);

        }

        JOptionPane.showMessageDialog(null, isEdit ? "PPE updated successfully!" : "PPE added successfully!");
        
        tfItemCode.setText("");
        tfItemName.setText("");        
        spinnerPrice.setValue(0.00);
        
        DefaultTableModel model = (DefaultTableModel) inventoryList.getModel();
        model.setRowCount(0);
        for (String[] rowData: ppeData) {
            if (rowData.length == 5) {
                model.addRow(rowData);
            }else {
                System.err.println("skipping record: " + Arrays.toString(rowData));
            }            
        } 
        
    }

        
    public void addCheckbox(JPanel panel, JTextField name) {
        JCheckBox newCheckBox = new JCheckBox(name.getText());
        newCheckBox.setText(name.getText());
        panel.add(newCheckBox);
        panel.revalidate();
        panel.repaint();
        Container parent = panel.getParent();
        parent.revalidate();
        parent.repaint();
        System.out.println("checkbox added");
    }
}
