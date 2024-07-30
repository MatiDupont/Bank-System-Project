package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.service.BankAccountService;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountDetailGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelBalance, labelAccountState, labelBankEntity, labelCBU, labelAlias, labelAccountNumber, labelSecurityCode, labelAccountType, labelCoin, labelOpeningDate, labelFooter;
    private JTextField textFieldBalance, textFieldAccountState, textFieldCBU, textFieldAlias, textFieldAccountNumber, textFieldSecurityCode, textFieldOpeningDate, textFieldAccountType, textFieldCoin, textFieldBankEntity;
    private JButton btnBack, btnEdit, btnSave;
    private Color background = new Color(18, 18, 18);
    Customer customer;
    BankAccount bankAccount;

    public AccountDetailGUI(Customer customer, BankAccount bankAccount) {
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
        Icon icon = new ImageIcon(systemIcon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        labelIcon = new JLabel(icon);
        labelIcon.setBounds(350, 50, 70, 70);
        this.repaint();
        add(labelIcon);

        labelAccountState = new JLabel("Account State:");
        labelAccountState.setBounds(50,70,100,20);
        labelAccountState.setForeground(new Color(255,255,255));
        labelAccountState.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelAccountState);

        textFieldAccountState = new JTextField(String.valueOf(bankAccount.isAccountState()));
        textFieldAccountState.setBounds(50,100,200,30);
        textFieldAccountState.setBackground(new Color(0,120,255));
        textFieldAccountState.setForeground(new Color(0,0,0));
        textFieldAccountState.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldAccountState.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldAccountState.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldAccountState);
        textFieldAccountState.setEnabled(false);

        labelBalance = new JLabel("Balance:");
        labelBalance.setBounds(530,70,100,20);
        labelBalance.setForeground(new Color(255,255,255));
        labelBalance.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelBalance);

        textFieldBalance = new JTextField(String.valueOf(bankAccount.getBalance()));
        textFieldBalance.setBounds(530,100,200,30);
        textFieldBalance.setBackground(new Color(0,120,255));
        textFieldBalance.setForeground(new Color(0,0,0));
        textFieldBalance.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldBalance.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldBalance.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldBalance);
        textFieldBalance.setEnabled(false);

        labelBankEntity = new JLabel("Bank Entity:");
        labelBankEntity.setBounds(50, 140, 100, 20);
        labelBankEntity.setForeground(new Color(255, 255, 255));
        labelBankEntity.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelBankEntity);

        textFieldBankEntity = new JTextField(bankAccount.getBankingEntities().name());
        textFieldBankEntity.setBounds(50, 170, 200, 30);
        textFieldBankEntity.setBackground(new Color(0, 120, 255));
        textFieldBankEntity.setForeground(new Color(0, 0, 0));
        textFieldBankEntity.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldBankEntity.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldBankEntity.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldBankEntity);
        textFieldBankEntity.setEnabled(false);

        labelCoin = new JLabel("Coin:");
        labelCoin.setBounds(530, 140, 100, 20);
        labelCoin.setForeground(new Color(255, 255, 255));
        labelCoin.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelCoin);

        textFieldCoin = new JTextField(bankAccount.getCoin().name());
        textFieldCoin.setBounds(530, 170, 200, 30);
        textFieldCoin.setBackground(new Color(0, 120, 255));
        textFieldCoin.setForeground(new Color(0, 0, 0));
        textFieldCoin.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldCoin.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldCoin.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldCoin);
        textFieldCoin.setEnabled(false);

        labelAccountNumber = new JLabel("Account Number:");
        labelAccountNumber.setBounds(50, 210, 120, 20);
        labelAccountNumber.setForeground(new Color(255, 255, 255));
        labelAccountNumber.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelAccountNumber);

        textFieldAccountNumber = new JTextField(bankAccount.getAccountNumber());
        textFieldAccountNumber.setBounds(50, 240, 200, 30);
        textFieldAccountNumber.setBackground(new Color(0, 120, 255));
        textFieldAccountNumber.setForeground(new Color(0, 0, 0));
        textFieldAccountNumber.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldAccountNumber.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldAccountNumber.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldAccountNumber);
        textFieldAccountNumber.setEnabled(false);

        labelCBU = new JLabel("CBU:");
        labelCBU.setBounds(530, 210, 100, 20);
        labelCBU.setForeground(new Color(255, 255, 255));
        labelCBU.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelCBU);

        textFieldCBU = new JTextField(bankAccount.getCBU());
        textFieldCBU.setBounds(530, 240, 200, 30);
        textFieldCBU.setBackground(new Color(0, 120, 255));
        textFieldCBU.setForeground(new Color(0, 0, 0));
        textFieldCBU.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldCBU.setFont(new Font("DialogInput", Font.BOLD, 14));
        textFieldCBU.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldCBU);
        textFieldCBU.setEnabled(false);

        labelAlias = new JLabel("Alias:");
        labelAlias.setBounds(50, 280, 100, 20);
        labelAlias.setForeground(new Color(255, 255, 255));
        labelAlias.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelAlias);

        textFieldAlias = new JTextField(bankAccount.getAlias());
        textFieldAlias.setBounds(50, 310, 200, 30);
        textFieldAlias.setBackground(new Color(0, 120, 255));
        textFieldAlias.setForeground(new Color(0, 0, 0));
        textFieldAlias.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldAlias.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldAlias.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldAlias);
        textFieldAlias.setEnabled(false);

        labelSecurityCode = new JLabel("Security Code:");
        labelSecurityCode.setBounds(530, 280, 120, 20);
        labelSecurityCode.setForeground(new Color(255, 255, 255));
        labelSecurityCode.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelSecurityCode);

        textFieldSecurityCode = new JTextField(bankAccount.getSecurityCode());
        textFieldSecurityCode.setBounds(530, 310, 200, 30);
        textFieldSecurityCode.setBackground(new Color(0, 120, 255));
        textFieldSecurityCode.setForeground(new Color(0, 0, 0));
        textFieldSecurityCode.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldSecurityCode.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldSecurityCode.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldSecurityCode);
        textFieldSecurityCode.setEnabled(false);

        labelAccountType = new JLabel("Account type:");
        labelAccountType.setBounds(50, 350, 100, 20);
        labelAccountType.setForeground(new Color(255, 255, 255));
        labelAccountType.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelAccountType);

        textFieldAccountType = new JTextField(bankAccount.getAccountType().name());
        textFieldAccountType.setBounds(50, 380, 200, 30);
        textFieldAccountType.setBackground(new Color(0, 120, 255));
        textFieldAccountType.setForeground(new Color(0, 0, 0));
        textFieldAccountType.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldAccountType.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldAccountType.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldAccountType);
        textFieldAccountType.setEnabled(false);

        labelOpeningDate = new JLabel("Opening Date");
        labelOpeningDate.setBounds(530, 350, 120, 20);
        labelOpeningDate.setForeground(new Color(255, 255, 255));
        labelOpeningDate.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelOpeningDate);

        textFieldOpeningDate = new JTextField(formatDate(bankAccount.getOpeningDate()));
        textFieldOpeningDate.setBounds(530, 380, 200, 30);
        textFieldOpeningDate.setBackground(new Color(0, 120, 255));
        textFieldOpeningDate.setForeground(new Color(0, 0, 0));
        textFieldOpeningDate.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldOpeningDate.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldOpeningDate.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldOpeningDate);
        textFieldOpeningDate.setEnabled(false);

        btnEdit = new JButton("Edit");
        btnEdit.setToolTipText("Edit bank account");
        btnEdit.setBounds(180, 455, 100, 30);
        btnEdit.setBackground(new Color(0, 120, 255));
        btnEdit.setForeground(new Color(0, 0, 0));
        btnEdit.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnEdit);
        btnEdit.addActionListener(this);

        btnSave = new JButton("Save");
        btnSave.setToolTipText("Save data");
        btnSave.setBounds(340,455,100,30);
        btnSave.setBackground(new Color(0,120,255));
        btnSave.setForeground(new Color(0,0,0));
        btnSave.setFont(new Font("Calibri", Font.BOLD, 18));
        btnSave.setEnabled(false);
        add(btnSave);
        btnSave.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setToolTipText("Back");
        btnBack.setBounds(500, 455, 100, 30);
        btnBack.setBackground(new Color(0, 120, 255));
        btnBack.setForeground(new Color(0, 0, 0));
        btnBack.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnBack);
        btnBack.addActionListener(this);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(312, 500, 210, 20);
        labelFooter.setForeground(new Color(255, 255, 255));
        add(labelFooter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEdit) {
            textFieldAlias.setEnabled(true);
            textFieldSecurityCode.setEnabled(true);
        }
        if (e.getSource() == btnSave) {
            String alias = textFieldAlias.getText();
            String password = textFieldSecurityCode.getText();

            bankAccount.setAlias(alias);
            bankAccount.setSecurityCode(password);

            BankAccountService bankAccountService = new BankAccountService();

            bankAccountService.update(bankAccount);

            JOptionPane.showMessageDialog(this, "Bank account updated successfully.", "Update", JOptionPane.INFORMATION_MESSAGE);

            textFieldAlias.setEnabled(false);
            textFieldSecurityCode.setEnabled(false);
        }
        if (e.getSource() == btnBack) {
            this.dispose();
            CustomerLicenseGUI customerLicenseGUI = new CustomerLicenseGUI(this.customer, this.bankAccount);
            customerLicenseGUI.setBounds(0,0,800,600);
            customerLicenseGUI.setResizable(false);
            customerLicenseGUI.setLocationRelativeTo(null);
            customerLicenseGUI.setVisible(true);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(date);
    }
}

