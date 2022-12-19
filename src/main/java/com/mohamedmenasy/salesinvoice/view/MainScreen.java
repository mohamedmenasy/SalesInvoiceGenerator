package com.mohamedmenasy.salesinvoice.view;

import com.mohamedmenasy.salesinvoice.controller.FileOperationsController;
import com.mohamedmenasy.salesinvoice.model.InvoiceDetailsModel;
import com.mohamedmenasy.salesinvoice.model.InvoicesModel;
import com.mohamedmenasy.salesinvoice.model.MainTableModel;
import com.mohamedmenasy.salesinvoice.model.pojo.InvoiceHeader;
import com.mohamedmenasy.salesinvoice.model.pojo.InvoiceLine;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainScreen extends JFrame implements ActionListener, ListSelectionListener {

    private JTable invoiceDetailsTable;
    private JLabel invoiceNumber;
    public JTextField invoiceDate;
    private JTextField customerName;
    private JLabel invoiceTotal;
    private JTable invoiceTable;
    private final ArrayList<ArrayList<String>> invoices = new ArrayList<>();
    private final ArrayList<ArrayList<String>> invoiceDetails = new ArrayList<>();

    private final FileOperationsController fileOperationsController;
    private InvoicesModel invoicesModel;
    private CreateNewInvoiceDialog createNewInvoiceDialog;
    private CreateNewInvoiceItemDialog createNewInvoiceItemDialog;
    private final ArrayList<InvoiceHeader> invoicesData = new ArrayList<>();
    private ArrayList<InvoiceLine> invoiceItems = new ArrayList<>();
    private InvoiceDetailsModel invoiceDetailsModel;

    /**
     * Constructor
     *
     * @param title screen title
     */
    public MainScreen(String title) {
        super(title);
        fileOperationsController = new FileOperationsController();
        initMenuBar();

        JPanel main = new JPanel();
        JPanel layoutBottom = new JPanel();
        JPanel invoiceDetailsPanel = new JPanel();
        JPanel layout1 = new JPanel();
        JPanel layout2 = new JPanel();
        JPanel layout3 = new JPanel();
        JPanel layout4 = new JPanel();
        JPanel layout5 = new JPanel();

        main.setLayout(new GridLayout(1, 2));
        layout1.setLayout(new BorderLayout(5, 5));
        layout2.setLayout(new BorderLayout(5, 5));
        invoiceDetailsPanel.setLayout(new GridLayout(1, 2));
        layout3.setLayout(new BoxLayout(layout3, BoxLayout.Y_AXIS));
        layout4.setLayout(new FlowLayout());
        layout5.setLayout(new GridLayout(4, 2));

        initInvoicePanel(main, layout1);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        initInvoiceDetailsPanel(constraints, layout2, layout3, layout5, layoutBottom);
        invoiceDetailsPanel.add(layout2);
        main.add(invoiceDetailsPanel);
        add(main);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim.width / 2, dim.height / 2);
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Initializes the main screen menu bar
     */
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem load = new JMenuItem("Load File");
        load.setActionCommand("load");
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
        load.addActionListener(this);
        menu.add(load);

        JMenuItem save = new JMenuItem("Save File");
        save.setActionCommand("save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        save.addActionListener(this);
        menu.add(save);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    /**
     * Initializes the invoice panel
     *
     * @param main
     * @param layout1
     */
    private void initInvoicePanel(JPanel main, JPanel layout1) {
        JPanel invoicePanel = new JPanel();
        Border titledBorder = BorderFactory.createTitledBorder("Invoice Table");

        invoicePanel.setLayout(new GridLayout(1, 1));
        invoicePanel.setBorder(titledBorder);

        MainTableModel TLeft = new MainTableModel(new String[]{"No.", "Date", "Customer", "Total"}, invoices);
        invoiceTable = new JTable(TLeft);
        invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        invoiceTable.getSelectionModel().addListSelectionListener(this);
        layout1.add(new JScrollPane(invoiceTable), BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));

        JButton createNewInvoiceButton = new JButton("Create New Invoice");
        createNewInvoiceButton.setActionCommand("create");
        createNewInvoiceButton.addActionListener(this);
        bottom.add(createNewInvoiceButton);
        JButton delete = new JButton("Delete Invoice");
        delete.setActionCommand("delete");
        delete.addActionListener(this);
        bottom.add(delete);

        layout1.add(bottom, BorderLayout.SOUTH);
        invoicePanel.add(layout1);
        main.add(invoicePanel);
    }

    /**
     * Initializes the invoice details panel
     *
     * @param constraints
     * @param layout2
     * @param layout3
     * @param layout5
     * @param layoutBottom
     */
    private void initInvoiceDetailsPanel(GridBagConstraints constraints, JPanel layout2, JPanel layout3, JPanel layout5, JPanel layoutBottom) {

        constraints.gridx = 0;
        constraints.gridy = 0;
        Border border = BorderFactory.createCompoundBorder();

        layout5.add(new JLabel("Invoice Number"), constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        invoiceNumber = new JLabel("");
        layout5.add(invoiceNumber, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        layout5.add(new JLabel("Invoice Date"), constraints);

        invoiceDate = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        layout5.add(invoiceDate, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        layout5.add(new JLabel("Customer Name"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        customerName = new JTextField(20);
        layout5.add(customerName, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;

        layout5.add(new JLabel("Invoice Total"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        invoiceTotal = new JLabel("");
        layout5.add(invoiceTotal, constraints);

        layout2.add(layout5, BorderLayout.NORTH);

        layout3.setBorder(BorderFactory.createTitledBorder("Invoice Items"));

        MainTableModel TRight = new MainTableModel(new String[]{"No.", "Item Name", "Item Price", "Count", "Item Total"}, invoiceDetails);
        invoiceDetailsTable = new JTable(TRight);
        invoiceDetailsTable.setEnabled(false);
        layout3.add(new JScrollPane(invoiceDetailsTable));
        layout2.add(layout3, BorderLayout.CENTER);

        layoutBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));
        JButton Save = new JButton("Create");
        Save.setActionCommand("saveButton");
        Save.addActionListener(this);
        layoutBottom.add(Save);
        JButton cancel = new JButton("Cancel");
        cancel.setActionCommand("cancel");
        cancel.addActionListener(this);
        layoutBottom.add(cancel);
        layout2.add(layoutBottom, BorderLayout.SOUTH);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        invoiceTableRowSelected();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (Objects.equals(e.getActionCommand(), "createInvoice")) {
            createInvoiceAction();
        } else if (Objects.equals(e.getActionCommand(), "cancelInvoice")) {
            createInvoiceCancel();
        } else if (Objects.equals(e.getActionCommand(), "createItem")) {
            createInvoiceItem();
        } else if (Objects.equals(e.getActionCommand(), "CancelItem")) {
            createItemCancel();
        } else if (Objects.equals(e.getActionCommand(), "saveButton")) {
            createItemAction();
        } else if (Objects.equals(e.getActionCommand(), "cancel")) {
            try {
                deleteItem();
            } catch (Exception item) {
                showDialog("Error", "Invoice item cannot be deleted", JOptionPane.ERROR_MESSAGE);
            }
        } else if (Objects.equals(e.getActionCommand(), "create")) {
            invoicesModel = new InvoicesModel(invoicesData);
            invoiceTable.setModel(invoicesModel);
            invoiceTable.validate();
            createInvoice();
        } else if (Objects.equals(e.getActionCommand(), "delete")) {
            try {
                deleteInvoice();
            } catch (Exception file) {
                showDialog("Error", "Invoice cannot be deleted", JOptionPane.ERROR_MESSAGE);
            }
        } else if (Objects.equals(e.getActionCommand(), "load")) {
            try {
                ArrayList<InvoiceHeader> invoiceHeaderData = null;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select invoices file");
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "CSV (comma-separated values) file", "csv");
                fileChooser.setFileFilter(filter);
                int option = fileChooser.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    invoiceHeaderData =
                            fileOperationsController.loadInvoiceHeader(fileChooser.getSelectedFile());
                    invoicesModel = new InvoicesModel(invoiceHeaderData);
                    invoiceTable.setModel(invoicesModel);
                    invoiceTable.validate();
                }

                fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select invoice items");
                fileChooser.setFileFilter(filter);
                option = fileChooser.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    fileOperationsController.loadInvoiceItems(invoiceHeaderData, fileChooser.getSelectedFile());
                    //TODO : check this
                    invoicesModel = new InvoicesModel(invoiceHeaderData);
                    invoiceTable.setModel(invoicesModel);
                    invoiceTable.validate();
                }

            } catch (IOException ex) {
                showDialog("Error Reading File", "Cannot read file, Please double check file name and make sure it is readable", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (ParseException ex) {
                showDialog("Error Parsing Date", "Cannot parse date, please make sure the date format is day-month-year", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else if (Objects.equals(e.getActionCommand(), "save")) {
            try {
                ArrayList<InvoiceHeader> invoiceHeaderData = null;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Invoices Files");
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "CSV (comma-separated values) file", "csv");
                fileChooser.setFileFilter(filter);
                int option = fileChooser.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    if (fileOperationsController.saveInvoiceHeader(invoiceTable, fileChooser.getSelectedFile())) {
                        showDialog("Saved Successfully", "Invoice saved successfully", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

                fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save invoice items file");
                fileChooser.setFileFilter(filter);
                option = fileChooser.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    if (fileOperationsController.saveInvoiceItems(invoicesData, fileChooser.getSelectedFile())) {
                        showDialog("Saved Successfully", "Invoice items saved successfully", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } catch (IOException ex) {
                showDialog("Error Reading File", "Cannot read file, Please double check file name and make sure it is readable", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    /**
     * Creates an invoice
     */
    private void createInvoice() {
        createNewInvoiceDialog = new CreateNewInvoiceDialog(this);
        createNewInvoiceDialog.setLocationRelativeTo(null);
        createNewInvoiceDialog.setTitle("Create New Invoice");
        createNewInvoiceDialog.setVisible(true);
    }

    /**
     * Delete an invoice
     */
    private void deleteInvoice() {
        invoiceTable.setModel(invoicesModel);
        int selectedInvoice = invoiceTable.getSelectedRow();
        invoicesData.remove(selectedInvoice);
        invoicesModel.fireTableDataChanged();
    }

    /**
     * Create new invoice item
     */
    private void createItemAction() {
        createNewInvoiceItemDialog = new CreateNewInvoiceItemDialog(this);
        createNewInvoiceItemDialog.setLocationRelativeTo(null);
        createNewInvoiceItemDialog.setTitle("Create New Invoice Item");
        createNewInvoiceItemDialog.setVisible(true);
    }

    /**
     * Delete an invoice item
     */
    private void deleteItem() {
        invoiceDetailsTable.setModel(invoiceDetailsModel);
        int rowSelected = invoiceDetailsTable.getSelectedRow();
        invoiceItems.remove(rowSelected);
        invoiceDetailsModel.fireTableDataChanged();
    }

    private void invoiceTableRowSelected() {
        invoiceTable.setModel(invoicesModel);
        int selectedRow = invoiceTable.getSelectedRow();
        if (selectedRow != -1) {
            InvoiceHeader row = invoicesModel.getInvoices().get(selectedRow);
            invoiceNumber.setText(String.valueOf(row.getInvoiceNum()));
            customerName.setText(row.getCustomerName());
            invoiceDate.setText(row.getInvoiceDate().toString());
            invoiceTotal.setText(String.valueOf(row.getInvoiceTotal()));
            invoiceItems = row.getInvoiceLines();
            invoiceDetailsModel = new InvoiceDetailsModel(invoiceItems);
            invoiceDetailsTable.setModel(invoiceDetailsModel);
            invoiceDetailsModel.fireTableDataChanged();
        }
    }

    private void createInvoiceAction() {
        String customer = createNewInvoiceDialog.getCustomerName();
        String invDateStr = createNewInvoiceDialog.getInvoiceDate();
        Date invoiceDate = new Date();
        if (invDateStr.isBlank()) {
            showDialog("Error", "Please enter a valid date", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (customer.isBlank()) {
            showDialog("Error", "Please enter customer name", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            invoiceDate = FileOperationsController.dateFormat.parse(invDateStr);
        } catch (ParseException ex) {
            showDialog("Error Parsing Date", "Cannot parse date, please make sure the date format is day-month-year", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        //invoicesTableDialog.setVisible(false);
        int num = getLastNum() + 1;
        InvoiceHeader newInvoice = new InvoiceHeader(num, customer, invoiceDate);
        invoicesData.add(newInvoice);
        invoicesModel.fireTableDataChanged();
        createNewInvoiceDialog.setVisible(false);
    }

    /**
     * called when user cancel the invoice creation
     */
    private void createInvoiceCancel() {
        createNewInvoiceDialog.setVisible(false);
    }

    /**
     * Create an invoice item
     */
    private void createInvoiceItem() {
        if (invoiceTable.getSelectedRow() != -1) {
            String itemName = createNewInvoiceItemDialog.getItemName();
            String itemCountStr = createNewInvoiceItemDialog.getItemCount();
            String itemPriceStr = createNewInvoiceItemDialog.getItemPrice();
            if (itemName.isBlank()) {
                showDialog("Error!", "Please enter a valid item name", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (itemCountStr.isBlank()) {
                showDialog("Error!", "Please enter a valid item count", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (itemPriceStr.isBlank()) {
                showDialog("Error!", "Please enter a valid item price", JOptionPane.ERROR_MESSAGE);
                return;
            }
            createNewInvoiceItemDialog.setVisible(false);
            int itemCount = Integer.parseInt(itemCountStr);
            double itemPrice = Double.parseDouble(itemPriceStr);
            InvoiceHeader invoice = invoicesData.get(invoiceTable.getSelectedRow());
            InvoiceLine line = new InvoiceLine(itemName, itemPrice, itemCount, invoice);
            invoice.addInvoiceItem(line);
            invoiceDetailsModel.fireTableDataChanged();
            invoicesModel.fireTableDataChanged();
        } else {
            showDialog("Error!", "You need to select an invoice first", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * called when user cancel the invoice item creation
     */
    private void createItemCancel() {
        createNewInvoiceItemDialog.setVisible(false);
    }

    /**
     * Get the last invoice number
     *
     * @return
     */
    private int getLastNum() {
        int num = 0;
        for (InvoiceHeader invoice : invoicesData) {
            if (invoice.getInvoiceNum() > num) {
                num = invoice.getInvoiceNum();
            }
        }
        return num;
    }

    private void showDialog(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
