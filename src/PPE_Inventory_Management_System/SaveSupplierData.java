/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author user
 */
public class SaveSupplierData {
    public static void saveSupplier(JTextField tfAddSupplierName, JTextField tfAddSupplierContact, JTextField tfAddSupplierEmail, JTextField tfAddSupplierAddress, 
                                    JCheckBox checkFaceShield, JCheckBox checkGloves, JCheckBox checkGown, JCheckBox checkHeadCover, 
                                    JCheckBox checkMask, JCheckBox checkShoeCovers) throws IOException {
        
        String supplier_id, supplier_name, supplier_contact, supplier_email, supplier_address;
        ArrayList<String> selectedPPE = new ArrayList<>();   
        
        supplier_id = ID_Generator.generate_id("supplier");
        supplier_name = tfAddSupplierName.getText();
        supplier_contact = tfAddSupplierContact.getText();
        supplier_email = tfAddSupplierEmail.getText();
        supplier_address = tfAddSupplierAddress.getText();

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
 
        
        FileHandling supplierFile = new FileHandling();
        if (!supplier_id.isEmpty() && !supplier_name.isEmpty() && !supplier_contact.isEmpty() && !supplier_email.isEmpty() && !supplier_address.isEmpty() && !supplies_PPE.isEmpty()){
            supplierFile.WriteDataToFile("suppliers.txt", headers, data);
            JOptionPane.showMessageDialog(null, "Supplier saved successfully!");
            
            tfAddSupplierName.setText("");
            tfAddSupplierContact.setText("");
            tfAddSupplierEmail.setText("");
            tfAddSupplierAddress.setText("");

            checkFaceShield.setSelected(false);
            checkGloves.setSelected(false);
            checkGown.setSelected(false);
            checkHeadCover.setSelected(false);
            checkMask.setSelected(false);
            checkShoeCovers.setSelected(false);
            
                        
        } else{
            JOptionPane.showMessageDialog(null, "Please fill out all fields !");
        }
        
        
    }
    
    
}
