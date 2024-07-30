package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.service.CustomerService;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileCustomerGUI extends JFrame implements ActionListener{
    private JLabel labelIcon, labelName, labelLastName, labelAddress, labelTelephone, labelEmail, labelNID, labelBirthday, labelPassword, labelFooter;
    private JTextField textFieldName, textFieldLastName, textFieldAddress, textFieldTelephone, textFieldEmail, textFieldNID, textFieldBirthday;
    private JPasswordField passwordField;
    private JButton btnEdit, btnSave, btnBack, btnBrowse, btnDelete;
    private Color background = new Color(18,18,18);
    Customer customer = new Customer();
    private String imagePath;

    public ProfileCustomerGUI(Customer customer) {
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

        textFieldName = new JTextField(customer.getName());
        textFieldName.setBounds(50,170,200,30);
        textFieldName.setBackground(new Color(0,120,255));
        textFieldName.setForeground(new Color(0,0,0));
        textFieldName.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldName.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldName.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldName.setEnabled(false);
        add(textFieldName);

        labelNID = new JLabel("NID:");
        labelNID.setBounds(290,140,100,20);
        labelNID.setForeground(new Color(255,255,255));
        labelNID.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelNID);

        textFieldNID = new JTextField(customer.getNID());
        textFieldNID.setBounds(290,170,200,30);
        textFieldNID.setBackground(new Color(0,120,255));
        textFieldNID.setForeground(new Color(0,0,0));
        textFieldNID.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldNID.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldNID.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNID.setEnabled(false);
        add(textFieldNID);

        labelLastName = new JLabel("LastName:");
        labelLastName.setBounds(530, 140,100,20);
        labelLastName.setForeground(new Color(255,255,255));
        labelLastName.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelLastName);

        textFieldLastName = new JTextField(customer.getLastName());
        textFieldLastName.setBounds(530,170,200,30);
        textFieldLastName.setBackground(new Color(0,120,255));
        textFieldLastName.setForeground(new Color(0,0,0));
        textFieldLastName.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldLastName.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldLastName.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldLastName.setEnabled(false);
        add(textFieldLastName);

        labelAddress = new JLabel("Adress:");
        labelAddress.setBounds(50, 240,100,20);
        labelAddress.setForeground(new Color(255,255,255));
        labelAddress.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelAddress);

        textFieldAddress = new JTextField(customer.getAddress());
        textFieldAddress.setToolTipText("Ubicacion 111");
        textFieldAddress.setBounds(50,270,200,30);
        textFieldAddress.setBackground(new Color(0,120,255));
        textFieldAddress.setForeground(new Color(0,0,0));
        textFieldAddress.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldAddress.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldAddress.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldAddress.setEnabled(false);
        add(textFieldAddress);

        labelTelephone = new JLabel("Telephone Number:");
        labelTelephone.setBounds(530, 240,200,20);
        labelTelephone.setForeground(new Color(255,255,255));
        labelTelephone.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelTelephone);

        textFieldTelephone = new JTextField(customer.getTelephoneNumber());
        textFieldTelephone.setBounds(530,270,200,30);
        textFieldTelephone.setBackground(new Color(0,120,255));
        textFieldTelephone.setForeground(new Color(0,0,0));
        textFieldTelephone.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldTelephone.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldTelephone.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldTelephone.setEnabled(false);
        add(textFieldTelephone);

        labelEmail= new JLabel("Email:");
        labelEmail.setBounds(50, 340,100,20);
        labelEmail.setForeground(new Color(255,255,255));
        labelEmail.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelEmail);

        textFieldEmail = new JTextField(customer.getEmail());
        textFieldEmail.setBounds(50,370,200,30);
        textFieldEmail.setBackground(new Color(0,120,255));
        textFieldEmail.setForeground(new Color(0,0,0));
        textFieldEmail.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldEmail.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldEmail.setEnabled(false);
        add(textFieldEmail);

        labelPassword= new JLabel("Password:");
        labelPassword.setBounds(290, 340,100,20);
        labelPassword.setForeground(new Color(255,255,255));
        labelPassword.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelPassword);

        passwordField = new JPasswordField(customer.getPassword());
        passwordField.setBounds(290,370,200,30);
        passwordField.setBackground(new Color(0,120,255));
        passwordField.setForeground(new Color(0,0,0));
        passwordField.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        passwordField.setFont(new Font("DialogInput", Font.BOLD, 18));
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setEnabled(false);
        add(passwordField);

        labelBirthday= new JLabel("Birthday:");
        labelBirthday.setBounds(530, 340,100,20);
        labelBirthday.setForeground(new Color(255,255,255));
        labelBirthday.setFont(new Font("Serif", Font.PLAIN,15));
        add(labelBirthday);

        textFieldBirthday = new JTextField(formatDate(customer.getBirthday()));
        textFieldBirthday.setToolTipText("YYYY-MM-DD");
        textFieldBirthday.setBounds(530,370,200,30);
        textFieldBirthday.setBackground(new Color(0,120,255));
        textFieldBirthday.setForeground(new Color(0,0,0));
        textFieldBirthday.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldBirthday.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldBirthday.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldBirthday.setEnabled(false);
        add(textFieldBirthday);

        // Baja de cliente
        btnDelete = new JButton("Delete");
        btnDelete.setToolTipText("Delete profile");
        btnDelete.setBounds(80,455,100,30);
        btnDelete.setBackground(new Color(0,120,255));
        btnDelete.setForeground(new Color(0,0,0));
        btnDelete.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnDelete);
        btnDelete.addActionListener(this);

        // Modificacion de cliente
        btnEdit = new JButton("Edit");
        btnEdit.setToolTipText("Edit profile");
        btnEdit.setBounds(250,455,100,30);
        btnEdit.setBackground(new Color(0,120,255));
        btnEdit.setForeground(new Color(0,0,0));
        btnEdit.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnEdit);
        btnEdit.addActionListener(this);

        btnSave = new JButton("Save");
        btnSave.setToolTipText("Save data");
        btnSave.setBounds(420,455,100,30);
        btnSave.setBackground(new Color(0,120,255));
        btnSave.setForeground(new Color(0,0,0));
        btnSave.setFont(new Font("Calibri", Font.BOLD, 18));
        btnSave.setEnabled(false);
        add(btnSave);
        btnSave.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setToolTipText("Back");
        btnBack.setBounds(590,455,100,30);
        btnBack.setBackground(new Color(0,120,255));
        btnBack.setForeground(new Color(0,0,0));
        btnBack.setFont(new Font("Calibri", Font.BOLD, 18));
        add(btnBack);
        btnBack.addActionListener(this);

        btnBrowse = new JButton();
        String imagePath = customer.getProfilePicturePath();
        Image imageIcon = new ImageIcon(imagePath).getImage().getScaledInstance(120,120,Image.SCALE_DEFAULT);
        Icon picture = new ImageIcon(imageIcon);
        btnBrowse.setIcon(picture);
        btnBrowse.setBounds(330,225,120,100);
        btnBrowse.setBackground(new Color(0,120,255));
        btnBrowse.setForeground(new Color(0,0,0));
        btnBrowse.setFont(new Font("Calibri", Font.BOLD, 15));
        btnBrowse.setEnabled(false);
        add(btnBrowse);
        btnBrowse.addActionListener(this);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(312, 500, 210, 20);
        labelFooter.setForeground(new Color(255,255,255));
        add(labelFooter);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBrowse) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(java.io.File file) {
                    if (file.isDirectory()) {
                        return true;
                    } else {
                        String filename = file.getName().toLowerCase();
                        return filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png") || filename.endsWith(".gif");
                    }
                }

                @Override
                public String getDescription() {
                    return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
                }
            });
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                imagePath = selectedFile.getAbsolutePath();
                Image imageIcon = new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                Icon icon = new ImageIcon(imageIcon);
                btnBrowse.setText("");
                btnBrowse.setIcon(icon);
            }
        }
        if (e.getSource() == btnDelete) {
            int response = JOptionPane.showConfirmDialog(null, "You are one step away from deleting your profile account. \nDo you wish to continue?", "Drop request", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "Successful drop request. \nThe bank was notified.", "Request in process", JOptionPane.INFORMATION_MESSAGE);
                btnDelete.setEnabled(false);
                btnDelete.setToolTipText("Request in process");
            }
        }
        if (e.getSource() == btnEdit) {
            btnSave.setEnabled(true);
            textFieldAddress.setEnabled(true);
            textFieldTelephone.setEnabled(true);
            textFieldEmail.setEnabled(true);
            passwordField.setEnabled(true);
            btnBrowse.setEnabled(true);
        }
        if (e.getSource() == btnSave) {
            String name = textFieldName.getText().trim();
            String lastName = textFieldLastName.getText().trim();
            String address = textFieldAddress.getText().trim();
            String telephone = textFieldTelephone.getText().trim();
            String email = textFieldEmail.getText().trim();
            String NID = textFieldNID.getText().trim();
            Date birthday = customer.getBirthday();
            String password = new String(passwordField.getPassword());
            String imagePath = customer.getProfilePicturePath();

            CustomerService customerService = new CustomerService();

            customerService.update(customer, address, telephone, email, password, imagePath);

            JOptionPane.showMessageDialog(this, "Customer profile updated successfully.", "Update", JOptionPane.INFORMATION_MESSAGE);

            textFieldAddress.setEnabled(false);
            textFieldTelephone.setEnabled(false);
            textFieldEmail.setEnabled(false);
            passwordField.setEnabled(false);
            btnBrowse.setEnabled(false);
        }
        if (e.getSource() == btnBack) {
            this.dispose();
            CustomerAccountsGUI customerAccountsGUI = new CustomerAccountsGUI(customer);
            customerAccountsGUI.setBounds(0,0,800,600);
            customerAccountsGUI.setResizable(false);
            customerAccountsGUI.setLocationRelativeTo(null);
            customerAccountsGUI.setVisible(true);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(date);
    }
}
