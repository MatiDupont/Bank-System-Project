package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.model.enums.AccountType;
import ar.edu.utn.tup.model.enums.BankingEntities;
import ar.edu.utn.tup.model.enums.CoinType;
import ar.edu.utn.tup.service.BankAccountService;
import ar.edu.utn.tup.service.MovementsService;
import ar.edu.utn.tup.service.ValidatorService;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateNewBankAccountGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelBankEntity, labelAlias, labelSecurityCode, labelAccountType, labelCoin, labelOpeningDate, labelFooter;
    private JComboBox<BankingEntities> cmbBankEntity;
    private JComboBox<CoinType> cmbCoinType;
    private JComboBox<AccountType> cmbAccountType;
    private JTextField textFieldAlias, textFieldOpeningDate;
    private JPasswordField passwordFieldSecurityCode;
    private JButton btnBack, btnCreate;
    private Color background = new Color(18,18,18);
    Customer customer;

    public CreateNewBankAccountGUI(Customer customer) {
        this.customer = customer;
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

        labelBankEntity = new JLabel("Bank Entity:");
        labelBankEntity.setBounds(50,140,100,20);
        labelBankEntity.setForeground(new Color(255,255,255));
        labelBankEntity.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelBankEntity);

        cmbBankEntity = new JComboBox<>();
        cmbBankEntity.setBounds(50,170,200,35);
        cmbBankEntity.setBackground(new Color(0,120,255));
        cmbBankEntity.setForeground(new Color(0,0,0));
        cmbBankEntity.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        cmbBankEntity.setFont(new Font("DialogInput", Font.BOLD, 13));
        populateComboBox(cmbBankEntity, BankingEntities.values());
        add(cmbBankEntity);

        cmbBankEntity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankingEntities selectedBank = (BankingEntities) cmbBankEntity.getSelectedItem();

                if (selectedBank != null) {
                    String abbreviation = selectedBank.getAbbreviation();
                    textFieldAlias.setText(abbreviation);
                    textFieldAlias.setEnabled(true);
                }
            }
        });

        labelCoin = new JLabel("Coin:");
        labelCoin.setBounds(520,140,100,20);
        labelCoin.setForeground(new Color(255,255,255));
        labelCoin.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelCoin);

        cmbCoinType = new JComboBox<>();
        cmbCoinType.setBounds(520, 170,200,35);
        cmbCoinType.setBackground(new Color(0,120,255));
        cmbCoinType.setForeground(new Color(0,0,0));
        cmbCoinType.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        cmbCoinType.setFont(new Font("DialogInput", Font.BOLD, 15));
        populateComboBox(cmbCoinType, CoinType.values());
        add(cmbCoinType);

        labelAlias = new JLabel("Alias:");
        labelAlias.setBounds(50,250,100,20);
        labelAlias.setForeground(new Color(255,255,255));
        labelAlias.setFont(new Font("Serif",Font.PLAIN,15));
        add(labelAlias);

        textFieldAlias = new JTextField();
        textFieldAlias.setBounds(50,280,200,30);
        textFieldAlias.setBackground(new Color(0,120,255));
        textFieldAlias.setForeground(new Color(0,0,0));
        textFieldAlias.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldAlias.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldAlias.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldAlias.setEnabled(false);
        add(textFieldAlias);

        textFieldAlias.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update() {
                String currentText = textFieldAlias.getText();
                BankingEntities selectedBank = (BankingEntities) cmbBankEntity.getSelectedItem();

                if (selectedBank != null) {
                    String abbreviation = selectedBank.getAbbreviation();

                    if (currentText.endsWith(abbreviation)) {
                        String userInput = currentText.replace(abbreviation, "");

                        if (ValidatorService.isValidAlias(userInput)) {
                            textFieldAlias.setBackground(new Color(0,120,255));
                        }
                        else {
                            textFieldAlias.setBackground(Color.red);
                        }
                    }
                    else {
                        textFieldAlias.setBackground(Color.red);
                    }
                }
            }

        });

        labelSecurityCode = new JLabel("Security Code:");
        labelSecurityCode.setBounds(520,250,120,20);
        labelSecurityCode.setForeground(new Color(255,255,255));
        labelSecurityCode.setFont(new Font("Serif",Font.PLAIN, 15));
        add(labelSecurityCode);

        passwordFieldSecurityCode = new JPasswordField();
        passwordFieldSecurityCode.setToolTipText("4-6 digits");
        passwordFieldSecurityCode.setBounds(520,280,200,30);
        passwordFieldSecurityCode.setBackground(new Color(0,120,255));
        passwordFieldSecurityCode.setForeground(new Color(0,0,0));
        passwordFieldSecurityCode.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        passwordFieldSecurityCode.setFont(new Font("DialogInput", Font.BOLD, 18));
        passwordFieldSecurityCode.setHorizontalAlignment(SwingConstants.CENTER);
        add(passwordFieldSecurityCode);

        passwordFieldSecurityCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!ValidatorService.isValidSecurityCode(new String(passwordFieldSecurityCode.getPassword()))) {
                    passwordFieldSecurityCode.setBackground(Color.red);
                }
                else {
                    passwordFieldSecurityCode.setBackground(new Color(0,120,255));
                }
            }
        });

        labelAccountType = new JLabel("Account type:");
        labelAccountType.setBounds(50,350,100,20);
        labelAccountType.setForeground(new Color(255,255,255));
        labelAccountType.setFont(new Font("Serif", Font.PLAIN, 15));
        add(labelAccountType);

        cmbAccountType = new JComboBox<>();
        cmbAccountType.setBounds(50,380,200,30);
        cmbAccountType.setBackground(new Color(0,120,255));
        cmbAccountType.setForeground(new Color(0,0,0));
        cmbAccountType.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        cmbAccountType.setFont(new Font("DialogInput", Font.BOLD, 15));
        populateComboBox(cmbAccountType, AccountType.values());
        add(cmbAccountType);

        labelOpeningDate = new JLabel("Opening Date");
        labelOpeningDate.setBounds(520,350,120,20);
        labelOpeningDate.setForeground(new Color(255,255,255));
        labelOpeningDate.setFont(new Font("Serif",Font.PLAIN, 15));
        add(labelOpeningDate);

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = now.format(formatter);

        textFieldOpeningDate = new JTextField(formattedDate);
        textFieldOpeningDate.setBounds(520,380,200,30);
        textFieldOpeningDate.setBackground(new Color(0,120,255));
        textFieldOpeningDate.setForeground(new Color(0,0,0));
        textFieldOpeningDate.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldOpeningDate.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldOpeningDate.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldOpeningDate);
        textFieldOpeningDate.setEnabled(false);

        btnCreate = new JButton("Create");
        btnCreate.setToolTipText("Create new bank account");
        btnCreate.setBounds(255, 455,100,30);
        btnCreate.setBackground(new Color(0,120,255));
        btnCreate.setForeground(new Color(0,0,0));
        btnCreate.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnCreate);
        btnCreate.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setToolTipText("Back");
        btnBack.setBounds(410,455,100,30);
        btnBack.setBackground(new Color(0,120,255));
        btnBack.setForeground(new Color(0,0,0));
        btnBack.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnBack);
        btnBack.addActionListener(this);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(312,500,210,20);
        labelFooter.setForeground(new Color(255,255,255));
        add(labelFooter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            this.dispose();
            CustomerAccountsGUI customerAccountsGUI = new CustomerAccountsGUI(this.customer);
            customerAccountsGUI.setBounds(0,0,800,600);
            customerAccountsGUI.setResizable(false);
            customerAccountsGUI.setLocationRelativeTo(null);
            customerAccountsGUI.setVisible(true);
        }
        if (e.getSource() == btnCreate) {
            int validation = 0;

            BankingEntities bankEntity = (BankingEntities) cmbBankEntity.getSelectedItem();
            CoinType coin = (CoinType) cmbCoinType.getSelectedItem();
            String alias = textFieldAlias.getText();
            String securityCode = new String(passwordFieldSecurityCode.getPassword());
            AccountType accountType = (AccountType) cmbAccountType.getSelectedItem();

            if (bankEntity == BankingEntities.EMPTY) {
                cmbBankEntity.setBackground(Color.red);
                validation ++;
            }
            if (coin == CoinType.EMPTY) {
                cmbCoinType.setBackground(Color.red);
                validation ++;
            }
            if (alias.equals("")) {
                textFieldAlias.setBackground(Color.red);
                validation ++;
            }
            if (securityCode.equals("")) {
                passwordFieldSecurityCode.setBackground(Color.red);
                validation ++;
            }
            if (accountType == AccountType.EMPTY) {
                cmbAccountType.setBackground(Color.red);
                validation ++;
            }

            if (validation > 0) {
                JOptionPane.showMessageDialog(this, "You must fill out all the fields.");
                clean();
                return;
            }

            if (!ValidatorService.isValidSecurityCode(securityCode)) {
                JOptionPane.showMessageDialog(this, "Invalid security code format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ValidatorService.isValidSecurityCode(alias)) {
                JOptionPane.showMessageDialog(this, "Invalid alias format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BankAccountService bankAccountService = new BankAccountService();

            if (bankAccountService.getBankAccountByCBUAlias(alias) != null) {
                JOptionPane.showMessageDialog(this, "The selected alias is not available at this time. \nTry again.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            MovementsService movementsService = new MovementsService();

            BankAccount bankAccount = bankAccountService.create(bankEntity, alias, securityCode, accountType, this.customer, coin);

            if (bankAccount == null) {
                JOptionPane.showMessageDialog(this, "We are sorry!. You already have a bank account in this bank.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            movementsService.deposit(bankAccount, bankEntity.getBonusInit());

            JOptionPane.showMessageDialog(this, "Bank account created successfully!", "Create", JOptionPane.INFORMATION_MESSAGE);

            cmbBankEntity.setEnabled(false);
            cmbCoinType.setEnabled(false);
            textFieldAlias.setEnabled(false);
            passwordFieldSecurityCode.setEnabled(false);
            cmbAccountType.setEnabled(false);
            btnCreate.setEnabled(false);
        }
    }

    private <E extends Enum<E>> void populateComboBox(JComboBox<E> comboBox, E[] values) {
        for (E value : values) {
            comboBox.addItem(value);
        }
    }

    private void clean() {
        cmbBankEntity.setBackground(new Color(0,120,255));
        cmbCoinType.setBackground(new Color(0,120,255));
        textFieldAlias.setBackground(new Color(0,120,255));
        passwordFieldSecurityCode.setBackground(new Color(0,120,255));
        cmbAccountType.setBackground(new Color(0,120,255));
    }

    private interface SimpleDocumentListener extends javax.swing.event.DocumentListener {
        void update();

        @Override
        default void insertUpdate(DocumentEvent e) {
            update();
        }

        @Override
        default void removeUpdate(DocumentEvent e) {
            update();
        }

        @Override
        default void changedUpdate(DocumentEvent e) {
            update();
        }
    }
}
