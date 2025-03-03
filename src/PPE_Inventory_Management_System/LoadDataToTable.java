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
        table.setColumnIdentifiers(new String[]{"User ID", "Name", "Password", "User Type"});

        try {
            ArrayList<String[]> userData = ReadDataFromFile(filename);
            for (String[] user : userData) {
                table.addRow(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<String[]> ReadDataFromFile(String filename) throws IOException {
    ArrayList<String[]> dataList = new ArrayList<>();
    ArrayList<String> record = new ArrayList<>();
    
    BufferedReader readFile = new BufferedReader(new FileReader(filename));
    String line;
    
    while ((line = readFile.readLine()) != null) {
        if (line.contains("----")) { // 记录结束，存入 dataList
            if (!record.isEmpty()) {
                dataList.add(record.toArray(new String[0]));
                record.clear();
            }
        } else {
            String[] parts = line.split(": ", 2);
            if (parts.length == 2) {
                record.add(parts[1].trim()); // 只存储值
            }
        }
    }
    if (!record.isEmpty()) { // 处理最后一条记录
        dataList.add(record.toArray(new String[0]));
    }
    readFile.close();
    return dataList;
}
}
