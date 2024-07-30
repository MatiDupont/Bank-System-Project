package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.service.BankAccountService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerAccountsGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelSubtitle, labelFooter;
    private JTable tableAccounts;
    private JScrollPane scrollPane;
    private JButton btnProfile, btnSelect, btnLogOut, btnNewBankAccount;
    private Color background = new Color(18,18,18);
    Customer customer;

    public CustomerAccountsGUI(Customer customer) {
        this.customer = customer;
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MDCompany");
        setIconImage(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png").getImage());
        getContentPane().setBackground(background);

        initComponents();
    }

    private void initComponents() {
        btnProfile = new JButton(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\profileIcon.png"));
        btnProfile.setToolTipText("Profile");
        btnProfile.setBounds(30,50,65,65);
        btnProfile.setBackground(new Color(0,120,255));
        add(btnProfile);
        btnProfile.addActionListener(this);

        ImageIcon systemIcon = new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png");
        Icon icon = new ImageIcon(systemIcon.getImage().getScaledInstance(70,70, Image.SCALE_DEFAULT));
        labelIcon = new JLabel(icon);
        labelIcon.setBounds(350,50,70,70);
        this.repaint();
        add(labelIcon);

        labelSubtitle = new JLabel("Select Account");
        labelSubtitle.setBounds(340,150,100,20);
        labelSubtitle.setForeground(new Color(255,255,255));
        labelSubtitle.setFont(new Font("Arial", Font.BOLD, 12));
        add(labelSubtitle);

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String columns[] = {"Bank", "Account number", "Account type", "Coin type", "Account state"};
        tableModel.setColumnIdentifiers(columns);

        tableAccounts = new JTable(tableModel);
        scrollPane = new JScrollPane(tableAccounts);
        scrollPane.setBounds(90, 180, 600, 200);
        add(scrollPane);

        loadTable();

        btnNewBankAccount = new JButton(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\newAccountIcon.png"));
        btnNewBankAccount.setToolTipText("Create new bank account");
        btnNewBankAccount.setBounds(700,200,65,65);
        btnNewBankAccount.setBackground(new Color(0,120,255));
        add(btnNewBankAccount);
        btnNewBankAccount.addActionListener(this);

        btnSelect = new JButton("Select");
        btnSelect.setBounds(340,400,100,35);
        btnSelect.setBackground(new Color(0,120,255));
        btnSelect.setForeground(new Color(0,0,0));
        add(btnSelect);
        btnSelect.addActionListener(this);

        btnLogOut = new JButton("Log Out");
        btnLogOut.setToolTipText("Log Out");
        btnLogOut.setBounds(630,450,100,30);
        btnLogOut.setBackground(new Color(0,120,255));
        btnLogOut.setForeground(new Color(0,0,0));
        btnLogOut.setFont(new Font("Calibri", Font.BOLD, 15));
        add(btnLogOut);
        btnLogOut.addActionListener(this);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(312, 500, 210, 20);
        labelFooter.setForeground(new Color(255,255,255));
        add(labelFooter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnProfile) {
            ProfileCustomerGUI profileCustomerGUI = new ProfileCustomerGUI(this.customer);
            profileCustomerGUI.setBounds(0,0,800,600);
            profileCustomerGUI.setResizable(false);
            profileCustomerGUI.setLocationRelativeTo(null);
            profileCustomerGUI.setVisible(true);
            this.dispose();
        }
        if (e.getSource() == btnNewBankAccount) {
            this.dispose();
            CreateNewBankAccountGUI createNewBankAccountGUI = new CreateNewBankAccountGUI(this.customer);
            createNewBankAccountGUI.setBounds(0,0,800,600);
            createNewBankAccountGUI.setResizable(false);
            createNewBankAccountGUI.setLocationRelativeTo(null);
            createNewBankAccountGUI.setVisible(true);
        }
        if (e.getSource() == btnSelect) {
            if (tableAccounts.getRowCount() > 0) {
                if (tableAccounts.getSelectedRow() != -1) {
                    String numberAccount = (String) tableAccounts.getValueAt(tableAccounts.getSelectedRow(), 1);

                    BankAccountService bankAccountService = new BankAccountService();

                    BankAccount bankAccount = bankAccountService.findBank(numberAccount);

                    JPasswordField passwordFieldBankAccount = new JPasswordField();
                    int okCancel = JOptionPane.showConfirmDialog(null, passwordFieldBankAccount, "Enter the bank account security code:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (okCancel == JOptionPane.OK_OPTION) {
                        String securityCode = new String(passwordFieldBankAccount.getPassword());

                        if (!securityCode.isEmpty()) {
                            if (bankAccount.isAccountState()) {
                                if (securityCode.equals(bankAccount.getSecurityCode())) {
                                    this.dispose();
                                    CustomerLicenseGUI customerLicenseGUI = new CustomerLicenseGUI(this.customer, bankAccount);
                                    customerLicenseGUI.setBounds(0, 0, 800, 600);
                                    customerLicenseGUI.setResizable(false);
                                    customerLicenseGUI.setLocationRelativeTo(null);
                                    customerLicenseGUI.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(this, "Access denied", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(this, "Your account is inactive.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(this, "Security code is required to proceed.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No account was selected", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "There are no records in the table to display. \n Create new bank account.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnLogOut) {
            this.dispose();
            CustomerGUI customerGUI = new CustomerGUI();
            customerGUI.setBounds(0,0,800,600);
            customerGUI.setResizable(false);
            customerGUI.setLocationRelativeTo(null);
            customerGUI.setVisible(true);
        }
    }

    private void loadTable() {
        BankAccountService bankAccountService = new BankAccountService();

        DefaultTableModel tableModel = (DefaultTableModel) tableAccounts.getModel();

        tableModel.setRowCount(0);

        List<BankAccount> bankAccounts = bankAccountService.getBankAccountsByCustomer(this.customer.getId());

        if (bankAccounts != null) {
            for (BankAccount ba : bankAccounts) {
                Object[] object = {
                        ba.getBankingEntities(),
                        ba.getAccountNumber(),
                        ba.getAccountType(),
                        ba.getCoin(),
                        ba.isAccountState()
                };
                tableModel.addRow(object);
            }
        }
    }
}
