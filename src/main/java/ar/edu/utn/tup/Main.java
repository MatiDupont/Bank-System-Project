package ar.edu.utn.tup;

import ar.edu.utn.tup.presentation.AccessGUI;

public class Main {
    public static void main(String[] args) {
        AccessGUI accessIGU = new AccessGUI();
        accessIGU.setBounds(0,0,800,600);
        accessIGU.setResizable(false);
        accessIGU.setLocationRelativeTo(null);
        accessIGU.setVisible(true);
    }
}
