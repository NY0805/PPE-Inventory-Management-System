/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class LoadData extends FileHandling {
    
    public void loadDataToTable(String filename, JTable table) throws IOException {

        ArrayList<String[]> data = ReadDataFromFile(filename);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int columnCount = model.getColumnCount();
        model.setRowCount(0);

        for (String[] row : data) {
            if (row.length == columnCount) {
                model.addRow(row);
            }
        }
    }
}
