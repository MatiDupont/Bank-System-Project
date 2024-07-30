package ar.edu.utn.tup.presentation;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class AdminGUI extends JFrame {
    private JLabel labelIcon, labelUser, labelPassword, labelFooter;
    private JMenuBar menuBar;
    private JMenu security, reload, setting, screen, home, logOut, help, wifi, calendar, info, microphoneOn, powerOff;
    private JMenuItem darkMode, lightMode;
    private JTextField textFieldUser;
    private JPasswordField passwordFieldUser;
    private JButton btnSignIn;
    private Color background = new Color(18, 18, 18);

    public AdminGUI() {
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
        darkMode.setForeground(new Color(0, 0, 0));
        darkMode.setFont(new Font("Tahoma", Font.BOLD, 15));
        screen.add(darkMode);

        lightMode = new JMenuItem("Light");
        lightMode.setForeground(new Color(0, 0, 0));
        lightMode.setFont(new Font("Tahoma", Font.BOLD, 15));
        screen.add(lightMode);

        ImageIcon systemIcon = new ImageIcon("C:\\Users\\Usuario\\Documents\\Matias\\LABO3 2023\\Sistema Bancario\\src\\main\\java\\ar\\edu\\utn\\tup\\presentation\\images\\logoBancario.png");
        Icon icon = new ImageIcon(systemIcon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        labelIcon = new JLabel(icon);
        labelIcon.setBounds(350, 50, 70, 70);
        this.repaint();
        add(labelIcon);

        labelUser = new JLabel("Enrollment:");
        labelUser.setBounds(340, 170, 100, 25);
        labelUser.setFont(new Font("Serif", Font.PLAIN, 18));
        labelUser.setForeground(new Color(255, 255, 255));
        add(labelUser);

        textFieldUser = new JTextField();
        textFieldUser.setBounds(285, 205, 200, 30);
        textFieldUser.setBackground(new Color(0, 120, 255));
        textFieldUser.setForeground(new Color(0, 0, 0));
        textFieldUser.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        textFieldUser.setFont(new Font("DialogInput", Font.BOLD, 18));
        textFieldUser.setHorizontalAlignment(SwingConstants.CENTER);
        add(textFieldUser);

        labelPassword = new JLabel("Password:");
        labelPassword.setBounds(350, 240, 100, 25);
        labelPassword.setFont(new Font("Serif", Font.PLAIN, 18));
        labelPassword.setForeground(new Color(255, 255, 255));
        add(labelPassword);

        passwordFieldUser = new JPasswordField();
        passwordFieldUser.setBounds(285, 275, 200, 30);
        passwordFieldUser.setBackground(new Color(0, 120, 255));
        passwordFieldUser.setForeground(new Color(0, 0, 0));
        passwordFieldUser.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        passwordFieldUser.setHorizontalAlignment(SwingConstants.CENTER);
        add(passwordFieldUser);

        btnSignIn = new JButton("Sign In");
        btnSignIn.setBounds(340, 330, 100, 30);
        btnSignIn.setBackground(new Color(0, 120, 255));
        btnSignIn.setForeground(new Color(0, 0, 0));
        btnSignIn.setFont(new Font("Calibri", Font.ITALIC, 18));
        add(btnSignIn);

        labelFooter = new JLabel("Designed by Matias Dupont ©");
        labelFooter.setBounds(312, 500, 210, 20);
        labelFooter.setForeground(new Color(255, 255, 255));
        add(labelFooter);
    }
}
