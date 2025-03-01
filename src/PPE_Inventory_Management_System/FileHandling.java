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
//        File file = new File(filename);
        BufferedWriter writeFile = new BufferedWriter(new FileWriter(filename, true));
//        if (file.length() == 0) {
//            writeFile.write(String.join("\t\t", headers) + "\n");
//        }
        
        for (int i = 0; i < headers.length; i++) {
                writeFile.write(headers[i] + ": " + data[i] + "\n");
            
        }
        writeFile.write("--------------------------------------------------\n");
        
        System.out.println("Data saved to " + filename);
        writeFile.close();

    }
    
    
    // READ FILE
    public void ReadDataFromFile(String filename) throws IOException {
        BufferedReader readFile = new BufferedReader(new FileReader(filename));
//        String line = readFile.readLine();
        String line;
        
        while ((line=readFile.readLine()) != null) {
            System.out.println(line);
        }
        readFile.close();
    }
    
    
    // make the data arranged neatly like table. It is private to prevent other class misuse it
    
}
