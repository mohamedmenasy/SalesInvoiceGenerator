package com.mohamedmenasy.salesinvoice.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MainTableModel extends AbstractTableModel {
    private final String[] columnsNames;
    private final ArrayList<ArrayList<String>> values;

    public MainTableModel(String[] columnsNames, ArrayList<ArrayList<String>> values) {
        this.columnsNames = columnsNames;
        this.values = values;
    }

    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        return columnsNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return values.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnsNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
