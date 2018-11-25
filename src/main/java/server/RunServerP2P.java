package server;

import client.FileClient;
import manager.PeersListManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import property.FileStorageProperties;
import representation.File;
import representation.Peer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static manager.FilesListManager.getSharedList;

@SpringBootApplication (scanBasePackages = {"server","service"})
@EnableConfigurationProperties({FileStorageProperties.class})
public class RunServerP2P {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(RunServerP2P.class, args);
        /*FileClient.getFiles();
        FileClient.getFile("6fab43fcb5c8a53b1693bad716e20055023419c52fb584ca6c7c51e4f8cf04da");
        FileClient.updateFiles();

        List<Peer> peers = new ArrayList();
        peers.add(new Peer("001","xxaax"));
        peers.add(new Peer("002","zzzuuz"));
        PeersListManager.savePeers(peers);

        peers = PeersListManager.readPeers();
        for (Peer p: peers) {
            System.out.println(p.getUrl());
        }*/
        List<File> sharedFiles = getSharedList();
        for (File f: sharedFiles) {
            System.out.println(f.getFileid()+" === "+f.getName()+" === "+f.getSize());
        }
    }
}