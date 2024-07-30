package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.model.enums.OperationType;
import ar.edu.utn.tup.service.BankAccountService;
import ar.edu.utn.tup.service.MovementsService;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.criteria.CriteriaBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class CustomerLicenseGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelFooter;
    private JButton btnDeposit, btnWithdrawal, btnTransfer, btnInfo, btnMovement, btnDeleteAccount, btnBack;
    private Color background = new Color(18,18,18);
    Customer customer;
    BankAccount bankAccount;

    public CustomerLicenseGUI(Customer customer, BankAccount bankAccount) {
        this.customer = customer;
        this.bankAccount = bankAccount;
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MDCompany");
        setIconImage(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png"). getImage());
        getContentPane().setBackground(background);

        initComponents();
    }

    private void initComponents() {
        ImageIcon systemIcon = new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png");
        Icon icon = new ImageIcon(systemIcon.getImage().getScaledInstance(70,70, Image.SCALE_DEFAULT));
        labelIcon = new JLabel(icon);
        labelIcon.setBounds(350,50,70,70);
        this.repaint();
        add(labelIcon);

        btnDeleteAccount = createRoundButton("deleteAccountIcon.png", 220, 200,80,80, "DeleteAccount");
        add(btnDeleteAccount);
        btnDeleteAccount.addActionListener(this);

        btnMovement = createRoundButton("movementsIcon.png", 350, 200, 80, 80, "Movements");
        add(btnMovement);
        btnMovement.addActionListener(this);

        btnInfo = createRoundButton(OperationType.CHECK_BALANCE.getRoute(), 480,200,80,80, "Account Details");
        add(btnInfo);
        btnInfo.addActionListener(this);

        btnDeposit = createRoundButton(OperationType.MONEY_DEPOSIT.getRoute(), 220, 350,80,80, "Investment");
        add(btnDeposit);
        btnDeposit.addActionListener(this);

        btnWithdrawal = createRoundButton(OperationType.MONEY_WITHDRAWAL.getRoute(), 350,350,80,80, "Withdrawall");
        add(btnWithdrawal);
        btnWithdrawal.addActionListener(this);

        btnTransfer = createRoundButton(OperationType.MONEY_TRANSFER.getRoute(), 480,350,80,80, "Transfer");
        add(btnTransfer);
        btnTransfer.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setToolTipText("Back");
        btnBack.setBounds(630,450,100,30);
        btnBack.setBackground(new Color(0,120,255));
        btnBack.setForeground(new Color(0,0,0));
        btnBack.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnBack);
        btnBack.addActionListener(this);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(305, 500, 210, 20);
        labelFooter.setForeground(new Color(255,255,255));
        add(labelFooter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDeleteAccount) {
            int response = JOptionPane.showConfirmDialog(null, "You are one step away from deleting your bank account. \nDo you wish to continue?", "Drop request", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                JPasswordField passwordFieldVerification = new JPasswordField();
                int okCancel = JOptionPane.showConfirmDialog(null, passwordFieldVerification, "Enter the bank account security code:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (okCancel == JOptionPane.OK_OPTION) {
                    String securityCode = new String(passwordFieldVerification.getPassword());

                    if (!securityCode.isEmpty()) {
                        if (securityCode.equals(bankAccount.getSecurityCode())) {
                            BankAccountService bankAccountService = new BankAccountService();

                            JOptionPane.showMessageDialog(this, "Successful drop request.", "Deleting...", JOptionPane.INFORMATION_MESSAGE);

                            bankAccount.setAccountState(false);

                            bankAccountService.update(bankAccount);

                            this.dispose();
                            CustomerAccountsGUI customerAccountsGUI = new CustomerAccountsGUI(this.customer);
                            customerAccountsGUI.setBounds(0,0,800,600);
                            customerAccountsGUI.setResizable(false);
                            customerAccountsGUI.setLocationRelativeTo(null);
                            customerAccountsGUI.setVisible(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(this, "Access denied", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Security code is required to proceed.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Security code is required to proceed.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        if (e.getSource() == btnMovement) {
            this.dispose();
            MovementsGUI movementsGUI = new MovementsGUI(this.customer, this.bankAccount);
            movementsGUI.setBounds(0,0,800,600);
            movementsGUI.setResizable(false);
            movementsGUI.setLocationRelativeTo(null);
            movementsGUI.setVisible(true);
        }
        if (e.getSource() == btnInfo) {
            this.dispose();
            AccountDetailGUI accountDetailGUI = new AccountDetailGUI(this.customer, this.bankAccount);
            accountDetailGUI.setBounds(0,0,800,600);
            accountDetailGUI.setResizable(false);
            accountDetailGUI.setLocationRelativeTo(null);
            accountDetailGUI.setVisible(true);
        }
        if (e.getSource() == btnDeposit) {
            this.dispose();
            InvestmentGUI investmentGUI = new InvestmentGUI(this.customer, this.bankAccount);
            investmentGUI.setBounds(0,0,800,600);
            investmentGUI.setResizable(false);
            investmentGUI.setLocationRelativeTo(null);
            investmentGUI.setVisible(true);
        }
        if (e.getSource() == btnWithdrawal) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel labelBalance = new JLabel("Current balance: $" + this.bankAccount.getBalance());
            panel.add(labelBalance);

            panel.add(Box.createVerticalStrut(10));

            JLabel labelAmount = new JLabel("Enter the amount you want to withdraw:");
            panel.add(labelAmount);

            panel.add(Box.createVerticalStrut(5));

            JTextField textfieldAmount = new JTextField();
            textfieldAmount.setPreferredSize(new Dimension(50,30));
            panel.add(textfieldAmount);

            int okCancel = JOptionPane.showConfirmDialog(null, panel, "Withdrawal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (okCancel == JOptionPane.OK_OPTION) {
                try {
                    double amount = Double.parseDouble(textfieldAmount.getText());

                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(this, "The amount must be greater than zero", "Invalid amount", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    MovementsService movementsService = new MovementsService();

                    boolean success = movementsService.withdraw(this.bankAccount, amount);

                    if (success) {
                        JOptionPane.showMessageDialog(this, "Withdrawal successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        textfieldAmount.setText(String.valueOf(this.bankAccount.getBalance()));
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Withdrawal failed. Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount entered. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == btnTransfer) {
            this.dispose();
            TransferMovementGUI transferMovementGUI = new TransferMovementGUI(this.customer, this.bankAccount);
            transferMovementGUI.setBounds(0,0,800,600);
            transferMovementGUI.setResizable(false);
            transferMovementGUI.setLocationRelativeTo(null);
            transferMovementGUI.setVisible(true);
        }
        if (e.getSource() == btnBack) {
            this.dispose();
            CustomerAccountsGUI customerAccountsGUI = new CustomerAccountsGUI(this.customer);
            customerAccountsGUI.setBounds(0,0,800,600);
            customerAccountsGUI.setResizable(false);
            customerAccountsGUI.setLocationRelativeTo(null);
            customerAccountsGUI.setVisible(true);
        }
    }

    private JButton createRoundButton(String iconPath, int x, int y, int width, int height, String toolTipText) {
        JButton button = new JButton(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\" + iconPath)) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(new Color(0,120,255));
                } else {
                    g.setColor(getBackground());
                }
                g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(getForeground());
                g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
            }

            @Override
            public boolean contains(int x, int y) {
                Ellipse2D shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
                return shape.contains(x, y);
            }
        };

        button.setPreferredSize(new Dimension(width, height));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(0,120,255));
        button.setToolTipText(toolTipText);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255,255,255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0,120,255));
            }
        });

        return button;
    }

}
