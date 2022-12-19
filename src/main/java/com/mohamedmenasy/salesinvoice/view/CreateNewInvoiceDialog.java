package com.mohamedmenasy.salesinvoice.view;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

import static com.mohamedmenasy.salesinvoice.controller.FileOperationsController.dateFormat;
import static com.mohamedmenasy.salesinvoice.controller.FileOperationsController.dateFormatStr;

/**
 * Dialog to create new invoice
 */
public class CreateNewInvoiceDialog extends JDialog {
    private JLabel customerNameLabel;
    private JTextField customerName;
    private JLabel invoiceDateLabel;
    private JDateChooser invoiceDate;
    private JButton okButton;
    private JButton cancelButton;

    public CreateNewInvoiceDialog(MainScreen frame) {

        customerNameLabel = new JLabel("Customer Name : ");
        customerName = new JTextField(20);
        invoiceDateLabel = new JLabel("Invoice Date : ");

        invoiceDate = new JDateChooser();
        invoiceDate.setDateFormatString(dateFormatStr);

        okButton = new JButton("Ok");
        okButton.setActionCommand("createInvoice");
        okButton.addActionListener(frame);
        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("cancelInvoice");
        cancelButton.addActionListener(frame);

        setLayout(new GridLayout(3, 2));
        setResizable(false);

        add(invoiceDateLabel);
        add(invoiceDate);
        add(customerNameLabel);
        add(customerName);
        add(okButton);
        add(cancelButton);
        pack();
    }

    public String getCustomerName() {
        return customerName.getText();
    }

    public String getInvoiceDate() {
        if (invoiceDate.getDate() != null)
            return dateFormat.format(invoiceDate.getDate());
        return "";
    }

}
