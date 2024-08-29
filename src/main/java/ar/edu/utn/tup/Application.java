package ar.edu.utn.tup;

import ar.edu.utn.tup.presentation.AccessGUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        AccessGUI accessIGU = new AccessGUI();
        accessIGU.setBounds(0,0,800,600);
        accessIGU.setResizable(false);
        accessIGU.setLocationRelativeTo(null);
        accessIGU.setVisible(true);

        SpringApplication.run(Application.class, args);
    }
}
