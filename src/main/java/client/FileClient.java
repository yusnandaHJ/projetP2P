package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import manager.FilesListManager;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.client.RestTemplate;
import property.FileStorageProperties;
import representation.File;
import representation.FileContent;
import service.FileStorageService;

import java.io.OutputStream;
import java.net.*;
import java.util.List;
import java.util.Map;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe des fonctions gérant les fichiers côté client
 */
public class FileClient {

    private static final String uri = "http://localhost:8080/files";
    //private static final String uri = "http://192.168.0.4:7878/files";

    /**
     * Fonction de gestion du GET de l'URL /files côté client
     */

    public static List<File> getFiles(String peerUrl) {
        RestTemplate restTemplate = new RestTemplate();
        List<File> files = new ArrayList<>();
        String result = restTemplate.getForObject(peerUrl+"/files", String.class);

        try {
            files = new ObjectMapper().readValue(result, new TypeReference<List<File>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    /**
     * Fonction de gestion du POST de l'URL /files côté client
     */
    public static void uploadFile(String peerUrl, String fileId)throws IOException {

        File fileToUploadMetadata = FileStorageService.getFileMetadataByFileId(fileId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(peerUrl+"/files", fileToUploadMetadata, File.class);

        uploadFileContent(peerUrl+"/files",fileToUploadMetadata);

        //System.out.println(fileToUploadMetadata.toString);
    }

    /**
     * Fonction de gestion du POST de l'URL /files/fileId côté client
     */
    public static void uploadFileContent(String peerUrl, File fileMetadata) throws IOException {
        FileContent fileContent = FileStorageService.getFileContentByFileMetadata(fileMetadata);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(peerUrl+"/"+fileMetadata.getFileId(), fileContent, FileContent.class);

        //System.out.println(fileContent.getContent());
    }

    /**
     * Fonction de gestion du GET de l'URL /files/{fileId} côté client
     */
    public static void getFile(String peerUrl, String fileid) {
        RestTemplate restTemplate = new RestTemplate();
        String result = null;
        List<File> files = getFiles(peerUrl);
        File fileToDownload = null;

        for (File f: files) {
            if(f.getFileId().equals(fileid)){
                fileToDownload = f;
                break;
            }
        }

        if(fileToDownload != null){
            result = restTemplate.getForObject(peerUrl+"/files/"+fileid, String.class);
            FileContent fileContent = new FileContent();

            //Uncomment to test local
            fileToDownload.setName("clone_"+fileToDownload.getName());
            fileToDownload.setFileId("5"+fileToDownload.getFileId());

            try {
                fileContent = new ObjectMapper().readValue(result, FileContent.class);
                byte[] data = fileContent.decode();
                try (OutputStream stream = new FileOutputStream(FileStorageProperties.getUploadDir()+"/"+fileToDownload.getName())) {
                    stream.write(data);
                    FilesListManager.addFile(fileToDownload);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fonction de gestion du DELETE de l'URL /files/[fileId} côté client
     */
    public static void deleteFile(String peerUrl, String fileid) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("fileId",fileid);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(peerUrl+"/files" +"/{fileId}",params);

        //System.out.println(result);
    }

}
