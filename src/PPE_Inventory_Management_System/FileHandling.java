/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//password: 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
//pull > edit > commit > pull > push

/**
 *
 * @author user
 */
public class FileHandling {
    
    // WRITE FILE
    public void WriteDataToFile(String filename, String[] headers, String[] data) throws IOException {
        if (data.length == 0) {
            System.out.println("No data to write!");
            return;
        }

        BufferedWriter writeFile = new BufferedWriter(new FileWriter(filename, true));
        for (int i = 0; i < headers.length; i++) {
                writeFile.write(headers[i] + ": " + data[i] + "\n");            
        }
        writeFile.write("--------------------------------------------------\n");
        
        System.out.println("Data saved to " + filename);
        writeFile.close();

    }
        
    // READ FILE
    public static ArrayList<String[]> ReadDataFromFile(String filename) throws IOException {
        ArrayList<String[]> dataList = new ArrayList();
        ArrayList<String> record = new ArrayList<>();
        
        BufferedReader readFile = new BufferedReader(new FileReader(filename));
        String line;
        
        while ((line = readFile.readLine()) != null) {
            if (line.contains("----")) { // End of a record, add to list
                if (!record.isEmpty()) {
                    dataList.add(record.toArray(new String[0]));
                    record.clear();
                }
            } else {
                String[] parts = line.split(": ", 2);
                if (parts.length == 2) {
                    record.add(parts[1].trim()); // Store only values
                }
            }
        }
        if (!record.isEmpty()) { // Add last record if exists
            dataList.add(record.toArray(new String[0]));
        }
        readFile.close();
        return dataList;
        
        
    }
    
    
    // make the data arranged neatly like table. It is private to prevent other class misuse it
    
}
