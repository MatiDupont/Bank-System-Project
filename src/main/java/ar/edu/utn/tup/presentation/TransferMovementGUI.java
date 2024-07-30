package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.service.BankAccountService;
import ar.edu.utn.tup.service.MovementsService;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

public class TransferMovementGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelAmount, labelCBUAlias, labelBalance, labelFooter;
    private JTextField textFieldAmount, textFieldCBUAlias, textFieldBalance;
    private JButton btnTest, btnConfirm, btnBack;
    private Color background = new Color(18,18,18);
    Customer customer;
    BankAccount bankAccount;
    BankAccountService bankAccountService = new BankAccountService();

    public TransferMovementGUI(Customer customer, BankAccount bankAccount) {
        this.customer = customer;
        this.bankAccount = bankAccount;
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MDCompany");
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

        labelBalance = new JLabel("Your balance:");
        labelBalance.setBounds(345,150, 100,20);
        labelBalance.setForeground(new Color(255,255,255));
        labelBalance.setFont(new Font("Arial", Font.BOLD, 12));
        add(labelBalance);

        textFieldBalance = new JTextField(String.valueOf(this.bankAccount.getBalance()));
        textFieldBalance.setBounds(285,180,200,30);
        textFieldBalance.setBackground(new Color(0,120,255));
        textFieldBalance.setForeground(new Color(0,0,0));
        textFieldBalance.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldBalance.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldBalance.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldBalance.setEnabled(false);
        add(textFieldBalance);

        labelCBUAlias = new JLabel("Enter the CBU or destination alias:");
        labelCBUAlias.setBounds(285,230,220,20);
        labelCBUAlias.setForeground(new Color(255,255,255));
        labelCBUAlias.setFont(new Font("Arial",Font.BOLD, 12));
        add(labelCBUAlias);

        textFieldCBUAlias = new JTextField();
        textFieldCBUAlias.setBounds(285,260,200,30);
        textFieldCBUAlias.setBackground(new Color(0,120,255));
        textFieldCBUAlias.setForeground(new Color(0,0,0));
        textFieldCBUAlias.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldCBUAlias.setFont(new Font("DialogInput", Font.BOLD, 14));
        textFieldCBUAlias.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldCBUAlias);

        btnTest = new JButton("Test");
        btnTest.setToolTipText("Test destination");
        btnTest.setBounds(630,260,100,30);
        btnTest.setBackground(new Color(0,120,255));
        btnTest.setForeground(new Color(0,0,0));
        btnTest.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnTest);
        btnTest.addActionListener(this);

        labelAmount = new JLabel("Amount:");
        labelAmount.setBounds(357,310, 100,20);
        labelAmount.setForeground(new Color(255,255,255));
        labelAmount.setFont(new Font("Arial", Font.BOLD, 12));
        add(labelAmount);

        textFieldAmount = new JTextField();
        textFieldAmount.setBounds(285,340,200,30);
        textFieldAmount.setBackground(new Color(0,120,255));
        textFieldAmount.setForeground(new Color(0,0,0));
        textFieldAmount.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldAmount.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldAmount.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldAmount.setEnabled(false);
        add(textFieldAmount);

        btnConfirm = new JButton("Confirm");
        btnConfirm.setToolTipText("Confirm amount");
        btnConfirm.setBounds(630,340,100,30);
        btnConfirm.setBackground(new Color(0,120,255));
        btnConfirm.setForeground(new Color(0,0,0));
        btnConfirm.setFont(new Font("Calibri", Font.BOLD, 18));
        btnConfirm.setEnabled(false);
        add(btnConfirm);
        btnConfirm.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setToolTipText("Back");
        btnBack.setBounds(630,450,100,30);
        btnBack.setBackground(new Color(0,120,255));
        btnBack.setForeground(new Color(0,0,0));
        btnBack.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnBack);
        btnBack.addActionListener(this);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(310,500,210,20);
        labelFooter.setForeground(new Color(255,255,255));
        add(labelFooter);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            this.dispose();
            CustomerLicenseGUI customerLicenseGUI = new CustomerLicenseGUI(this.customer, bankAccount);
            customerLicenseGUI.setBounds(0, 0, 800, 600);
            customerLicenseGUI.setResizable(false);
            customerLicenseGUI.setLocationRelativeTo(null);
            customerLicenseGUI.setVisible(true);
        }
        if (e.getSource() == btnTest) {
            String CBUAlias = textFieldCBUAlias.getText();

            BankAccount ba = bankAccountService.getBankAccountByCBUAlias(CBUAlias);

            if (ba != null) {
                if (bankAccount.getCBU().equals(ba.getCBU()) || bankAccount.getAlias().equals(ba.getAlias())) {
                    JOptionPane.showMessageDialog(null, "It is not possible to transfer to your same bank account.", "Warning", JOptionPane.WARNING_MESSAGE);
                    textFieldCBUAlias.setText("");
                }
                else {
                    int option = JOptionPane.showConfirmDialog(null, "Information about the destination account: \n\n" +
                            "Name: " + ba.getCustomer().getName() + " " + ba.getCustomer().getLastName() + "\n" +
                            "NID: " + ba.getCustomer().getNID() + "\n" +
                            "BankEntity: " + ba.getBankingEntities().name() + "\n" +
                            "Alias: " + ba.getAlias() + "\n" +
                            "CBU: " + ba.getCBU(), "Confirm Destination", JOptionPane.OK_CANCEL_OPTION
                    );

                    if (option == JOptionPane.OK_OPTION) {
                        textFieldCBUAlias.setEnabled(false);
                        btnTest.setEnabled(false);
                        textFieldAmount.setEnabled(true);
                        btnConfirm.setEnabled(true);
                    }
                    else {
                        textFieldCBUAlias.setText("");
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Invalid CBU or alias", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == btnConfirm) {
            double amount = Double.parseDouble(textFieldAmount.getText());
            String CBUAlias = textFieldCBUAlias.getText();
            LocalDate now = LocalDate.now();

            BankAccount destinationAccount = bankAccountService.getBankAccountByCBUAlias(CBUAlias);

            MovementsService movementsService = new MovementsService();

            Integer success = movementsService.makeABankTransfer(this.bankAccount, destinationAccount, now, amount);

            if (success == 1) {
                JOptionPane.showMessageDialog(this, "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                textFieldBalance.setText(String.valueOf(bankAccount.getBalance()));
                textFieldAmount.setEnabled(false);
                btnConfirm.setEnabled(false);
            }
            else if (success == 2){
                JOptionPane.showMessageDialog(this, "Transfer failed. Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                textFieldCBUAlias.setEnabled(true);
                btnTest.setEnabled(true);
                textFieldAmount.setText("");
                textFieldAmount.setEnabled(false);
                btnConfirm.setEnabled(false);
            }
            else if (success == 3){
                JOptionPane.showMessageDialog(this, "Transfer failed. Destination account is inactive.", "Error", JOptionPane.ERROR_MESSAGE);
                textFieldCBUAlias.setEnabled(true);
                btnTest.setEnabled(true);
                textFieldAmount.setText("");
                textFieldAmount.setEnabled(false);
                btnConfirm.setEnabled(false);
            }
            else if (success == 4){
                JOptionPane.showMessageDialog(this, "Transfer failed. The transfer must be to an account with the same type of currency.", "Error", JOptionPane.ERROR_MESSAGE);
                textFieldCBUAlias.setEnabled(true);
                btnTest.setEnabled(true);
                textFieldAmount.setText("");
                textFieldAmount.setEnabled(false);
                btnConfirm.setEnabled(false);
            }
        }
    }
}
