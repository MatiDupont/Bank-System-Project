package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.service.ValidatorService;
import ar.edu.utn.tup.service.CustomerService;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateCustomerGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelName, labelLastName, labelAddress, labelTelephone, labelEmail, labelNID, labelBirthday, labelPassword, labelFooter;
    private JTextField textFieldName, textFieldLastName, textFieldAddress, textFieldTelephone, textFieldEmail, textFieldNID, textFieldBirthday;
    private JPasswordField passwordField;
    private JButton btnBrowse, btnCreate, btnBack;
    private Color background = new Color(18,18,18);
    private String imagePath;

    public CreateCustomerGUI() {
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MDCompany");
        setIconImage(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png").getImage());
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

        labelName = new JLabel("Name:");
        labelName.setBounds(50,140,100,20);
        labelName.setForeground(new Color(255,255,255));
        labelName.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelName);

        textFieldName = new JTextField();
        textFieldName.setToolTipText("John Rick");
        textFieldName.setBounds(50,170,200,30);
        textFieldName.setBackground(new Color(0,120,255));
        textFieldName.setForeground(new Color(0,0,0));
        textFieldName.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldName.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldName.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldName);

        textFieldName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!ValidatorService.isValidName(textFieldName.getText())) {
                    textFieldName.setBackground(Color.red);
                }
                else {
                    textFieldName.setBackground(new Color(0,120,255));
                }
            }
        });

        labelNID = new JLabel("NID:");
        labelNID.setBounds(290,140,100,20);
        labelNID.setForeground(new Color(255,255,255));
        labelNID.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelNID);

        textFieldNID = new JTextField();
        textFieldNID.setToolTipText("xxxxxxxx");
        textFieldNID.setBounds(290,170,200,30);
        textFieldNID.setBackground(new Color(0,120,255));
        textFieldNID.setForeground(new Color(0,0,0));
        textFieldNID.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldNID.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldNID.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldNID);

        textFieldNID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!ValidatorService.isValidNID(textFieldNID.getText())) {
                    textFieldNID.setBackground(Color.red);
                }
                else {
                    textFieldNID.setBackground(new Color(0,120,255));
                }
            }
        });

        labelLastName = new JLabel("LastName:");
        labelLastName.setBounds(530, 140,100,20);
        labelLastName.setForeground(new Color(255,255,255));
        labelLastName.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelLastName);

        textFieldLastName = new JTextField();
        textFieldLastName.setToolTipText("Doe Nichols");
        textFieldLastName.setBounds(530,170,200,30);
        textFieldLastName.setBackground(new Color(0,120,255));
        textFieldLastName.setForeground(new Color(0,0,0));
        textFieldLastName.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldLastName.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldLastName.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldLastName);

        textFieldLastName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!ValidatorService.isValidName(textFieldLastName.getText())) {
                    textFieldLastName.setBackground(Color.red);
                }
                else {
                    textFieldLastName.setBackground(new Color(0,120,255));
                }
            }
        });

        labelAddress = new JLabel("Address:");
        labelAddress.setBounds(50, 240,100,20);
        labelAddress.setForeground(new Color(255,255,255));
        labelAddress.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelAddress);

        textFieldAddress = new JTextField();
        textFieldAddress.setToolTipText("Ex Example 111");
        textFieldAddress.setBounds(50,270,200,30);
        textFieldAddress.setBackground(new Color(0,120,255));
        textFieldAddress.setForeground(new Color(0,0,0));
        textFieldAddress.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldAddress.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldAddress.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldAddress);

        textFieldAddress.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!ValidatorService.isValidAddress(textFieldAddress.getText())) {
                    textFieldAddress.setBackground(Color.red);
                }
                else {
                    textFieldAddress.setBackground(new Color(0,120,255));
                }
            }
        });

        labelTelephone = new JLabel("Telephone Number:");
        labelTelephone.setBounds(530, 240,200,20);
        labelTelephone.setForeground(new Color(255,255,255));
        labelTelephone.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelTelephone);

        textFieldTelephone = new JTextField();
        textFieldTelephone.setToolTipText("+xx x xxx xxxxxxx");
        textFieldTelephone.setBounds(530,270,200,30);
        textFieldTelephone.setBackground(new Color(0,120,255));
        textFieldTelephone.setForeground(new Color(0,0,0));
        textFieldTelephone.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldTelephone.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldTelephone.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldTelephone);

        textFieldTelephone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!ValidatorService.isValidTelephone(textFieldTelephone.getText())) {
                    textFieldTelephone.setBackground(Color.red);
                }
                else {
                    textFieldTelephone.setBackground(new Color(0,120,255));
                }
            }
        });

        labelEmail= new JLabel("Email:");
        labelEmail.setBounds(50, 340,100,20);
        labelEmail.setForeground(new Color(255,255,255));
        labelEmail.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setToolTipText("test.user@example.com");
        textFieldEmail.setBounds(50,370,200,30);
        textFieldEmail.setBackground(new Color(0,120,255));
        textFieldEmail.setForeground(new Color(0,0,0));
        textFieldEmail.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldEmail.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldEmail);

        textFieldEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!ValidatorService.isValidEmail(textFieldEmail.getText())) {
                    textFieldEmail.setBackground(Color.red);
                }
                else {
                    textFieldEmail.setBackground(new Color(0,120,255));
                }
            }
        });

        labelPassword= new JLabel("Password:");
        labelPassword.setBounds(290, 340,100,20);
        labelPassword.setForeground(new Color(255,255,255));
        labelPassword.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelPassword);

        passwordField = new JPasswordField();
        passwordField.setToolTipText("Weak: abcyxz - Medium: 1abc23 - Strong: A1b2C3!@");
        passwordField.setBounds(290,370,200,30);
        passwordField.setBackground(new Color(0,120,255));
        passwordField.setForeground(new Color(0,0,0));
        passwordField.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        passwordField.setFont(new Font("DialogInput", Font.BOLD, 18));
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        add(passwordField);

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String strength = ValidatorService.getPasswordStrength(new String(passwordField.getPassword()));

                switch (strength) {
                    case "Strong":
                        passwordField.setBackground(Color.green);
                        passwordField.setToolTipText("Strong password");
                        break;
                    case "Medium":
                        passwordField.setBackground(Color.orange);
                        passwordField.setToolTipText("Medium password");
                        break;
                    case "Weak":
                        passwordField.setBackground(Color.red);
                        passwordField.setToolTipText("Weak password");
                        break;
                    default:
                        passwordField.setBackground(new Color(0,120,255));
                        passwordField.setToolTipText("Weak: abcyxz - Medium: 1abc23 - Strong: A1b2C3!@");
                        break;
                }
            }
        });

        labelBirthday= new JLabel("Birthday:");
        labelBirthday.setBounds(530, 340,100,20);
        labelBirthday.setForeground(new Color(255,255,255));
        labelBirthday.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelBirthday);

        textFieldBirthday = new JTextField();
        textFieldBirthday.setToolTipText("yyyy/MM/dd");
        textFieldBirthday.setBounds(530,370,200,30);
        textFieldBirthday.setBackground(new Color(0,120,255));
        textFieldBirthday.setForeground(new Color(0,0,0));
        textFieldBirthday.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldBirthday.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldBirthday.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldBirthday);

        btnCreate = new JButton("Create");
        btnCreate.setToolTipText("Create profile");
        btnCreate.setBounds(220,455,100,30);
        btnCreate.setBackground(new Color(0,120,255));
        btnCreate.setForeground(new Color(0,0,0));
        btnCreate.setFont(new Font("Calibri",Font.BOLD, 18));
        add(btnCreate);
        btnCreate.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setToolTipText("Back");
        btnBack.setBounds(460,455,100,30);
        btnBack.setBackground(new Color(0,120,255));
        btnBack.setForeground(new Color(0,0,0));
        btnBack.setFont(new Font("Calibri",Font.BOLD, 18));
        add(btnBack);
        btnBack.addActionListener(this);

        btnBrowse = new JButton("No image yet");
        btnBrowse.setBounds(330,225,120,100);
        btnBrowse.setBackground(new Color(0,120,255));
        btnBrowse.setForeground(new Color(0,0,0));
        btnBrowse.setFont(new Font("Calibri",Font.BOLD,15));
        add(btnBrowse);
        btnBrowse.addActionListener(this);

        // all rights reserved
        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(295,500,210,20);
        labelFooter.setForeground(new Color(255,255,255));
        add(labelFooter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            CustomerGUI customerGUI = new CustomerGUI();
            customerGUI.setBounds(0,0,800,600);
            customerGUI.setResizable(false);
            customerGUI.setLocationRelativeTo(null);
            customerGUI.setVisible(true);
            this.dispose();
        }
        if (e.getSource() == btnBrowse) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png");
                }

                @Override
                public String getDescription() {
                    return "Image Files (*.jpg, *.png)";
                }
            });
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath = selectedFile.getAbsolutePath();
                Image imageIcon = new ImageIcon(imagePath).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                Icon icon = new ImageIcon(imageIcon);
                btnBrowse.setText("");
                btnBrowse.setIcon(icon);
            }
        }
        if (e.getSource() == btnCreate) {
            int validation = 0;

            String name = textFieldName.getText().trim();
            String lastName = textFieldLastName.getText().trim();
            String address = textFieldAddress.getText().trim();
            String telephone = textFieldTelephone.getText().trim();
            String email = textFieldEmail.getText().trim();
            String NID = textFieldNID.getText().trim();
            String birthdayString = textFieldBirthday.getText().trim();
            String password = new String(passwordField.getPassword());
            String imagePath = this.imagePath;

            if (name.equals("")) {
                textFieldName.setBackground(Color.red);
                validation ++;
            }
            if (lastName.equals("")){
                textFieldLastName.setBackground(Color.red);
                validation ++;
            }
            if (address.equals("")){
                textFieldAddress.setBackground(Color.red);
                validation ++;
            }
            if (telephone.equals("")){
                textFieldTelephone.setBackground(Color.red);
                validation ++;
            }
            if (email.equals("")){
                textFieldEmail.setBackground(Color.red);
                validation ++;
            }
            if (NID.equals("")){
                textFieldNID.setBackground(Color.red);
                validation ++;
            }
            if (birthdayString.equals("")){
                textFieldBirthday.setBackground(Color.red);
                validation ++;
            }
            if (password.equals("")){
                passwordField.setBackground(Color.red);
                validation ++;
            }
            if (btnBrowse.getText().equals("No image yet")){
                btnBrowse.setBackground(Color.red);
                validation ++;
            }

            if (validation > 0) {
                JOptionPane.showMessageDialog(this, "You must fill out all the fields.");
                clean();
                return;
            }

            if (!ValidatorService.isValidName(name)) {
                JOptionPane.showMessageDialog(this, "Invalid name format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ValidatorService.isValidName(lastName)) {
                JOptionPane.showMessageDialog(this, "Invalid last name format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ValidatorService.isValidAddress(address)) {
                JOptionPane.showMessageDialog(this, "Invalid address format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ValidatorService.isValidDate(birthdayString)) {
                JOptionPane.showMessageDialog(this, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ValidatorService.isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ValidatorService.isValidNID(NID)) {
                JOptionPane.showMessageDialog(this, "Invalid NID format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ValidatorService.isValidTelephone(telephone)) {
                JOptionPane.showMessageDialog(this, "Invalid telephone number format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (ValidatorService.getPasswordStrength(password).equals("Invalid")) {
                JOptionPane.showMessageDialog(this, "Invalid password format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date birthday = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                formatter.setLenient(false);
                birthday = formatter.parse(birthdayString);
            }
            catch (ParseException ex) {
                return;
            }

            CustomerService customerService = new CustomerService();

            if (customerService.findCustomerByNID(NID) != null) {
                JOptionPane.showMessageDialog(this, "There is already a registered user with that NID. \nTry again or go back to login.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean customerCreated = customerService.createCustomer(name, lastName, address, telephone, email, NID, birthday, password, imagePath);

            if (customerCreated) {
                JOptionPane.showMessageDialog(this, "Customer profile created successfully!", "Create", JOptionPane.INFORMATION_MESSAGE);

                textFieldName.setEnabled(false);
                textFieldNID.setEnabled(false);
                textFieldLastName.setEnabled(false);
                textFieldAddress.setEnabled(false);
                textFieldTelephone.setEnabled(false);
                textFieldEmail.setEnabled(false);
                passwordField.setEnabled(false);
                textFieldBirthday.setEnabled(false);
                btnBrowse.setEnabled(false);
            }
            else {
                JOptionPane.showMessageDialog(this, "You must be of legal age to create an account.", "Create", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clean() {
        textFieldName.setBackground(new Color(0,120,255));
        textFieldLastName.setBackground(new Color(0,120,255));
        textFieldAddress.setBackground(new Color(0,120,255));
        textFieldTelephone.setBackground(new Color(0,120,255));
        textFieldEmail.setBackground(new Color(0,120,255));
        textFieldNID.setBackground(new Color(0,120,255));
        textFieldBirthday.setBackground(new Color(0,120,255));
        passwordField.setBackground(new Color(0,120,255));
        btnBrowse.setBackground(new Color(0,120,255));
    }
}
