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
        URL obj = null;

        try {
            obj = new URL(peerUrl+"/files" + "/" + fileid);
            URLConnection conn = obj.openConnection();
            Map<String, List<String>> map = conn.getHeaderFields();

            System.out.println("Printing Response Header...\n");

            String filename = conn.getHeaderField("Content-Disposition").substring(22,conn.getHeaderField("Content-Disposition").length()-1);
            filename = URLDecoder.decode(filename,"UTF-8");
            System.out.println(filename);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
