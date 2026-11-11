package br.com.reciclamais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReciclaMaisApplication {

    public static void main(String[] args) {
        // A interface Swing é o padrão deste aplicativo desktop. Defina isso
        // antes do Spring ou de qualquer classe AWT verificar o modo headless.
        if (!"false".equalsIgnoreCase(System.getProperty("reciclamais.desktop.enabled", "true"))) {
            System.setProperty("java.awt.headless", "false");
        }
        SpringApplication.run(ReciclaMaisApplication.class, args);
    }

}
