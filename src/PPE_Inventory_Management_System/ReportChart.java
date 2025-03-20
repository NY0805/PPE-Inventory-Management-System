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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
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

    public DefaultCategoryDataset readCurrentStockData(boolean lowStock, JTextArea noti) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<String> lowStockPPE = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("ppe.txt"))) {
            String line;
            String itemName = "";
            String itemCode = "";
            int quantity = 0;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Item Name:")) {
                    itemName = line.split(":")[1].trim();
                } else if (line.startsWith("Item Code:")) {
                    itemCode = line.split(":")[1].trim();
                } else if (line.startsWith("Quantity(boxes):")) {
                    quantity = Integer.parseInt(line.split(":")[1].trim());
                    dataset.addValue(quantity, "Stock Level", itemName);

                    if (quantity <= 25) {
                        lowStockPPE.add(itemCode + " (" + itemName + ")");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lowStock) {
            if (lowStockPPE.isEmpty()) {
                noti.setBackground(new Color(0xCCFFCC));
                noti.setText("All item are sufficiently stocked. No immediate restoking is needed.");
            } else {
                noti.setBackground(new Color(0xFFCCCC));
                noti.setText("The following item is/are running critically low: "
                        + String.join(" ,", lowStockPPE) + ". Please restock immediately!");
            }
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
                int quantity;
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
        String title;

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

    public String selectCode(String code, JComboBox<String> combobox, JTable table) {

        String userSelectedCode = (String) combobox.getSelectedItem();

        combobox.removeAllItems();
        String firstItemCode = null;

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowcount = model.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            String itemCode = model.getValueAt(i, 0).toString();
            combobox.addItem(itemCode);

            if (i == 0) {
                firstItemCode = itemCode;
            }
        }

        if (userSelectedCode != null && containsItem(combobox, userSelectedCode)) {
            combobox.setSelectedItem(userSelectedCode);
            return userSelectedCode;
        }

        combobox.setSelectedItem(firstItemCode);
        return firstItemCode;
    }

    private boolean containsItem(JComboBox<String> combobox, String item) {
        for (int i = 0; i < combobox.getItemCount(); i++) {
            if (combobox.getItemAt(i).equals(item)) {
                return true;
            }
        }
        return false;
    }

    public DefaultCategoryDataset readTransactionData(String code, String toDate,
            String fromDate, boolean item, boolean supplier, boolean hospital) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);

        if (fromDate == null || fromDate.isEmpty()) {
            fromDate = firstDay.format(DateTimeFormatter.ISO_DATE);
        }

        if (toDate == null || toDate.isEmpty()) {
            toDate = today.format(DateTimeFormatter.ISO_DATE);
        }

        Map<String, Integer> receivedData = new TreeMap<>();
        Map<String, Integer> distributedData = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            String itemCode = "";
            String itemName = "";
            String transactionDate = "";
            String transactionType = "";
            boolean isReceive = false;
            boolean isDistribute = false;
            int quantity = 0;

            while ((line = br.readLine()) != null) {
                if (item) {
                    if (line.startsWith("Transaction Type:")) {
                        transactionType = line.split(":")[1].trim();
                    } else if (line.startsWith("Received Date:") || line.startsWith("Distributed Date:")) {
                        transactionDate = line.split(":")[1].trim();
                    } else if (line.startsWith("Item Code:")) {
                        itemCode = line.split(":")[1].trim();
                    } else if (line.startsWith("Quantity(boxes):")) {
                        quantity = Integer.parseInt(line.split(":")[1].trim());

                        if (!itemCode.equals(code)) {
                            continue;
                        }

                        if (transactionDate.compareTo(fromDate) >= 0 && transactionDate.compareTo(toDate) <= 0) {
                            if (transactionType.equals("Receive")) {
                                receivedData.put(transactionDate, receivedData.getOrDefault(transactionDate, 0) + quantity);
                            } else if (transactionType.equals("Distribute")) {
                                distributedData.put(transactionDate, distributedData.getOrDefault(transactionDate, 0) + quantity);
                            }
                        }
                    }
                } else if (supplier) {
                    if (line.startsWith("Transaction Type:")) {
                        isReceive = line.split(":")[1].trim().equalsIgnoreCase("Receive");
                    } else if (line.startsWith("Received Date:")) {
                        transactionDate = line.split(":")[1].trim();
                    } else if (line.startsWith("Supplier ID:")) {
                        itemCode = line.split(":")[1].trim();
                    } else if (line.startsWith("Quantity(boxes):")) {
                        quantity = Integer.parseInt(line.split(":")[1].trim());

                        if (!itemCode.equals(code)) {
                            continue;
                        }

                        if (isReceive && transactionDate.compareTo(fromDate) >= 0 && transactionDate.compareTo(toDate) <= 0) {
                            receivedData.put(transactionDate, receivedData.getOrDefault(transactionDate, 0) + quantity);
                        }
                    }
                } else if (hospital) {
                    System.out.println("hospital");
                    if (line.startsWith("Transaction Type:")) {
                        isDistribute = line.split(":")[1].trim().equalsIgnoreCase("Distribute");
                    } else if (line.startsWith("Distributed Date:")) {
                        transactionDate = line.split(":")[1].trim();
                    } else if (line.startsWith("Hospital ID:")) {
                        itemCode = line.split(":")[1].trim();
                    } else if (line.startsWith("Quantity(boxes):")) {
                        quantity = Integer.parseInt(line.split(":")[1].trim());

                        if (!itemCode.equals(code)) {
                            continue;
                        }

                        if (isDistribute && transactionDate.compareTo(fromDate) >= 0 && transactionDate.compareTo(toDate) <= 0) {
                            distributedData.put(transactionDate, distributedData.getOrDefault(transactionDate, 0) + quantity);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (item) {
            Set<String> allDates = new TreeSet<>();
            allDates.addAll(receivedData.keySet());
            allDates.addAll(distributedData.keySet());

            for (String date : allDates) {
                int receivedQty = receivedData.getOrDefault(date, 0);
                int distributedQty = distributedData.getOrDefault(date, 0);

                dataset.addValue(receivedQty, "Received", date);
                dataset.addValue(distributedQty, "Distributed", date);
            }
        } else if (supplier) {
            for (Map.Entry<String, Integer> entry : receivedData.entrySet()) {
                String date = entry.getKey();
                int qty = entry.getValue();

                dataset.addValue(qty, "Received", date);
            }
        } else if (hospital) {
            for (Map.Entry<String, Integer> entry : distributedData.entrySet()) {
                String date = entry.getKey();
                int qty = entry.getValue();

                dataset.addValue(qty, "Distributed", date);
            }
        }

        for (int row = 0; row < dataset.getRowCount(); row++) {
            for (int col = 0; col < dataset.getColumnCount(); col++) {
                Comparable rowKey = dataset.getRowKey(row);
                Comparable colKey = dataset.getColumnKey(col);
                Number value = dataset.getValue(row, col);
            }
        }
        return dataset;
    }

    public void showTransactionBarChart(String code, DefaultCategoryDataset dataset,
            JPanel pTransactionLineChart, boolean ppe, boolean supplier, boolean hospital) {
        String name = "";
        String codeTitle = "";
        String nameTitle = "";
        String filename = "";

        if (ppe) {
            filename = "ppe.txt";
            codeTitle = "Item Code";
            nameTitle = "Item Name";
        } else if (supplier) {
            filename = "suppliers.txt";
            codeTitle = "Supplier ID";
            nameTitle = "Supplier Name";
        } else if (hospital) {
            filename = "hospitals.txt";
            codeTitle = "Hospital ID";
            nameTitle = "Hospital Name";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith(codeTitle)) {
                    found = line.split(":")[1].trim().equals(code);
                } else if (found && line.startsWith(nameTitle)) {
                    name = line.split(":")[1].trim();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(code);
        System.out.println(name);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Transaction Trend - " + code + "(" + name + ")",
                "Date", "Quantity (boxes)", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(barChart);
        pTransactionLineChart.removeAll();
        pTransactionLineChart.add(chartPanel, BorderLayout.CENTER);
        pTransactionLineChart.validate();
        pTransactionLineChart.repaint();
    }

}
