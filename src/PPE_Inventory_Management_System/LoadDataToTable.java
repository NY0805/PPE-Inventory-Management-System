/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class LoadDataToTable extends FileHandling {

    public void loadData(String filename, JTable tableNeeded) {
        DefaultTableModel table = (DefaultTableModel) tableNeeded.getModel();
        table.setRowCount(0);
        table.setColumnIdentifiers(new String[]{"User ID", "Name", "Password", "Contact No","User Type"});

        try {
            ArrayList<String[]> userData = ReadDataFromFile(filename);
            for (String[] user : userData) {
                table.addRow(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
