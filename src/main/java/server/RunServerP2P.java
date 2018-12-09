package server;

import gui.MainFrameController;
import manager.FilesListManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import property.FileStorageProperties;
import property.PeerServerProperties;

import javax.swing.*;
import java.io.IOException;

@SpringBootApplication (scanBasePackages = {"server","service","manager"})
@EnableConfigurationProperties({FileStorageProperties.class,PeerServerProperties.class})
public class RunServerP2P extends JFrame {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(RunServerP2P.class, args);
        FilesListManager.initFileList();
        MainFrameController mainFrameController = new MainFrameController(); // GUI
    }
}