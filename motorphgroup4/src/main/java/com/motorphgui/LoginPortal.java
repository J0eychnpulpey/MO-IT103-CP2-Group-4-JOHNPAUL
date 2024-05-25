package com.motorphgui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class LoginPortal extends JFrame {
    private final Font mainFont = new Font("Segoe UI", Font.PLAIN, 16);
    private JTextField tfEmployeeID;
    private JPasswordField pfPassword;
    private JLabel lbMessage;

    public LoginPortal() {
        initialize();
    }

    private void initialize() {
        /*********************Form Panel*********************/
        JLabel lbTitle = new JLabel("MotorPH Payroll Management System", SwingConstants.CENTER);
        lbTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbTitle.setPreferredSize(new Dimension(400, 30));
         

        JLabel lbEmployeeID = new JLabel("Employee ID");
        lbEmployeeID.setFont(mainFont);

        tfEmployeeID = new JTextField();
        tfEmployeeID.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        formPanel.add(lbTitle);
        formPanel.add(lbEmployeeID);
        formPanel.add(tfEmployeeID);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);

        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        /************Message Label******************************/
        lbMessage = new JLabel();
        lbMessage.setFont(mainFont);
        lbMessage.setHorizontalAlignment(SwingConstants.CENTER);

        /********************Buttons Panel**********************/
        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setFont(mainFont);
        btnLogin.setBackground(new Color(76, 175, 80));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLogin);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(lbMessage, BorderLayout.NORTH);

        add(mainPanel);

        setTitle("MotorPH Payroll Management System");
        setSize(400, 300);
        setMinimumSize(new Dimension(400, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void handleLogin() {
        String employeeIDText = tfEmployeeID.getText();
        String password = new String(pfPassword.getPassword());
    
        if (employeeIDText.isEmpty() || password.isEmpty()) {
            lbMessage.setText("Please enter both Employee ID and Password.");
            lbMessage.setForeground(Color.RED);
            return;
        }
    
        try {
            int employeeID = Integer.parseInt(employeeIDText);
            if (authenticateUser(employeeID, password)) {
                lbMessage.setText("Login successful. Welcome!");
                lbMessage.setForeground(Color.GREEN);
    
                new Main(employeeID);
                dispose();
                
            } else {
                lbMessage.setText("Invalid Employee ID or Password.");
                lbMessage.setForeground(Color.RED);
            }
        } catch (NumberFormatException e) {
            lbMessage.setText("Employee ID must be a number.");
            lbMessage.setForeground(Color.RED);
        }
    }

    private boolean authenticateUser(int employeeID, String password) {
        Map<Integer, String> users = loadUsersFromCSV("/home/johnpaul/Documents/motorphgroup4/src/main/resources/user.csv");
        String storedPassword = users.get(employeeID);
        return storedPassword != null && storedPassword.equals(password);
    }

    private Map<Integer, String> loadUsersFromCSV(String csvFile) {
        Map<Integer, String> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String pass = values[1];
                users.put(id, pass);
            }
        } catch (IOException e) {
            lbMessage.setText("Error reading user data.");
            lbMessage.setForeground(Color.RED);
        }
        return users;
    }

    public static void main(String[] args) {
        new LoginPortal();
    }
}