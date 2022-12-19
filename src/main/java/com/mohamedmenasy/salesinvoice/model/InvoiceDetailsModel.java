package com.mohamedmenasy.salesinvoice.model;

import com.mohamedmenasy.salesinvoice.model.pojo.InvoiceLine;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class InvoiceDetailsModel extends DefaultTableModel {

    private String[] columnsHeader = {"No.", "Item Name", "Item Price", "Count", "Item Total"};
    private ArrayList<InvoiceLine> invoiceItems;

    public InvoiceDetailsModel(ArrayList<InvoiceLine> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public int getRowCount() {
        if (this.invoiceItems == null) {
            invoiceItems = new ArrayList<>();
        }
        return invoiceItems.size();
    }

    public int getColumnCount() {
        return columnsHeader.length;
    }

    public String getColumnName(int column) {
        return columnsHeader[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine item = invoiceItems.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getInvoiceHeader().getInvoiceNum();
            case 1:
                return item.getItemName();
            case 2:
                return item.getItemPrice();
            case 3:
                return item.getCount();
            case 4:
                return item.getItemTotal();
        }
        return "";
    }
}
