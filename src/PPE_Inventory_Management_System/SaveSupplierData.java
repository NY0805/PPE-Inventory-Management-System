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
    public static void saveSupplier(boolean isEdit, String currentSupplierId, JTextField tfAddSupplierName, JTextField tfAddSupplierContact, JTextField tfAddSupplierEmail, JTextArea taAddSupplierAddress, 
                                    JCheckBox checkFaceShield, JCheckBox checkGloves, JCheckBox checkGown, JCheckBox checkHeadCover, 
                                    JCheckBox checkMask, JCheckBox checkShoeCovers, JTable supplierList, JComboBox<String> dropdownMenu) throws IOException {
        
        String supplier_id, supplier_name, supplier_contact, supplier_email, supplier_address;
        ArrayList<String> selectedPPE = new ArrayList<>();   
        
        supplier_id = isEdit ? currentSupplierId : ID_Generator.generate_id("supplier");
        supplier_name = tfAddSupplierName.getText();
        supplier_contact = tfAddSupplierContact.getText();
        supplier_email = tfAddSupplierEmail.getText();
        supplier_address = taAddSupplierAddress.getText();

        if (checkFaceShield.isSelected()) {
            selectedPPE.add("Face Shield");
        }
        if (checkGloves.isSelected()) {
            selectedPPE.add("Gloves");
        }
        if (checkGown.isSelected()) {
            selectedPPE.add("Gown");
        }
        if (checkHeadCover.isSelected()) {
            selectedPPE.add("Head Cover");
        }
        if (checkMask.isSelected()) {
            selectedPPE.add("Mask");
        }
        if (checkShoeCovers.isSelected()) {
            selectedPPE.add("Shoe Covers");
        }
        String supplies_PPE = String.join(", ", selectedPPE);
        String[] headers = {"Supplier ID", "Supplier Name", "Supplier Contact", "Supplier email", "Supplier Address", "PPE Supplies"};
        String[] data = {supplier_id, supplier_name, supplier_contact, supplier_email, supplier_address, supplies_PPE};
 
        ValidateEntity validate = new ValidateEntity();
        FileHandling ppeFile = new FileHandling();
        
        if (supplier_name.isEmpty() || supplier_contact.isEmpty() || supplier_email.isEmpty() || 
            supplier_address.isEmpty() || supplies_PPE.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        
        if (!validate.validateName(supplier_name) || !validate.validateContact(supplier_contact) || !validate.validateEmail(supplier_email)) {
            JOptionPane.showMessageDialog(null, "Please enter valid information!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        ArrayList<String[]> ppeData = ppeFile.ReadDataFromFile("ppe.txt");
        
        for (String[] ppe: ppeData) {
            if (selectedPPE.contains(ppe[1]) && !ppe[2].equals("NULL")) {
//                JOptionPane.showMessageDialog(null, ppe[1] + " has been supplied by another supplier!", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
                
                if (ppe[1].equals("FaceShield")) {
                    checkFaceShield.setEnabled(false);
                }
                if (ppe[1].equals("Gloves")) {
                    checkGloves.setEnabled(false);
                }
                if (ppe[1].equals("Gown")) {
                    checkGown.setEnabled(false);
                }
                if (ppe[1].equals("Head Cover")) {
                    checkHeadCover.setEnabled(false);
                }
                if (ppe[1].equals("Mask")) {
                    checkMask.setEnabled(false);
                }
                if (ppe[1].equals("Shoe Covers")) {
                    checkFaceShield.setEnabled(false);
                }
                
            }
        }
        
        for (String[] ppe: ppeData) {
            if (selectedPPE.contains(ppe[1]) && ppe[2].equals("NULL")) {
                ppe[2] = supplier_id;
            }
        }
        
        BufferedWriter ppeWriter = new BufferedWriter(new FileWriter("ppe.txt"));
        ppeWriter.close();
        String[] ppeHeaders = {"Item Code", "Item Name", "Supplier Code", "Quantity(boxes)", "Price per box(RM)"};
        for (String[]ppe: ppeData) {
            ppeFile.WriteDataToFile("ppe.txt", ppeHeaders, ppe);
        }
        
//      =====================================================================================
        
        FileHandling supplierFile = new FileHandling();
        ArrayList<String[]> supplierData = supplierFile.ReadDataFromFile("suppliers.txt");
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

        checkFaceShield.setSelected(false);
        checkGloves.setSelected(false);
        checkGown.setSelected(false);
        checkHeadCover.setSelected(false);
        checkMask.setSelected(false);
        checkShoeCovers.setSelected(false);

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
