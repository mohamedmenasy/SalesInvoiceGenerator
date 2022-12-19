package com.mohamedmenasy.salesinvoice.controller;

import com.mohamedmenasy.salesinvoice.model.pojo.InvoiceHeader;
import com.mohamedmenasy.salesinvoice.model.pojo.InvoiceLine;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.mohamedmenasy.salesinvoice.controller.InvoiceOperations.findInvoiceByNumber;

public class FileOperationsController {
    // Main date format
    public static final String dateFormatStr = "dd-MM-yyyy";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);

    /**
     * Reads the file and returns the list of invoice headers
     * @param selectedFile
     * @return list of invoice headers
     * @throws IOException
     * @throws ParseException
     */
    public ArrayList<InvoiceHeader> loadInvoiceHeader(File selectedFile) throws IOException, ParseException {
        ArrayList<InvoiceHeader> invoicesHeaderData = new ArrayList<>();
        FileReader fileReader = new FileReader(selectedFile);
        BufferedReader buffer = new BufferedReader(fileReader);
        String invoiceString = null;
        while ((invoiceString = buffer.readLine()) != null) {
            String[] items = invoiceString.split(",");
            String invoiceNumberStr = items[0];
            String invoiceDateStr = items[1];
            String customerName = items[2];

            int invoiceNumber = Integer.parseInt(invoiceNumberStr);
            Date invoiceDate = dateFormat.parse(invoiceDateStr);
            InvoiceHeader invoice = new InvoiceHeader(invoiceNumber, customerName, invoiceDate);
            invoicesHeaderData.add(invoice);
        }
        buffer.close();
        fileReader.close();
        return invoicesHeaderData;
    }

    /**
     * Reads the file and load the list of invoice items
     * @param invoicesHeaderData
     * @param selectedFile
     * @throws IOException
     */
    public void loadInvoiceItems(ArrayList<InvoiceHeader> invoicesHeaderData, File selectedFile) throws IOException {
        FileReader fileReader = new FileReader(selectedFile);
        BufferedReader buffer = new BufferedReader(fileReader);
        String invoiceString = null;

        while ((invoiceString = buffer.readLine()) != null) {
            String[] invoiceItems = invoiceString.split(",");
            String invoiceNumberStr = invoiceItems[0];
            String item = invoiceItems[1];
            String invoicePriceStr = invoiceItems[2];
            String invoiceCountStr = invoiceItems[3];

            int invoiceNumber = Integer.parseInt(invoiceNumberStr);
            double price = Double.parseDouble(invoicePriceStr);
            int count = Integer.parseInt(invoiceCountStr);
            InvoiceHeader invoiceHeader = findInvoiceByNumber(invoicesHeaderData, invoiceNumber);
            InvoiceLine invoiceLine = new InvoiceLine(item, price, count, invoiceHeader);
            invoiceHeader.addInvoiceItem(invoiceLine);
        }

    }

    /**
     * Saves the invoice header
     * @param invoiceTable
     * @param selectedFile
     * @return
     * @throws IOException
     */
    public boolean saveInvoiceHeader(JTable invoiceTable, File selectedFile) throws IOException {
        FileWriter fw = new FileWriter(selectedFile + ".csv");
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < invoiceTable.getRowCount(); i++) {
            for (int j = 0; j < invoiceTable.getColumnCount(); j++) {
                bw.write(invoiceTable.getValueAt(i, j).toString() + ",");
            }
            bw.newLine();
        }
        bw.close();
        fw.close();
        return true;
    }

    /**
     * Saves the invoice items
     * @param selectedFile
     * @param invoiceHeader
     * @param selectedFile
     * @return whatever the file is saved successfully or not
     * @throws IOException
     */
    public boolean saveInvoiceItems(ArrayList<InvoiceHeader> invoiceHeader, File selectedFile) throws IOException {
        FileWriter fw = new FileWriter(selectedFile + ".csv");
        BufferedWriter bw = new BufferedWriter(fw);
        for (InvoiceHeader header : invoiceHeader) {
            for (InvoiceLine line : header.getInvoiceLines()) {
                bw.write(header.getInvoiceNum() + "," + line.getItemName() + "," + line.getItemPrice() + "," + line.getCount());
                bw.newLine();
            }
        }
        bw.close();
        fw.close();
        return true;
    }
}
