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
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
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

    public DefaultPieDataset readSupplierPPEData(String fromDate, String toDate) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try (BufferedReader br = new BufferedReader(new FileReader("transaction.txt"))) {
            String line;
            String supplierId = "";
            String itemName = "";
            String receivedDate = "";
            int quantity = 0;
            boolean isReceive = false;

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
                        String item = supplierId + " (" + itemName + ")";

                        Number totalQuantity = dataset.getValue(item);
                        int newQuantity = (totalQuantity == null) ? quantity : totalQuantity.intValue() + quantity;
                        dataset.setValue(item, newQuantity);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public void showSupplierPieChart() {

    }

}
