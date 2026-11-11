package br.com.reciclamais.desktop;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import java.awt.GraphicsEnvironment;

@Component
@ConditionalOnProperty(name = "reciclamais.desktop.enabled", havingValue = "true", matchIfMissing = true)
public class JanelaOperacaoRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(JanelaOperacaoRunner.class);

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) {
        if (GraphicsEnvironment.isHeadless()) {
            log.warn("A janela Recicla+ não será aberta porque o Java iniciou em modo sem interface gráfica.");
            return;
        }
        log.info("Abrindo a janela de operação do Recicla+.");
        SwingUtilities.invokeLater(() -> {
            try {
                JanelaOperacao janela = new JanelaOperacao();
                janela.setVisible(true);
                janela.toFront();
                log.info("Janela de operação do Recicla+ aberta.");
            } catch (RuntimeException erro) {
                log.error("Não foi possível abrir a janela de operação do Recicla+.", erro);
            }
        });
    }
}
