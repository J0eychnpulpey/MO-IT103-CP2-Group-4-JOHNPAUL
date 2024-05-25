package com.motorphgui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class EmployeeRecordsPanel extends JPanel {

    private final JTable employeeTable;
    private DefaultTableModel tableModel;
    private List<Employee> employees;
    private String csvFilePath;

    public EmployeeRecordsPanel() {
        setLayout(new BorderLayout());

        // Provide an absolute path to the CSV file for testing
        csvFilePath = "/home/johnpaul/Documents/motorphgroup4/src/main/resources/MotorPH Employee Data - Employee Details.csv";

        // Updated column names
        String[] columnNames = {
            "Employee #", "Last Name", "First Name", "Birthday", "Address", "Phone Number", 
            "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position", 
            "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance", 
            "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate"
        };
        tableModel = new DefaultTableModel(columnNames, 0);

        // JTable to display Employee Records
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load employee data from CSV
        employees = loadEmployeesFromCSV(csvFilePath);
        for (Employee employee : employees) {
            addEmployeeRecord(employee);
        }

        // Add buttons for update and delete
        JButton btnUpdate = new JButton("Update Record");
        btnUpdate.addActionListener(e -> updateEmployeeRecord());

        JButton btnDelete = new JButton("Delete Record");
        btnDelete.addActionListener(e -> deleteEmployeeRecord());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private List<Employee> loadEmployeesFromCSV(String csvFile) {
        List<Employee> employeeList = new ArrayList<>();
        try (Reader reader = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim()
                    .parse(reader);

            for (CSVRecord record : records) {
                Employee employee = new Employee(
                        record.get("Employee #"), record.get("Last Name"), record.get("First Name"), 
                        record.get("Birthday"), record.get("Address"), record.get("Phone Number"), 
                        record.get("SSS #"), record.get("Philhealth #"), record.get("TIN #"), 
                        record.get("Pag-ibig #"), record.get("Status"), record.get("Position"), 
                        record.get("Immediate Supervisor"), record.get("Basic Salary"), 
                        record.get("Rice Subsidy"), record.get("Phone Allowance"), 
                        record.get("Clothing Allowance"), record.get("Gross Semi-monthly Rate"), 
                        record.get("Hourly Rate")
                );
                employeeList.add(employee);
            }
            System.out.println("Loaded employee data successfully.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading employee data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return employeeList;
    }

    private void addEmployeeRecord(Employee employee) {
        tableModel.addRow(new Object[]{
                employee.getEmpNo(), employee.getLastName(), employee.getFirstName(),
                employee.getBirthday(), employee.getAddress(), employee.getPhoneNumber(),
                employee.getSss(), employee.getPhilhealth(), employee.getTin(), 
                employee.getPagIbig(), employee.getStatus(), employee.getPosition(), 
                employee.getImmediateSupervisor(), employee.getBasicSalary(), 
                employee.getRiceSubsidy(), employee.getPhoneAllowance(), 
                employee.getClothingAllowance(), employee.getGrossSemiMonthlyRate(), 
                employee.getHourlyRate()
        });
    }

    private void saveEmployeesToCSV(String csvFile) {
        try (Writer writer = new FileWriter(csvFile);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                "Employee #", "Last Name", "First Name", "Birthday", "Address", "Phone Number", 
                "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position", 
                "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance", 
                "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate"))) {
            
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                csvPrinter.printRecord(
                        tableModel.getValueAt(i, 0),
                        tableModel.getValueAt(i, 1),
                        tableModel.getValueAt(i, 2),
                        tableModel.getValueAt(i, 3),
                        tableModel.getValueAt(i, 4),
                        tableModel.getValueAt(i, 5),
                        tableModel.getValueAt(i, 6),
                        tableModel.getValueAt(i, 7),
                        tableModel.getValueAt(i, 8),
                        tableModel.getValueAt(i, 9),
                        tableModel.getValueAt(i, 10),
                        tableModel.getValueAt(i, 11),
                        tableModel.getValueAt(i, 12),
                        tableModel.getValueAt(i, 13),
                        tableModel.getValueAt(i, 14),
                        tableModel.getValueAt(i, 15),
                        tableModel.getValueAt(i, 16),
                        tableModel.getValueAt(i, 17),
                        tableModel.getValueAt(i, 18)
                );
            }
            System.out.println("Saved employee data successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving employee data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateEmployeeRecord() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            String empNo = (String) tableModel.getValueAt(selectedRow, 0);
            String lastName = (String) tableModel.getValueAt(selectedRow, 1);
            String firstName = (String) tableModel.getValueAt(selectedRow, 2);
            String birthday = (String) tableModel.getValueAt(selectedRow, 3);
            String address = (String) tableModel.getValueAt(selectedRow, 4);
            String phoneNumber = (String) tableModel.getValueAt(selectedRow, 5);
            String sss = (String) tableModel.getValueAt(selectedRow, 6);
            String philhealth = (String) tableModel.getValueAt(selectedRow, 7);
            String tin = (String) tableModel.getValueAt(selectedRow, 8);
            String pagIbig = (String) tableModel.getValueAt(selectedRow, 9);
            String status = (String) tableModel.getValueAt(selectedRow, 10);
            String position = (String) tableModel.getValueAt(selectedRow, 11);
            String immediateSupervisor = (String) tableModel.getValueAt(selectedRow, 12);
            String basicSalary = (String) tableModel.getValueAt(selectedRow, 13);
            String riceSubsidy = (String) tableModel.getValueAt(selectedRow, 14);
            String phoneAllowance = (String) tableModel.getValueAt(selectedRow, 15);
            String clothingAllowance = (String) tableModel.getValueAt(selectedRow, 16);
            String grossSemiMonthlyRate = (String) tableModel.getValueAt(selectedRow, 17);
            String hourlyRate = (String) tableModel.getValueAt(selectedRow, 18);

            String newEmpNo = showInputDialog("Enter new Employee #:", empNo);
            String newLastName = showInputDialog("Enter new last name:", lastName);
            String newFirstName = showInputDialog("Enter new first name:", firstName);
            String newBirthday = showInputDialog("Enter new birthday:", birthday);
            String newAddress = showInputDialog("Enter new address:", address);
            String newPhoneNumber = showInputDialog("Enter new phone number:", phoneNumber);
            String newSss = showInputDialog("Enter new SSS #:", sss);
            String newPhilhealth = showInputDialog("Enter new Philhealth #:", philhealth);
            String newTin = showInputDialog("Enter new TIN #:", tin);
            String newPagIbig = showInputDialog("Enter new Pag-ibig #:", pagIbig);
            String newStatus = showInputDialog("Enter new status:", status);
            String newPosition = showInputDialog("Enter new position:", position);
            String newImmediateSupervisor = showInputDialog("Enter new immediate supervisor:", immediateSupervisor);
            String newBasicSalary = showInputDialog("Enter new basic salary:", basicSalary);
            String newRiceSubsidy = showInputDialog("Enter new rice subsidy:", riceSubsidy);
            String newPhoneAllowance = showInputDialog("Enter new phone allowance:", phoneAllowance);
            String newClothingAllowance = showInputDialog("Enter new clothing allowance:", clothingAllowance);
            String newGrossSemiMonthlyRate = showInputDialog("Enter new gross semi-monthly rate:", grossSemiMonthlyRate);
            String newHourlyRate = showInputDialog("Enter new hourly rate:", hourlyRate);

            tableModel.setValueAt(newEmpNo, selectedRow, 0);
            tableModel.setValueAt(newLastName, selectedRow, 1);
            tableModel.setValueAt(newFirstName, selectedRow, 2);
            tableModel.setValueAt(newBirthday, selectedRow, 3);
            tableModel.setValueAt(newAddress, selectedRow, 4);
            tableModel.setValueAt(newPhoneNumber, selectedRow, 5);
            tableModel.setValueAt(newSss, selectedRow, 6);
            tableModel.setValueAt(newPhilhealth, selectedRow, 7);
            tableModel.setValueAt(newTin, selectedRow, 8);
            tableModel.setValueAt(newPagIbig, selectedRow, 9);
            tableModel.setValueAt(newStatus, selectedRow, 10);
            tableModel.setValueAt(newPosition, selectedRow, 11);
            tableModel.setValueAt(newImmediateSupervisor, selectedRow, 12);
            tableModel.setValueAt(newBasicSalary, selectedRow, 13);
            tableModel.setValueAt(newRiceSubsidy, selectedRow, 14);
            tableModel.setValueAt(newPhoneAllowance, selectedRow, 15);
            tableModel.setValueAt(newClothingAllowance, selectedRow, 16);
            tableModel.setValueAt(newGrossSemiMonthlyRate, selectedRow, 17);
            tableModel.setValueAt(newHourlyRate, selectedRow, 18);

            saveEmployeesToCSV(csvFilePath);
        } else {
            JOptionPane.showMessageDialog(this, "No record selected for update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployeeRecord() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected record?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                saveEmployeesToCSV(csvFilePath);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No record selected for deletion.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String showInputDialog(String message, String initialValue) {
        String input = JOptionPane.showInputDialog(this, message, initialValue);
        return input == null ? initialValue : input;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Employee Records");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new EmployeeRecordsPanel());
            frame.setVisible(true);
        });
    }
}
