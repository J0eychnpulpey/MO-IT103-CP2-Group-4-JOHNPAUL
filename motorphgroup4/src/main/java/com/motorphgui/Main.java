package com.motorphgui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Main extends JFrame {

    public Main(int employeeID) {
        setLayout(new BorderLayout());

        JButton btnShowProfile = new JButton("Show Employee Profile");
        btnShowProfile.addActionListener(e -> showProfile());

        JButton btnViewRecords = new JButton("View Employee Records");
        btnViewRecords.addActionListener(e -> viewEmployeeRecords());

        JButton btnApplyLeave = new JButton("Apply for Leave");
        btnApplyLeave.addActionListener(e -> applyForLeave());

        JButton btnAbout = new JButton("About");
        btnAbout.addActionListener(e -> showAbout());

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(btnShowProfile, gbc);

        gbc.gridy = 1;
        buttonPanel.add(btnViewRecords, gbc);

        gbc.gridy = 2;
        buttonPanel.add(btnApplyLeave, gbc);

        gbc.gridy = 3;
        buttonPanel.add(btnAbout, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        setTitle("Main Menu");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void showProfile() {
        String employeeIDText = JOptionPane.showInputDialog(this, "Enter Employee ID:");
        if (employeeIDText == null || employeeIDText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int employeeID = Integer.parseInt(employeeIDText);
            Map<String, String[]> employeeData = loadEmployeesFromCSV("/home/johnpaul/Documents/motorphgroup4/src/main/resources/MotorPH Employee Data - Employee Details.csv");
            String[] profile = employeeData.get(String.valueOf(employeeID));
            if (profile != null) {
                String profileInfo = String.format("""
                                                   Employee #: %s
                                                   Last Name: %s
                                                   First Name: %s
                                                   Birthday: %s
                                                   Address: %s
                                                   Phone Number: %s
                                                   SSS #: %s
                                                   Philhealth #: %s
                                                   TIN #: %s
                                                   Pag-ibig #: %s
                                                   Status: %s
                                                   Position: %s
                                                   Immediate Supervisor: %s
                                                   Basic Salary: %s
                                                   Rice Subsidy: %s
                                                   Phone Allowance: %s
                                                   Clothing Allowance: %s
                                                   Gross Semi-monthly Rate: %s
                                                   Hourly Rate: %s""",
                    profile[0],  // Employee #
                    profile[1],  // Last Name
                    profile[2],  // First Name
                    profile[3],  // Birthday
                    profile[4],  // Address
                    profile[5],  // Phone Number
                    profile[6],  // SSS #
                    profile[7],  // Philhealth #
                    profile[8],  // TIN #
                    profile[9],  // Pag-ibig #
                    profile[10], // Status
                    profile[11], // Position
                    profile[12], // Immediate Supervisor
                    profile[13], // Basic Salary
                    profile[14], // Rice Subsidy
                    profile[15], // Phone Allowance
                    profile[16], // Clothing Allowance
                    profile[17], // Gross Semi-monthly Rate
                    profile[18]  // Hourly Rate
                );
                JOptionPane.showMessageDialog(this, profileInfo, "Employee Profile", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Employee with ID " + employeeID + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewEmployeeRecords() {
        JFrame recordsFrame = new JFrame("Employee Records");
        recordsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        recordsFrame.setSize(600, 400);

        EmployeeRecordsPanel recordsPanel = new EmployeeRecordsPanel();
        recordsFrame.add(recordsPanel);

        recordsFrame.setVisible(true);
    }

    private void applyForLeave() {
        JFrame leaveFrame = new JFrame("Apply for Leave");
        leaveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaveFrame.setSize(400, 300);

        LeaveApplicationPanel leavePanel = new LeaveApplicationPanel();
        leaveFrame.add(leavePanel);

        leaveFrame.setVisible(true);
    }

    private void showAbout() {
        JFrame aboutFrame = new JFrame("About");
        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aboutFrame.setSize(200, 200);

        JTextArea aboutText = new JTextArea();
        aboutText.setText("This project was made by Group 4 IT103 | A1101");

        aboutText.setWrapStyleWord(true);
        aboutText.setLineWrap(true);
        aboutText.setOpaque(false);
        aboutText.setEditable(false);
        aboutText.setFocusable(false);

        aboutFrame.add(aboutText);
        aboutFrame.setVisible(true);
    }

    private Map<String, String[]> loadEmployeesFromCSV(String csvFile) {
        Map<String, String[]> employeeData = new HashMap<>();
        try (Reader reader = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim()
                    .parse(reader);

            for (CSVRecord record : records) {
                String[] employee = new String[]{
                        record.get("Employee #").trim(),
                        record.get("Last Name").trim(),
                        record.get("First Name").trim(),
                        record.get("Birthday").trim(),
                        record.get("Address").trim(),
                        record.get("Phone Number").trim(),
                        record.get("SSS #").trim(),
                        record.get("Philhealth #").trim(),
                        record.get("TIN #").trim(),
                        record.get("Pag-ibig #").trim(),
                        record.get("Status").trim(),
                        record.get("Position").trim(),
                        record.get("Immediate Supervisor").trim(),
                        record.get("Basic Salary").trim(),
                        record.get("Rice Subsidy").trim(),
                        record.get("Phone Allowance").trim(),
                        record.get("Clothing Allowance").trim(),
                        record.get("Gross Semi-monthly Rate").trim(),
                        record.get("Hourly Rate").trim()
                };
                employeeData.put(employee[0], employee);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading the CSV file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return employeeData;
    }

    public static void main(String[] args) {
        LoginPortal loginPortal = new LoginPortal();
    }
}
