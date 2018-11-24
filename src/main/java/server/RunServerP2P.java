package server;

import client.FileClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import property.FileStorageProperties;

@SpringBootApplication (scanBasePackages = {"server","service"})
@EnableConfigurationProperties({FileStorageProperties.class})
public class RunServerP2P {
    public static void main(String[] args) {
        SpringApplication.run(RunServerP2P.class, args);
        FileClient.getFiles();
        FileClient.getFile("0001");
        FileClient.updateFiles();
    }
}