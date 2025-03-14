/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import static PPE_Inventory_Management_System.FileHandling.ReadDataFromFile;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author User
 */
public class DataOverview {

    public static void totalData(JLabel total, String filename, String condition, boolean PPE, boolean totalPPE) throws IOException {
        ArrayList<String[]> data = ReadDataFromFile(filename);
        int totalDataCount = data.size();

        if (PPE) {
            LocalDate today = LocalDate.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            int totalQuantity = 0;
            for (String[] row : data) {
                if (row[0].equals(condition) && LocalDate.parse(row[2], format).equals(today)) {
                    totalQuantity += Integer.parseInt(row[7]);
                }
            }
            
            total.setText("" + totalQuantity);
        } else if (totalPPE) {
            int totalPPEInStock = 0;
            for (String[] row : data) {
                totalPPEInStock += Integer.parseInt(row[3]);
            }
            
            total.setText("" + totalPPEInStock);
        } else {
            total.setText("" + totalDataCount);
        }
    }

    public static void topSupplierOrHospital(JLabel frequentSource, JLabel frequentItem, String filename, String condition) throws IOException {
        ArrayList<String[]> data = ReadDataFromFile(filename);
        ArrayList<String> sourceCodeRecord = new ArrayList<>();
        ArrayList<String> itemRecord = new ArrayList<>();

        for (String[] row : data) {
            if (row[0].equals(condition)) {
                sourceCodeRecord.add(row[6]);
                itemRecord.add(row[5]);
            }
        }

        int highestCount = 0;
        String sourceCode = "";
        String item = "";

        for (int i = 0; i < sourceCodeRecord.size(); i++) {
            String source = sourceCodeRecord.get(i);
            int count = 0;
            for (String codePointer : sourceCodeRecord) {
                if (codePointer.equals(source)) {
                    count++;
                }
            }

            if (count > highestCount) {
                highestCount = count;
                sourceCode = source;
                item = itemRecord.get(i);
            }
        }

        frequentSource.setText(sourceCode);
        frequentItem.setText("(" + item + ")");
    }

    public static void activeInactive(JLabel activeSupplier, JLabel inactiveSupplier, String filename) throws IOException {
        ArrayList<String[]> data = ReadDataFromFile(filename);
        int active = 0;
        int inactive = 0;

        for (String[] row : data) {
            String ppe = row[5].trim();
            if (ppe.equals("NULL")) {
                inactive++;
            } else {
                active++;
            }
        }
        activeSupplier.setText("" + active);
        inactiveSupplier.setText("" + inactive);
    }

}
