/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import static PPE_Inventory_Management_System.FileHandling.ReadDataFromFile;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author user
 */
public class HospitalDisplay {
    public static void totalHospital(JLabel totalHospital, String filename) throws IOException {
        ArrayList<String[]> data = ReadDataFromFile(filename);
        int hospitalCount = data.size();
        
        totalHospital.setText("" + hospitalCount);
    }
    
    public static void topDistributedHospital(JLabel topHospital, JLabel topDistributedItem ,String filename) throws IOException {
        ArrayList<String[]> data = ReadDataFromFile(filename);
        ArrayList<String> hospitalCodeRecord = new ArrayList<>();
        ArrayList<String> itemRecord = new ArrayList<>();
        for (String[] row : data) {
            if (row[0].equals("Distribute")) { 
                hospitalCodeRecord.add(row[6]);
                itemRecord.add(row[5]);
            }
        }
        int highestCount = 0;
        String hospitalCode = "";
        String item = "";
        
        for (int i = 0; i < hospitalCodeRecord.size(); i++) {
            String hospital = hospitalCodeRecord.get(i);
            int count = 0;
            for (String codePointer: hospitalCodeRecord) {
                if (codePointer.equals(hospital)) {
                    count++;
                }
            }
            
            if (count > highestCount) {
                highestCount = count;
                hospitalCode = hospital;
                item = itemRecord.get(i);
            }
        }
        
        topHospital.setText(hospitalCode);
        topDistributedItem.setText("(" + item + ")");
    }
}
