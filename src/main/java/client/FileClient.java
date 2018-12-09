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
        //Ajout d'un timeout en cas de non disponibilité du serveur
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);
        List<File> files = new ArrayList<>();
        String result;

        try{
            //Envoi de la requête et stockage de la réponse dans une liste de fichiers
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
        //Ajout d'un timeout en cas de non disponibilité du serveur
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);

        try{
            restTemplate.postForObject(peerUrl+"/files", fileToUploadMetadata, File.class);
        }
        catch (RestClientException e){
            return false;
        }

        //Si l'envoi de metadata a fonctionné, on passe à l'envoi du contenu
        return uploadFileContent(peerUrl+"/files",fileToUploadMetadata);
    }

    /**
     * Fonction de gestion du POST de l'URL /files/fileId côté client
     */
    public static boolean uploadFileContent(String peerUrl, File fileMetadata) throws IOException {
        FileContent fileContent = FileStorageService.getFileContentByFileMetadata(fileMetadata);
        RestTemplate restTemplate = new RestTemplate();
        //Ajout d'un timeout en cas de non disponibilité du serveur
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);

        try{
            restTemplate.postForObject(peerUrl+"/"+fileMetadata.getFileId(), fileContent, FileContent.class);
        }
        catch (RestClientException e){
            return false;
        }
        return true;
    }

    /**
     * Fonction de gestion du GET de l'URL /files/{fileId} côté client
     */
    public static boolean getFile(String peerUrl, String fileId) {
        String result = null;
        List<File> files = getFiles(peerUrl);
        File fileToDownload = null;
        FileContent fileContent = null;
        RestTemplate restTemplate = new RestTemplate();
        //Ajout d'un timeout en cas de non disponibilité du serveur
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);

        for (File f: files) {
            //Si le fichier a été trouvé dans le répertoire
            if(f.getFileId().equals(fileId)){
                fileToDownload = f;
                break;
            }
        }

        if(fileToDownload==null){ //Si le fichier n'a pas été trouvé
            return false;
        }
        else{
            result = restTemplate.getForObject(peerUrl+"/files/"+fileId, String.class);

            try { //On récupère le contenu du fichier, qu'on decode et qu'on stocke dans le répertoire partagé
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
        //On ajoute les données dans une map
        params.put("fileId",fileid);
        RestTemplate restTemplate = new RestTemplate();
        //Ajout d'un timeout en cas de non disponibilité du serveur
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);

        try{
            //On envoie au serveur les informations pour supprimer le fichier
            restTemplate.delete(peerUrl+"/files" +"/{fileId}",params);
        }
        catch (RestClientException e){
            return false;
        }

        return true;
    }

}
