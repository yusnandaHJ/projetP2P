package server;

import client.FileClient;
import gui.MainFrameController;
import manager.FilesListManager;
import manager.PeersListManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import property.FileStorageProperties;
import representation.File;
import representation.Peer;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication (scanBasePackages = {"server","service","manager"})
@EnableConfigurationProperties({FileStorageProperties.class})
public class RunServerP2P extends JFrame {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(RunServerP2P.class, args);
        FilesListManager.initFileList();
        MainFrameController mainFrameController = new MainFrameController(); // GUI

        //test upload
        FileClient.uploadFile("http://localhost:8080","cf35d17c7a180fc62343b24505be58364594d85a8e034c193e65b92caa447adc");

        /* Files test list from other peers */
        mainFrameController.setFileList(FilesListManager.readFiles());

        System.out.println(FilesListManager.getAvailableFiles().size());

        HashMap<File,List<Peer>> files = FilesListManager.getAvailableFiles();

    }
}