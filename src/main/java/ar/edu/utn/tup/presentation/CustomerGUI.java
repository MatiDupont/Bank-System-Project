package ar.edu.utn.tup.presentation;

import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.service.CustomerService;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelUser, labelPassword, labelRegister, labelFooter;
    protected JTextField textFieldUser;
    protected JPasswordField passwordFieldUser;
    protected JButton btnRegister;
    protected JButton btnSignIn;
    private Color background = new Color(18,18,18);
    Customer customer = new Customer();
    public CustomerGUI() {
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

        labelUser = new JLabel("DNI:");
        labelUser.setBounds(365,170,100,25);
        labelUser.setFont(new Font("Serif", Font.PLAIN, 18));
        labelUser.setForeground(new Color(255,255,255));
        add(labelUser);

        textFieldUser = new JTextField();
        textFieldUser.setBounds(285,205,200,30);
        textFieldUser.setBackground(new Color(0,120,255));
        textFieldUser.setForeground(new Color(0,0,0));
        textFieldUser.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldUser.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldUser.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldUser);

        labelPassword = new JLabel("Password:");
        labelPassword.setBounds(350,240,100,25);
        labelPassword.setFont(new Font("Serif", Font.PLAIN, 18));
        labelPassword.setForeground(new Color(255,255,255));
        add(labelPassword);

        passwordFieldUser = new JPasswordField();
        passwordFieldUser.setBounds(285,275,200,30);
        passwordFieldUser.setBackground(new Color(0,120,255));
        passwordFieldUser.setForeground(new Color(0,0,0));
        passwordFieldUser.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        passwordFieldUser.setHorizontalAlignment(SwingConstants.CENTER);
        add(passwordFieldUser);

        btnSignIn = new JButton("Sign In");
        btnSignIn.setBounds(340,330,100,30);
        btnSignIn.setBackground(new Color(0,120,255));
        btnSignIn.setForeground(new Color(0,0,0));
        btnSignIn.setFont(new Font("Calibri",Font.ITALIC, 18));
        add(btnSignIn);
        btnSignIn.addActionListener(this);

        labelRegister = new JLabel("Register new account below");
        labelRegister.setBounds(305,370,200,25);
        labelRegister.setFont(new Font("SansSerif", Font.BOLD, 12));
        labelRegister.setForeground(new Color(255,255,255));
        add(labelRegister);

        // Alta de cliente
        btnRegister = new JButton("Register");
        btnRegister.setBounds(340,410,100,30);
        btnRegister.setBackground(new Color(0,120,255));
        btnRegister.setForeground(new Color(0,0,0));
        btnRegister.setFont(new Font("Calibri",Font.ITALIC, 18));
        add(btnRegister);
        btnRegister.addActionListener(this);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(312, 500, 210, 20);
        labelFooter.setForeground(new Color(255,255,255));
        add(labelFooter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSignIn) {
            // Flujo de trabajo
            /*
            1. El cliente ingresa sus credenciales en los campos de texto
            2. Al presionar el boton "Sign In" se capturan los datos ingresados
            3. Se llama al metodo findCustomer en la clase Customer, que a su vez llama al metodo correspondiente en CustomerDAO
            4. El metodo en CustomerDAO consulta la base de datos y retorna un objeto Customer si las credenciales son correctas o null si no lo son
            5. Dependiendo el resultado, se muestra un mensaje de exito o error utilizando JOptionPane
             */
            String NID = textFieldUser.getText().trim();
            String password = new String(passwordFieldUser.getPassword());

            if (NID.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null, "You must fill out all the fields", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            else {
                CustomerService customerService = new CustomerService();

                Customer c = customerService.findCustomer(NID, password);

                if (c != null) {
                    JOptionPane.showMessageDialog(null, "Sign In Successful");

                    CustomerAccountsGUI customerAccountsGUI = new CustomerAccountsGUI(c);
                    customerAccountsGUI.setBounds(0,0,800,600);
                    customerAccountsGUI.setResizable(false);
                    customerAccountsGUI.setLocationRelativeTo(null);
                    customerAccountsGUI.setVisible(true);
                    this.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Incorrect Data", "Error", JOptionPane.ERROR_MESSAGE);
                    textFieldUser.setText("");
                    passwordFieldUser.setText("");
                }
            }
        }
        if (e.getSource() == btnRegister) {
            CreateCustomerGUI createCustomer = new CreateCustomerGUI();
            createCustomer.setBounds(0,0,800,600);
            createCustomer.setResizable(false);
            createCustomer.setLocationRelativeTo(null);
            createCustomer.setVisible(true);
            this.dispose();
        }
    }
}
