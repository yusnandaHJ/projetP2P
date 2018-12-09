package client;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import manager.FilesListManager;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import property.FileStorageProperties;
import representation.File;
import representation.FileContent;
import service.FileStorageService;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe des fonctions gérant les fichiers côté client
 */
public class FileClient {

    /**
     * Fonction de gestion du GET de l'URL /files côté client
     */
    public static List<File> getFiles(String peerUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);
        List<File> files = new ArrayList<>();
        String result;

        try{
            result = restTemplate.getForObject(peerUrl+"/files", String.class);
            files = new ObjectMapper().readValue(result, new TypeReference<List<File>>(){});
        }
        catch (RestClientException e){
            return new ArrayList<>();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    /**
     * Fonction de gestion du POST de l'URL /files côté client
     */
    public static boolean uploadFile(String peerUrl, String fileId)throws IOException {
        File fileToUploadMetadata = FileStorageService.getFileMetadataByFileId(fileId);
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);
        String result;

        try{
            restTemplate.postForObject(peerUrl+"/files", fileToUploadMetadata, File.class);
        }
        catch (RestClientException e){
            System.out.println("Erreur dans l'upload des meta");
            return false;
        }

        return uploadFileContent(peerUrl+"/files",fileToUploadMetadata);


        //System.out.println(fileToUploadMetadata.toString);
    }

    /**
     * Fonction de gestion du POST de l'URL /files/fileId côté client
     */
    public static boolean uploadFileContent(String peerUrl, File fileMetadata) throws IOException {
        FileContent fileContent = FileStorageService.getFileContentByFileMetadata(fileMetadata);
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);
        String result;

        try{
            restTemplate.postForObject(peerUrl+"/"+fileMetadata.getFileId(), fileContent, FileContent.class);
        }
        catch (RestClientException e){
            System.out.println("Erreur dans l'upload des meta");
            return false;
        }

        return true;
    }

    /**
     * Fonction de gestion du GET de l'URL /files/{fileId} côté client
     */
    public static boolean getFile(String peerUrl, String fileid) {
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);
        String result = null;
        List<File> files = getFiles(peerUrl);
        File fileToDownload = null;
        FileContent fileContent = null;

        for (File f: files) {
            if(f.getFileId().equals(fileid)){
                fileToDownload = f;
                break;
            }
        }

        if(fileToDownload==null){
            return false;
        }
        else{
            result = restTemplate.getForObject(peerUrl+"/files/"+fileid, String.class);

            //Uncomment to test local
            /*fileToDownload.setName("clone_"+fileToDownload.getName());
            fileToDownload.setFileId("5"+fileToDownload.getFileId());*/

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
        return true;
    }

    /**
     * Fonction de gestion du DELETE de l'URL /files/[fileId} côté client
     */
    public static boolean deleteFile(String peerUrl, String fileid) {
        Map<String, String> params = new HashMap<>();
        params.put("fileId",fileid);
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);

        try{
            restTemplate.delete(peerUrl+"/files" +"/{fileId}",params);
        }
        catch (RestClientException e){
            System.out.println("Erreur dans la suppression du fichier");
            return false;
        }

        return true;
    }

}
