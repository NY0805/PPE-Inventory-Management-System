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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class SaveHospitalData {
    public static void saveHospital(boolean isEdit, String currentHospitalId, JTextField tfAddHospitalName,
            JTextField tfAddHospitalContact, JTextField tfAddHospitalEmail,
            JTextArea taAddHospitalAddress, JTable hospitalList, JComboBox<String> dropdownMenu) throws IOException {
        
        String hospital_id, hospital_name, hospital_contact, hospital_email, hospital_address;
//        ArrayList<String> selectedPPE = new ArrayList<>();  
        
        hospital_id = isEdit ? currentHospitalId : ID_Generator.generate_id("hospital");
        hospital_name = tfAddHospitalName.getText();
        hospital_contact = tfAddHospitalContact.getText();
        hospital_email = tfAddHospitalEmail.getText();
        hospital_address = taAddHospitalAddress.getText();

        String[] headers = {"Hospital ID", "Hospital Name", "Hospital Contact", "Hospital email", "Hospital Address"};
        String[] data = {hospital_id, hospital_name, hospital_contact, hospital_email, hospital_address};
 
        ValidateEntity validate = new ValidateEntity();
        FileHandling hospitalFile = new FileHandling();
        
        if (hospital_name.isEmpty() || hospital_contact.isEmpty() || hospital_email.isEmpty() || 
            hospital_address.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        
        if (!validate.validateName(hospital_name) || !validate.validateContact(hospital_contact) || !validate.validateEmail(hospital_email)) {
            JOptionPane.showMessageDialog(null, "Please enter valid information!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

         
        ArrayList<String[]> hospitalData = hospitalFile.ReadDataFromFile("hospitals.txt");
        if (isEdit) {
            for (int i = 0; i < hospitalData.size(); i++) {
                if (hospitalData.get(i)[0].equals(currentHospitalId)) {
                    hospitalData.set(i, data);
                    break;
                }
            }
        }else{
            hospitalData.add(data);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("hospitals.txt"));
        writer.close();
        for (String[] supplier: hospitalData) {
            hospitalFile.WriteDataToFile("hospitals.txt", headers, supplier);

        }

        JOptionPane.showMessageDialog(null, isEdit ? "Hospital updated successfully!" : "Hospital added successfully!");
        dropdownMenu.setSelectedIndex(0);
        
        tfAddHospitalName.setText("");
        tfAddHospitalContact.setText("");
        tfAddHospitalEmail.setText("");
        taAddHospitalAddress.setText("");

        DefaultTableModel model = new DefaultTableModel();
        hospitalList.setModel(model);
        model.setColumnIdentifiers(headers);
        model.setRowCount(0);
        for (String[] rowData: hospitalData) {
            if (rowData.length == 5) {
                System.out.println(Arrays.toString(rowData));
                model.addRow(rowData);
            }else {
                System.err.println("skipping record: " + Arrays.toString(rowData));
            }            
        } 
    }
}
