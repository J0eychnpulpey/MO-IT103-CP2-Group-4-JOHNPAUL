package com.motorphgui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveApplicationPanel extends JPanel {

    private JTextField tfLeaveType, tfStartDate, tfEndDate;
    private JTextArea taReason;
    private List<String> leaveApplications;

    public LeaveApplicationPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbLeaveType = new JLabel("Leave Type:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lbLeaveType, gbc);

        tfLeaveType = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(tfLeaveType, gbc);

        JLabel lbStartDate = new JLabel("Start Date:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lbStartDate, gbc);

        tfStartDate = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(tfStartDate, gbc);

        JLabel lbEndDate = new JLabel("End Date:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lbEndDate, gbc);

        tfEndDate = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(tfEndDate, gbc);

        JLabel lbReason = new JLabel("Reason:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        add(lbReason, gbc);

        taReason = new JTextArea(5, 20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JScrollPane(taReason), gbc);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(e -> submitLeaveApplication());
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(btnSubmit, gbc);

        leaveApplications = new ArrayList<>();
    }

    private void submitLeaveApplication() {
        String leaveType = tfLeaveType.getText();
        String startDate = tfStartDate.getText();
        String endDate = tfEndDate.getText();
        String reason = taReason.getText();

        if (leaveType.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String application = String.format("Leave Type: %s\nStart Date: %s\nEnd Date: %s\nReason: %s\n",
                leaveType, startDate, endDate, reason);
        leaveApplications.add(application);

        JOptionPane.showMessageDialog(this, "Leave application submitted successfully.");
        clearFields();
    }

    private void clearFields() {
        tfLeaveType.setText("");
        tfStartDate.setText("");
        tfEndDate.setText("");
        taReason.setText("");
    }
}