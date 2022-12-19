package com.mohamedmenasy.salesinvoice.controller;

import com.mohamedmenasy.salesinvoice.model.pojo.InvoiceHeader;

import java.util.ArrayList;

public class InvoiceOperations {
    /**
     * This method is used to get the invoice by its number
     * @param invoiceNumber
     * @param invoicesHeader
     * @param invoiceNumber
     * @return
     */
    static InvoiceHeader findInvoiceByNumber(ArrayList<InvoiceHeader> invoicesHeader, int invoiceNumber) {
        for (InvoiceHeader invoice : invoicesHeader) {
            if (invoice.getInvoiceNum() == invoiceNumber) {
                return invoice;
            }
        }
        return null;
    }
}
