package manager;


import client.FileClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import property.FileStorageProperties;
import representation.File;
import representation.Peer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;

/**
 * Classe gérant le fichier de persistence des listes des fichiers présents dans un pair
 */
public class FilesListManager {

    private static final java.io.File filePersistenceFile = new java.io.File("./src/main/resources/listeFichiers.json");


    /**
     * Enregistre la liste de fichier dans le fichier listeFichiers.json
     */
    public static void saveFiles(List<File> files) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(filePersistenceFile, files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addFile(File file){
        List<File> files = readFiles();
        files.add(file);
        saveFiles(files);
    }

    public static void initFileList(){
        List<representation.File> fileListMetadata = new ArrayList<>();
        String test = FileStorageProperties.getUploadDir();
        java.io.File sharedDirectory = new java.io.File(test);
        java.io.File[] directoryListing = sharedDirectory.listFiles();
        if(directoryListing != null) {
            for(java.io.File file : directoryListing) {
                fileListMetadata.add(new representation.File(file.getName(),file.length()));
            }
        }
        saveFiles(fileListMetadata);
    }

    /**
     * Lit la liste de fichier dans le fichier listeFichiers.json
     */
    public static List<File> readFiles() {
        List<File> files = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            files = mapper.readValue(filePersistenceFile, new TypeReference<List<File>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static HashMap<File,List<Peer>> getAvailableFiles(){
        HashMap<File,List<Peer>> files = new HashMap<>();
        List<Peer> peers = PeersListManager.readPeers();

        for (Peer p: peers) {
            for(File f: FileClient.getFiles(p.getUrl())){
                if (files.containsKey(f)){
                    files.get(f).add(p);
                }
                else{
                    List<Peer> destPeers = new ArrayList<>();
                    destPeers.add(p);
                    files.put(f,destPeers);
                }
            }
        }

        return files;
    }

    public static List<File> getFileListFromMap(HashMap<File,List<Peer>> filesMap){
        List<File> files = new ArrayList<>();

        Iterator it = filesMap.entrySet().iterator();
        while(it.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)it.next();
            //System.out.println(((File)pair.getKey()).getFileId() + " = "+pair.getValue());
            files.add((File)pair.getKey());
            //it.remove();
        }

        return files;
    }

}
