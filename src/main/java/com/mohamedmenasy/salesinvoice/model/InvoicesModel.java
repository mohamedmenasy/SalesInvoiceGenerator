package com.mohamedmenasy.salesinvoice.model;

import com.mohamedmenasy.salesinvoice.model.pojo.InvoiceHeader;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

import static com.mohamedmenasy.salesinvoice.controller.FileOperationsController.dateFormat;

public class InvoicesModel extends DefaultTableModel {
    private final String[] columnsHeader = {"No.", "Date", "Customer", "Total"};
    private ArrayList<InvoiceHeader> invoices;

    public InvoicesModel(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }

    public int getColumnCount() {
        return columnsHeader.length;
    }

    public String getColumnName(int column) {
        return columnsHeader[column];
    }

    public ArrayList<InvoiceHeader> getInvoices() {
        return invoices;
    }

    public int getRowCount() {
        if (this.invoices == null) {
            invoices = new ArrayList<>();
        }
        return invoices.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        InvoiceHeader invoice = invoices.get(row);
        switch (column) {
            case 0:
                return invoice.getInvoiceNum();
            case 1:
                return dateFormat.format(invoice.getInvoiceDate());
            case 2:
                return invoice.getCustomerName();
            case 3:
                return invoice.getInvoiceTotal();
        }
        return null;
    }
}
