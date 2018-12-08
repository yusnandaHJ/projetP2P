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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static manager.FilesListManager.getSharedList;

@SpringBootApplication (scanBasePackages = {"server","service"})
@EnableConfigurationProperties({FileStorageProperties.class})
public class RunServerP2P extends JFrame {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(RunServerP2P.class, args);


        MainFrameController mainFrameController = new MainFrameController(); // GUI

        /* Files test list from other peers */
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("file 001",1000));
        fileList.add(new File("file 002",2000));
        fileList.add(new File("file 003",3000));
        fileList.add(new File("file 004",4000));
        fileList.add(new File("file 005",5000));

        mainFrameController.setFileList(fileList);

        System.out.println(FilesListManager.getAvailableFiles().size());

        HashMap<File,List<Peer>> files = FilesListManager.getAvailableFiles();

        /*Iterator it = files.entrySet().iterator();
        while(it.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)it.next();
            //System.out.println(((File)pair.getKey()).getFileId() + " = "+pair.getValue());
            System.out.println(pair.getKey() + " = "+pair.getValue());
            it.remove();
        }*/

        //FileClient.downloadUpdate("http://localhost:8080","a5da68f5680da4ea4fa974e49f5c50c54a22a19de0f88da5096e3a9d714bd389");

        /*List<File> list = FilesListManager.getFileListFromMap(files);
        for (File f: list
             ) {
            System.out.println(f);
        }*/

        /*for (File f: FileClient.getFiles()
             ) {
            System.out.println(f);
        }*/


        /*FileClient.getFiles();
        FileClient.getFile("6fab43fcb5c8a53b1693bad716e20055023419c52fb584ca6c7c51e4f8cf04da");
        FileClient.updateFiles();

        List<Peer> peers = new ArrayList();
        peers.add(new Peer("001","xxaax"));
        peers.add(new Peer("002","zzzuuz"));
        PeersListManager.savePeers(peers);*/

        List<File> sharedFiles = getSharedList();
        FilesListManager.saveFiles(getSharedList());

        //for (File f: sharedFiles) {
          //  System.out.println(f.getFileId()+" === "+f.getName()+" === "+f.getSize());
        //}
    }
}