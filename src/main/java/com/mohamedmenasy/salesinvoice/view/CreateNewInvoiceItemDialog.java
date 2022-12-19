package com.mohamedmenasy.salesinvoice.view;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Dialog to create new invoice item
 */
public class CreateNewInvoiceItemDialog extends JDialog {
    private JLabel itemNameLabel;
    private JTextField itemName;
    private JLabel itemCountLabel;
    private JFormattedTextField itemCount;
    private JLabel itemPriceLabel;
    private JFormattedTextField itemPrice;
    private JButton okButton;
    private JButton cancelButton;

    public CreateNewInvoiceItemDialog(MainScreen frame) {
        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter longNumberFormatter = new NumberFormatter(longFormat);
        longNumberFormatter.setValueClass(Long.class);
        longNumberFormatter.setAllowsInvalid(false);
        longNumberFormatter.setMinimum(1L);


        itemNameLabel = new JLabel("Item Name : ");
        itemName = new JTextField(20);
        itemCountLabel = new JLabel("Item Count : ");
        itemCount = new JFormattedTextField(longNumberFormatter);
        itemCount.setColumns(20);
        itemPriceLabel = new JLabel("Item Price : ");
        itemPrice = new JFormattedTextField(longNumberFormatter);
        itemPrice.setColumns(20);

        okButton = new JButton("Ok");
        okButton.setActionCommand("createItem");
        okButton.addActionListener(frame);
        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("CancelItem");
        cancelButton.addActionListener(frame);

        setLayout(new GridLayout(4, 2));
        setResizable(false);

        add(itemNameLabel);
        add(itemName);
        add(itemCountLabel);
        add(itemCount);
        add(itemPriceLabel);
        add(itemPrice);
        add(okButton);
        add(cancelButton);

        pack();
    }

    public String getItemName() {
        return itemName.getText();
    }

    public String getItemCount() {
        return itemCount.getText();
    }

    public String getItemPrice() {
        return itemPrice.getText();
    }
}
