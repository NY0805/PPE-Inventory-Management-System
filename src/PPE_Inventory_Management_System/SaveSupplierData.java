/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author user
 */
public class SaveSupplierData {
    public static void saveSupplier(boolean isEdit, String currentSupplierId, JTextField tfAddSupplierName, 
            JTextField tfAddSupplierContact, JTextField tfAddSupplierEmail, JTextArea taAddSupplierAddress, 
            JTextField tfppeSupplies, JTable supplierList, JComboBox<String> dropdownMenu) throws IOException {
        
        String supplier_id, supplier_name, supplier_contact, supplier_email, supplier_address, supplier_ppe;
        
        supplier_id = isEdit ? currentSupplierId : ID_Generator.generate_id("supplier");
        supplier_name = tfAddSupplierName.getText();
        supplier_contact = tfAddSupplierContact.getText();
        supplier_email = tfAddSupplierEmail.getText();
        supplier_address = taAddSupplierAddress.getText();
//        supplier_ppe = tfppeSupplies.getText();

        FileHandling supplierFile = new FileHandling();
        ArrayList<String[]> supplierData = supplierFile.ReadDataFromFile("suppliers.txt");

        if (isEdit) {
            for (String[] supplier: supplierData) {
                if (supplier[0].equals(currentSupplierId)) {
                    supplier_ppe = supplier[5];
                    break;
                }
            }
        }else{
            supplier_ppe = "NULL";
        }
        
        String[] headers = {"Supplier ID", "Supplier Name", "Supplier Contact", "Supplier email", "Supplier Address", "PPE Supplies"};
        String[] data = {supplier_id, supplier_name, supplier_contact, supplier_email, supplier_address, "NULL"};
 
        ValidateEntity validate = new ValidateEntity();
        
        if (supplier_name.isEmpty() || supplier_contact.isEmpty() || supplier_email.isEmpty() || 
            supplier_address.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        
        if (!validate.validateName(supplier_name) || !validate.validateContact(supplier_contact) || !validate.validateEmail(supplier_email)) {
            JOptionPane.showMessageDialog(null, "Please enter valid information!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
                
        // update
        if (isEdit) {
            for (int i = 0; i < supplierData.size(); i++) {
                if (supplierData.get(i)[0].equals(currentSupplierId)) {
                    supplierData.set(i, data);
                    break;
                }
            }
        }else{
            // add
            supplierData.add(data);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("suppliers.txt"));
        writer.close();
        for (String[] supplier: supplierData) {
            supplierFile.WriteDataToFile("suppliers.txt", headers, supplier);

        }

        JOptionPane.showMessageDialog(null, isEdit ? "Supplier updated successfully!" : "Supplier added successfully!");
        
        dropdownMenu.setSelectedIndex(0);        
        tfAddSupplierName.setText("");
        tfAddSupplierContact.setText("");
        tfAddSupplierEmail.setText("");
        taAddSupplierAddress.setText("");
        tfppeSupplies.setText("");
        


        DefaultTableModel model = new DefaultTableModel();
        supplierList.setModel(model);
        model.setColumnIdentifiers(headers);
        model.setRowCount(0);
        for (String[] rowData: supplierData) {
            if (rowData.length == 6) {
                model.addRow(rowData);
            }else {
                System.err.println("skipping record: " + Arrays.toString(rowData));
            }            
        } 
    }
}
