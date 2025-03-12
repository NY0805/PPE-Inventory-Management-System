/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author User
 */
public class ReportChart {

    public DefaultCategoryDataset readCurrentStockData() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (BufferedReader br = new BufferedReader(new FileReader("ppe.txt"))) {
            String line;
            String itemName = "";
            int quantity = 0;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Item Name:")) {
                    itemName = line.split(":")[1].trim();
                } else if (line.startsWith("Quantity(boxes):")) {
                    quantity = Integer.parseInt(line.split(":")[1].trim());
                    dataset.addValue(quantity, "Stock Level", itemName);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    public void createCurrentStockBarChart(DefaultCategoryDataset dataset,
            JPanel pCurrentStockLevel) {
        JFreeChart barChart = ChartFactory.createBarChart("Current Inventory Stock Levels",
                "PPE Name", "Quantity (boxes)", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = new CustomBarRenderer(dataset);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setRenderer(renderer);
        renderer.setBarPainter(new StandardBarPainter());

        ChartPanel chartPanel = new ChartPanel(barChart);
        pCurrentStockLevel.removeAll();
        pCurrentStockLevel.add(chartPanel, BorderLayout.CENTER);
        pCurrentStockLevel.validate();
        pCurrentStockLevel.repaint();
    }

    class CustomBarRenderer extends BarRenderer {

        private DefaultCategoryDataset dataset;

        public CustomBarRenderer(DefaultCategoryDataset dataset) {
            this.dataset = dataset;
        }

        @Override
        public Paint getItemPaint(int row, int column) {
            Number value = dataset.getValue(row, column);
            int quantity = value.intValue();

            if (quantity <= 25) {
                return Color.red;
            } else if (quantity <= 50) {
                return Color.orange;
            } else if (quantity <= 100) {
                return Color.yellow;
            } else {
                return Color.green;
            }
        }
    }

    public DefaultPieDataset readPPEData(String fromDate, String toDate, boolean SupplierOrHospital) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);
        System.out.println(today);
        System.out.println(firstDay);

        if (fromDate == null || fromDate.isEmpty()) {
            fromDate = firstDay.format(DateTimeFormatter.ISO_DATE);
        }

        if (toDate == null || toDate.isEmpty()) {
            toDate = today.format(DateTimeFormatter.ISO_DATE);
        }
        
        if (SupplierOrHospital) {
            try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            String supplierId = "";
            String itemName = "";
            String receivedDate = "";
            int quantity = 0;
            boolean isReceive = false;

            Map<String, Set<String>> supplierItems = new HashMap<>();
            Map<String, Integer> supplierQuantity = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Transaction Type:")) {
                    isReceive = line.split(":")[1].trim().equalsIgnoreCase("Receive");
                } else if (line.startsWith("Received Date:")) {
                    receivedDate = line.split(":")[1].trim();
                } else if (line.startsWith("Supplier ID:")) {
                    supplierId = line.split(":")[1].trim();
                } else if (line.startsWith("Item Name:")) {
                    itemName = line.split(":")[1].trim();
                } else if (line.startsWith("Quantity(boxes):")) {
                    quantity = Integer.parseInt(line.split(":")[1].trim());

                    if (isReceive && receivedDate.compareTo(fromDate) >= 0 && receivedDate.compareTo(toDate) <= 0) {
                        supplierItems.putIfAbsent(supplierId, new HashSet<>());
                        supplierItems.get(supplierId).add(itemName);
                        supplierQuantity.put(supplierId, supplierQuantity.getOrDefault(supplierId, 0) + quantity);
                    }
                }
            }

            for (String id : supplierItems.keySet()) {
                String itemList = String.join(", ", supplierItems.get(id));
                int totalQuantity = supplierQuantity.getOrDefault(id, 0);
                String title = id + "(" + itemList + ")";

                dataset.setValue(title, totalQuantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataset;
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            String hospitalId = "";
            String itemName = "";
            String distributedDate = "";
            int quantity = 0;
            boolean isDistributed = false;

            Map<String, Set<String>> distributeItems = new HashMap<>();
            Map<String, Integer> distrubuteQuantity = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Transaction Type:")) {
                    isDistributed = line.split(":")[1].trim().equalsIgnoreCase("Distribute");
                } else if (line.startsWith("Distributed Date:")) {
                    distributedDate = line.split(":")[1].trim();
                } else if (line.startsWith("Hospital ID:")) {
                    hospitalId = line.split(":")[1].trim();
                } else if (line.startsWith("Item Name:")) {
                    itemName = line.split(":")[1].trim();
                } else if (line.startsWith("Quantity(boxes):")) {
                    quantity = Integer.parseInt(line.split(":")[1].trim());

                    if (isDistributed && distributedDate.compareTo(fromDate) >= 0 && distributedDate.compareTo(toDate) <= 0) {
                        distributeItems.putIfAbsent(hospitalId, new HashSet<>());
                        distributeItems.get(hospitalId).add(itemName);
                        distrubuteQuantity.put(hospitalId, distrubuteQuantity.getOrDefault(hospitalId, 0) + quantity);
                    }
                }
            }

            for (String id : distributeItems.keySet()) {
                String itemList = String.join(", ", distributeItems.get(id));
                int totalQuantity = distrubuteQuantity.getOrDefault(id, 0);
                String title = id + "(" + itemList + ")";

                dataset.setValue(title, totalQuantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataset;
        }

        
    }

    public void showPieChart(DefaultPieDataset dataset, JPanel pPieChart, boolean SupplierOrHospital) {
        String title = ""; 
        
        if (SupplierOrHospital) {
            title = "Supplier PPE Received";
        } else {
            title = "Hospital PPE Distributed";
        }
        
        JFreeChart pieChart = ChartFactory.createPieChart(title,
                dataset,
                true,
                true,
                false);

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} boxes"));

        ChartPanel chartPanel = new ChartPanel(pieChart);
        pPieChart.removeAll();
        pPieChart.add(chartPanel, BorderLayout.CENTER);
        pPieChart.validate();
    }
    

}
