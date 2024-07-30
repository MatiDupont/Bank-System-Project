package ar.edu.utn.tup.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessGUI extends JFrame implements ActionListener {
    private JLabel labelIcon, labelAdmin, labelCustomer, labelFooter;
    private JMenuBar menuBar;
    private JMenu security, reload, setting, screen, home, logOut, help, wifi, calendar, info, microphoneOn, powerOff;
    private JMenuItem darkMode, lightMode;
    private JButton btnCustomer, btnAdmin;
    private Color background = new Color(18,18,18);

    public AccessGUI() {
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MDCompany");
        setIconImage(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png").getImage());
        getContentPane().setBackground(background);

        initComponents();
    }

    private void initComponents() {
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 120, 255));
        setJMenuBar(menuBar);

        powerOff = new JMenu();
        powerOff.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\powerOffIcon.png"));
        powerOff.setToolTipText("Power Off");
        menuBar.add(powerOff);

        reload = new JMenu();
        reload.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\reloadIcon.png"));
        reload.setToolTipText("Reload");
        menuBar.add(reload);

        home = new JMenu();
        home.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\homeIcon.png"));
        home.setToolTipText("Home");
        menuBar.add(home);

        wifi = new JMenu();
        wifi.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\wifiIcon.png"));
        wifi.setToolTipText("Wifi");
        menuBar.add(wifi);

        microphoneOn = new JMenu();
        microphoneOn.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\microphoneOnIcon.png"));
        microphoneOn.setToolTipText("Microphone On");
        menuBar.add(microphoneOn);

        setting = new JMenu();
        setting.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\settingIcon.png"));
        setting.setToolTipText("Setting");
        menuBar.add(setting);

        logOut = new JMenu();
        logOut.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logOutIcon.png"));
        logOut.setToolTipText("LogOut");
        menuBar.add(logOut);

        screen = new JMenu();
        screen.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\screenIcon.png"));
        screen.setToolTipText("Screen");
        menuBar.add(screen);

        info = new JMenu();
        info.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\infoIcon.png"));
        info.setToolTipText("Info");
        menuBar.add(info);

        help = new JMenu();
        help.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\helpIcon.png"));
        help.setToolTipText("Help");
        menuBar.add(help);

        calendar = new JMenu();
        calendar.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\calendarIcon.png"));
        calendar.setToolTipText("Calendar");
        menuBar.add(calendar);

        security = new JMenu();
        security.setIcon(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\securityIcon.png"));
        security.setToolTipText("Secure connection");
        menuBar.add(security);

        darkMode = new JMenuItem("Dark");
        darkMode.setForeground(new Color(0,0,0));
        darkMode.setFont(new Font("Tahoma",Font.BOLD, 15));
        screen.add(darkMode);
        darkMode.addActionListener(this);

        lightMode = new JMenuItem("Light");
        lightMode.setForeground(new Color(0,0,0));
        lightMode.setFont(new Font("Tahoma",Font.BOLD, 15));
        screen.add(lightMode);
        lightMode.addActionListener(this);

        ImageIcon systemIcon = new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png");
        Icon icon = new ImageIcon(systemIcon.getImage().getScaledInstance(70,70, Image.SCALE_DEFAULT));
        labelIcon = new JLabel(icon);
        labelIcon.setBounds(350,50,70,70);
        this.repaint();
        add(labelIcon);

        btnAdmin = new JButton(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\adminIcon.png"));
        btnAdmin.setBounds(160,200,150,150);
        btnAdmin.setBackground(new Color(0,120,255));
        add(btnAdmin);
        btnAdmin.addActionListener(this);

        labelAdmin = new JLabel("Admin");
        labelAdmin.setBounds(205,370,100,30);
        labelAdmin.setFont(new Font("SansSerif", Font.PLAIN, 18));
        labelAdmin.setForeground(new Color(255,255,255));
        add(labelAdmin);

        btnCustomer = new JButton(new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\customerIcon.png"));
        btnCustomer.setBounds(460,200,150,150);
        btnCustomer.setBackground(new Color(0,120,255));
        add(btnCustomer);
        btnCustomer.addActionListener(this);

        labelCustomer = new JLabel("Customer");
        labelCustomer.setBounds(495,370,100,30);
        labelCustomer.setFont(new Font("SansSerif", Font.PLAIN, 18));
        labelCustomer.setForeground(new Color(255,255,255));
        add(labelCustomer);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(305, 500, 210, 20);
        labelFooter.setForeground(new Color(255,255,255));
        add(labelFooter);
    }

    @Override
    public Color getBackground() {
        return background;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == darkMode) {
            background = new Color(18,18,18);
            getContentPane().setBackground(background);
            labelAdmin.setForeground(new Color(255,255,255));
            labelCustomer.setForeground(new Color(255,255,255));
            labelFooter.setForeground(new Color(255,255,255));
        }
        /*if (e.getSource() == lightMode) {
            background = new Color(255,255,255);
            getContentPane().setBackground(background);
            labelAdmin.setForeground(new Color(0,0,0));
            labelCustomer.setForeground(new Color(0,0,0));
            labelFooter.setForeground(new Color(0,0,0));
        }*/
        if (e.getSource() == btnCustomer) {
            this.dispose();
            CustomerGUI customerGUI = new CustomerGUI();
            customerGUI.setBounds(0,0,800,600);
            customerGUI.setResizable(false);
            customerGUI.setLocationRelativeTo(null);
            customerGUI.setVisible(true);
        }
        if (e.getSource() == btnAdmin) {
            String securityCode = JOptionPane.showInputDialog(null, "Enter the bank security code:", "Access", JOptionPane.PLAIN_MESSAGE);

            if (securityCode != null && !securityCode.isEmpty()) {
                if (securityCode.equals("codeTest")) {
                    this.dispose();
                    AdminGUI adminIGU = new AdminGUI();
                    adminIGU.setBounds(0,0,800,600);
                    adminIGU.setResizable(false);
                    adminIGU.setLocationRelativeTo(null);
                    adminIGU.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Access denied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Security code is required to proceed.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
