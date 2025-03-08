/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class DeleteHospitalData {
    public void DeleteHospital (String filename, String hospital_id, JComboBox<String> combobox, 
                           JTextField name, JTextField contact, JTextField email, 
                           JTextArea address, JCheckBox[] checkBoxItems, JTable table) throws IOException {
        
        FileHandling hospitalFile = new FileHandling();
        ArrayList<String[]> hospitals = hospitalFile.ReadDataFromFile(filename);
        ArrayList<String[]> updatedHospitals = new ArrayList<>();
        
        boolean found = false;
        
        for (String[] hospital: hospitals) {
            if (!hospital[0].equals(hospital_id)) {
//                updatedHospitals.add(hospital);
            }else{
                found = true;
            }
        }
        
        if (found) {
            name.setText("");
            contact.setText("");
            email.setText("");
            address.setText("");

            for (JCheckBox checkBox : checkBoxItems) {
                checkBox.setSelected(false);
            }
            
            String[] headers = {"Hospital ID", "Hospital Name", "Hospital Contact", "Hospital Email", "Hospital Address", "PPE Supplies"};
            BufferedWriter writeFile = new BufferedWriter(new FileWriter(filename, false));
            
            for (String[] hospital : updatedHospitals) {
                for (int i = 0; i < headers.length; i++) {
                    writeFile.write(headers[i] + ": " + hospital[i] + "\n");
                }
                writeFile.write("--------------------------------------------------\n");
            }
            writeFile.close();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(hospital_id)) {
                    model.removeRow(i);
                    break;
                }
            }
            
            combobox.removeAllItems();
            combobox.addItem("Please select");
            for (String[] hospital : updatedHospitals) {
                combobox.addItem(hospital[0]); // Add supplier ID
            }       

        } else {
            System.out.println("Hospital id not found.");
        } 
    }
}
