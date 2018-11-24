package server;

import client.FileClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import property.FileStorageProperties;
import representation.Peer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication (scanBasePackages = {"server","service"})
@EnableConfigurationProperties({FileStorageProperties.class})
public class RunServerP2P {
    public static void main(String[] args) {
        SpringApplication.run(RunServerP2P.class, args);
        FileClient.getFiles();
        //FileClient.getFile("oui");
        FileClient.updateFiles();
        List<Peer> peers = new ArrayList();

        peers = PeersList.readPeers();
        for (Peer p: peers
             ) {
            System.out.println(p.getUrl());
        }
        
        peers.add(new Peer("001","xxxxxx"));
        peers.add(new Peer("002","zzzzzz"));
        PeersList.savePeers(peers);
        
        
    }
}