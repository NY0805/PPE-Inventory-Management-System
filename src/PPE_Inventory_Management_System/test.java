/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.util.ArrayList;
import java.util.Scanner;

//password: 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
//pull > edit > commit > pull > push

/**
 *
 * @author user
 */
public class test {
    public static void main (String[] args) {
        
        FileHandling file1 = new FileHandling();                
        Scanner input = new Scanner(System.in);
        ArrayList<String> Names = new ArrayList<>();
        ArrayList<String> Ages = new ArrayList<>();
        
        System.out.println("enter name: ");
            String name = input.nextLine();
            Names.add(name);
            System.out.println("enter age: ");
            int age = input.nextInt();
            input.nextLine();
            Ages.add(String.valueOf(age));
        
        String[] headers = {"Name", "Age"};
        String[][] data = new String[Names.size()][headers.length];
        for (int i=0; i<Names.size(); i++){
            data[i][0] = Names.get(i);
            data[i][1] = Ages.get(i);
        }
        
//        try {
//            file1.WriteDataToFile("test-data.txt", headers, data);
//        } catch (IOException e) {
//            System.out.println("Error writing file: " + e.getMessage());
//        }
            
    }
    
}
