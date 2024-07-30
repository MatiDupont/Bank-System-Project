package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.model.enums.BankingEntities;
import ar.edu.utn.tup.service.MovementsService;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

public class InvestmentGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelTerm, labelInterest, labelBalance, labelAmount, labelMaturedAmount, labelFooter;
    private JComboBox<String> cmbFixedTerm;
    private JTextField textFieldInterest, textFieldBalance, textFieldAmount, textFieldMaturedAmount;
    private JButton btnInvest, btnBack;
    private Color background = new Color(18,18,18);
    private Map<String, Double> interestRates;
    Customer customer;
    BankAccount bankAccount;
    public InvestmentGUI(Customer customer, BankAccount bankAccount) {
        this.customer = customer;
        this.bankAccount = bankAccount;
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Create an investment");
        setIconImage(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png").getImage());
        getContentPane().setBackground(background);

        for (BankingEntities bankingEntities : BankingEntities.values()) {
            this.interestRates = bankingEntities.getInterestRates();
        }

        initComponents();
    }

    private void initComponents() {
        ImageIcon systemIcon = new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png");
        Icon icon = new ImageIcon(systemIcon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        labelIcon = new JLabel(icon);
        labelIcon.setBounds(350, 50, 70, 70);
        this.repaint();
        add(labelIcon);

        labelTerm = new JLabel("Fixed term:");
        labelTerm.setBounds(50, 140, 100, 20);
        labelTerm.setForeground(new Color(255, 255, 255));
        labelTerm.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelTerm);

        cmbFixedTerm = new JComboBox<>();
        cmbFixedTerm.setBounds(50, 170, 200, 30);
        cmbFixedTerm.setBackground(new Color(0, 120, 255));
        cmbFixedTerm.setForeground(new Color(0, 0, 0));
        cmbFixedTerm.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        cmbFixedTerm.setFont(new Font("DialogInput", Font.BOLD, 18));
        cmbFixedTerm.addItem("-");
        cmbFixedTerm.addItem("1 month");
        cmbFixedTerm.addItem("3 months");
        cmbFixedTerm.addItem("6 months");
        cmbFixedTerm.addItem("12 months");
        cmbFixedTerm.addActionListener(this);
        add(cmbFixedTerm);

        labelInterest = new JLabel("Interest Rate:");
        labelInterest.setBounds(530, 140, 100, 20);
        labelInterest.setForeground(new Color(255, 255, 255));
        labelInterest.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelInterest);

        textFieldInterest = new JTextField();
        textFieldInterest.setBounds(530, 170, 200, 30);
        textFieldInterest.setBackground(new Color(0, 120, 255));
        textFieldInterest.setForeground(new Color(0, 0, 0));
        textFieldInterest.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldInterest.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldInterest.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldInterest.setEnabled(false);
        add(textFieldInterest);

        labelBalance = new JLabel("Balance:");
        labelBalance.setBounds(290, 240, 100, 20);
        labelBalance.setForeground(new Color(255, 255, 255));
        labelBalance.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelBalance);

        textFieldBalance = new JTextField(String.valueOf(this.bankAccount.getBalance()));
        textFieldBalance.setBounds(290, 270, 200, 30);
        textFieldBalance.setBackground(new Color(0, 120, 255));
        textFieldBalance.setForeground(new Color(0, 0, 0));
        textFieldBalance.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldBalance.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldBalance.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldBalance.setEnabled(false);
        add(textFieldBalance);

        labelAmount = new JLabel("Amount to invest:");
        labelAmount.setBounds(50, 340, 120, 20);
        labelAmount.setForeground(new Color(255, 255, 255));
        labelAmount.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelAmount);

        textFieldAmount = new JTextField();
        textFieldAmount.setBounds(50, 370, 200, 30);
        textFieldAmount.setBackground(new Color(0, 120, 255));
        textFieldAmount.setForeground(new Color(0, 0, 0));
        textFieldAmount.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldAmount.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldAmount.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldAmount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateMaturedAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateMaturedAmount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateMaturedAmount();
            }
        });
        add(textFieldAmount);

        labelMaturedAmount = new JLabel("Matured amount:");
        labelMaturedAmount.setBounds(530,340,120,20);
        labelMaturedAmount.setForeground(new Color(255,255,255));
        labelMaturedAmount.setFont(new Font("Serif",Font.PLAIN,15));
        add(labelMaturedAmount);

        textFieldMaturedAmount = new JTextField();
        textFieldMaturedAmount.setBounds(530,370,200,30);
        textFieldMaturedAmount.setBackground(new Color(0,120,255));
        textFieldMaturedAmount.setForeground(new Color(0,0,0));
        textFieldMaturedAmount.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldMaturedAmount.setFont(new Font("DialogInput",Font.BOLD,18));
        textFieldMaturedAmount.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldMaturedAmount.setEnabled(false);
        add(textFieldMaturedAmount);

        btnInvest = new JButton("Invest");
        btnInvest.setToolTipText("Make new investment");
        btnInvest.setBounds(260,455,100,30);
        btnInvest.setBackground(new Color(0,120,255));
        btnInvest.setForeground(new Color(0,0,0));
        btnInvest.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnInvest);
        btnInvest.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setToolTipText("Back");
        btnBack.setBounds(415,455,100,30);
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

    private void updateMaturedAmount() {
        String selectedTerm = (String) cmbFixedTerm.getSelectedItem();
        String amountText = textFieldAmount.getText();

        if (!Objects.equals(selectedTerm, "-") && interestRates.containsKey(selectedTerm) && !amountText.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountText);
                double interestRate = interestRates.get(selectedTerm);
                double maturedAmount = amount + (amount * (interestRate / 100));

                textFieldMaturedAmount.setText(String.format("%.2f", maturedAmount));
            }
            catch (NumberFormatException ex) {
                textFieldMaturedAmount.setText("");
            }
        }
        else {
            textFieldMaturedAmount.setText("");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmbFixedTerm) {
            String selectedTerm = (String) cmbFixedTerm.getSelectedItem();
            if (interestRates.containsKey(selectedTerm)) {
                textFieldInterest.setText(String.format("%.2f", interestRates.get(selectedTerm)));
            }
            updateMaturedAmount();
        }
        if (e.getSource() == btnBack) {
            this.dispose();
            CustomerLicenseGUI customerLicenseGUI = new CustomerLicenseGUI(this.customer, bankAccount);
            customerLicenseGUI.setBounds(0, 0, 800, 600);
            customerLicenseGUI.setResizable(false);
            customerLicenseGUI.setLocationRelativeTo(null);
            customerLicenseGUI.setVisible(true);
        }
        if (e.getSource() == btnInvest) {
            if (textFieldAmount.getText().equals("") || Objects.equals(cmbFixedTerm.getSelectedItem(), "-")) {
                JOptionPane.showMessageDialog(null, "You must fill out all the fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                textFieldAmount.setText("");
            }
            else {
                String amountText = textFieldAmount.getText().replace(",", ".");
                String interestRateText = textFieldInterest.getText().replace(",", ".");

                double amount = Double.parseDouble(amountText);
                double interestRate = Double.parseDouble(interestRateText);

                LocalDate startDate = LocalDate.now();
                LocalDate endDate = calculateEndDate(startDate, (String) Objects.requireNonNull(cmbFixedTerm.getSelectedItem()));

                MovementsService movementsService = new MovementsService();

                boolean success = movementsService.invest(this.bankAccount, amount, interestRate, startDate, endDate);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Invest success");
                    cmbFixedTerm.setEnabled(false);
                    textFieldAmount.setEnabled(false);
                    btnInvest.setEnabled(false);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Insufficient balance to make the investment", "Error", JOptionPane.ERROR_MESSAGE);
                    textFieldAmount.setText("");
                }
            }
        }
    }

    private LocalDate calculateEndDate(LocalDate startDate, String term) {
        switch (term) {
            case "1 month":
                return startDate.plus(1, ChronoUnit.MONTHS);
            case "3 months":
                return startDate.plus(3, ChronoUnit.MONTHS);
            case "6 months":
                return startDate.plus(6, ChronoUnit.MONTHS);
            case "12 months":
                return startDate.plus(12, ChronoUnit.MONTHS);
            default:
                return startDate;
        }
    }
}
