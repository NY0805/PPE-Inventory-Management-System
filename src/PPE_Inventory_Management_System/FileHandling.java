/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author user
 */
public class FileHandling {
    
    // WRITE FILE
    public void WriteDataToFile(String filename, String[] headers, String[][] data) throws IOException {
        if (data.length == 0) {
            System.out.println("No data to write!");
            return;
        }
        File file = new File(filename);
        try(FileWriter writeFile = new FileWriter(filename, true)) {
            // insert the headers if the file is empty
            if (file.length() == 0) {
                writeFile.write(String.join("\t", headers) + "\n");
            }
            // insert data
            for(String[] row: data){
                writeFile.write(String.join("\t", row) + "\n");
            }
            System.out.println("Data saved to " + filename); 
        }
        
    }
    
    
    // READ FILE
    public static void readDataFromFile(String filename) throws IOException {
        BufferedReader readFile = new BufferedReader(new FileReader(filename));
        String line = readFile.readLine();
        while (line != null) {
            System.out.println(line);
        }
    }
    
    
    // make the data arranged neatly like table. It is private to prevent other class misuse it
    
}
