package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.model.Movement;
import ar.edu.utn.tup.service.MovementsService;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MovementsGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelSubtitle, labelBalance, labelFooter;
    private JTextField textFieldBalance;
    private JTable tableAccounts;
    private JScrollPane scrollPane;
    private JButton btnRevenue, btnExpenses, btnInvestment, btnBack;
    private Color background = new Color(18,18,18);
    Customer customer;
    BankAccount bankAccount;
    public MovementsGUI(Customer customer, BankAccount bankAccount) {
        this.customer = customer;
        this.bankAccount = bankAccount;
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png").getImage());
        getContentPane().setBackground(background);

        initComponents();
    }

    private void initComponents() {
        ImageIcon systemIcon = new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png");
        Icon icon = new ImageIcon(systemIcon.getImage().getScaledInstance(70,70,Image.SCALE_DEFAULT));
        labelIcon = new JLabel(icon);
        labelIcon.setBounds(350,50,70,70);
        this.repaint();
        add(labelIcon);

        labelBalance = new JLabel("Balance:");
        labelBalance.setBounds(357,150, 100,20);
        labelBalance.setForeground(new Color(255,255,255));
        labelBalance.setFont(new Font("Arial", Font.BOLD, 12));
        add(labelBalance);

        textFieldBalance = new JTextField(String.format("%.2f", this.bankAccount.getBalance()));
        textFieldBalance.setBounds(285,180,200,30);
        textFieldBalance.setBackground(new Color(0,120,255));
        textFieldBalance.setForeground(new Color(0,0,0));
        textFieldBalance.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldBalance.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldBalance.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldBalance.setEnabled(false);
        add(textFieldBalance);

        btnRevenue = new JButton(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\revenue.png"));
        btnRevenue.setToolTipText("Revenue");
        btnRevenue.setBounds(100,190,50,50);
        btnRevenue.setBackground(new Color(0,120,255));
        add(btnRevenue);
        btnRevenue.addActionListener(this);

        btnExpenses = new JButton(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\expenses.png"));
        btnExpenses.setToolTipText("Expenses");
        btnExpenses.setBounds(630,190,50,50);
        btnExpenses.setBackground(new Color(0,120,255));
        add(btnExpenses);
        btnExpenses.addActionListener(this);

        labelSubtitle = new JLabel("Movements");
        labelSubtitle.setBounds(350,220,100,20);
        labelSubtitle.setForeground(new Color(255,255,255));
        labelSubtitle.setFont(new Font("Arial", Font.BOLD, 12));
        add(labelSubtitle);

        loadTableRevenue();

        btnInvestment = new JButton("Financial Investments");
        btnInvestment.setToolTipText("Fixed term");
        btnInvestment.setBounds(285,470,200,30);
        btnInvestment.setBackground(new Color(0,120,255));
        btnInvestment.setForeground(new Color(0,0,0));
        btnInvestment.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnInvestment);
        btnInvestment.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setToolTipText("Back");
        btnBack.setBounds(630,470,100,30);
        btnBack.setBackground(new Color(0,120,255));
        btnBack.setForeground(new Color(0,0,0));
        btnBack.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnBack);
        btnBack.addActionListener(this);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(310,520,210,20);
        labelFooter.setForeground(new Color(255,255,255));
        add(labelFooter);
    }

    private void loadTableRevenue() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String columns[] = {"Date", "Name", "Source account", "CBU", "Amount", "Motive"};
        tableModel.setColumnIdentifiers(columns);

        this.setTitle("Revenue");

        tableAccounts = new JTable(tableModel);
        scrollPane = new JScrollPane(tableAccounts);
        scrollPane.setBounds(90,250,600,200);
        add(scrollPane);

        MovementsService movementsService = new MovementsService();

        tableModel = (DefaultTableModel) tableAccounts.getModel();

        tableModel.setRowCount(0);

        List<Movement> movements = movementsService.getMovementsReceived(this.bankAccount.getId());

        if (movements != null) {
            for (Movement m : movements) {
                Object[] object = {
                        m.getDate(),
                        m.getSourceAccount().getCustomer().getLastName() + " " + m.getSourceAccount().getCustomer().getName(),
                        m.getSourceAccount().getBankingEntities().toString(),
                        m.getSourceAccount().getCBU(),
                        m.getAmount(),
                        m.getMotive()
                };
                tableModel.addRow(object);
            }
        }

    }

    private void loadTableExpenses() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String columns[] = {"Date", "Name", "Destination account", "CBU", "Amount", "Motive"};
        tableModel.setColumnIdentifiers(columns);

        this.setTitle("Expenses");

        tableAccounts = new JTable(tableModel);
        scrollPane = new JScrollPane(tableAccounts);
        scrollPane.setBounds(90,250,600,200);
        add(scrollPane);

        MovementsService movementsService = new MovementsService();

        tableModel = (DefaultTableModel) tableAccounts.getModel();

        tableModel.setRowCount(0);

        List<Movement> movements = movementsService.getMovementsMade(this.bankAccount.getId());

        if (movements != null) {
            for (Movement m : movements) {
                Object[] object = {
                        m.getDate(),
                        m.getDestinationAccount().getCustomer().getLastName() + " " + m.getDestinationAccount().getCustomer().getName(),
                        m.getDestinationAccount().getBankingEntities().toString(),
                        m.getDestinationAccount().getCBU(),
                        m.getAmount(),
                        m.getMotive()
                };
                tableModel.addRow(object);
            }
        }
    }


    private void loadTableInvestment() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String columns[] = {"Start date", "Amount", "Interest rate", "End date", "Matured amount", "State"};
        tableModel.setColumnIdentifiers(columns);

        this.setTitle("Financial Investments");

        tableAccounts = new JTable(tableModel);
        scrollPane = new JScrollPane(tableAccounts);
        scrollPane.setBounds(90,250,600,200);
        add(scrollPane);

        MovementsService movementsService = new MovementsService();

        movementsService.checkMaturedInvestments();

        tableModel = (DefaultTableModel) tableAccounts.getModel();

        tableModel.setRowCount(0);

        List<Movement> movements = movementsService.getMovementsInvestment(this.bankAccount.getId());

        if (movements != null) {
            for (Movement m : movements) {
                Object[] object = {
                        m.getStartDate(),
                        m.getAmount(),
                        m.getInterestRate(),
                        m.getEndDate(),
                        m.getMaturedAmount(),
                        m.getStatus()
                };
                tableModel.addRow(object);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRevenue) {
            loadTableRevenue();
        }
        if (e.getSource() == btnExpenses) {
            loadTableExpenses();
        }
        if (e.getSource() == btnInvestment) {
            loadTableInvestment();
        }
        if (e.getSource() == btnBack) {
            this.dispose();
            CustomerLicenseGUI customerLicenseGUI = new CustomerLicenseGUI(this.customer, bankAccount);
            customerLicenseGUI.setBounds(0, 0, 800, 600);
            customerLicenseGUI.setResizable(false);
            customerLicenseGUI.setLocationRelativeTo(null);
            customerLicenseGUI.setVisible(true);
        }
    }
}
