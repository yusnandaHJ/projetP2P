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

    private static final java.io.File filePersistenceFile = new java.io.File(FileStorageProperties.getFilePersistenceJson());


    /**
     * Enregistre la liste de fichiers dans le fichier listeFichiers.json
     */
    public static void saveFiles(List<File> files) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(filePersistenceFile, files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajout d'un fichier à la liste de fichiers
     * @param file fichier à ajouter
     */
    public static void addFile(File file){
        List<File> files = readFiles();
        files.add(file);
        saveFiles(files);
    }

    /**
     * Suppression d'un fichier local
     * @param fileId Id du fichier à supprimer
     */
    public static void deleteLocalFile(String fileId){
        List<File> fileList = FilesListManager.readFiles();
        String fileName = null;
        for (Iterator<File> fileIterator = fileList.iterator(); fileIterator.hasNext(); ) {
            File file = fileIterator.next();
            if (file.getFileId().equals(fileId)) { //Si l'id en paramètre correspond à un fichier
                fileName = file.getName();
                java.io.File fileToDelete = new java.io.File(FileStorageProperties.getUploadDir() + "/" + fileName);
                fileIterator.remove();
                fileToDelete.delete();
            }
        }
        FilesListManager.saveFiles(fileList);
    }

    /**
     * Initialisation de la liste des fichiers via le répertoire partagé
     */
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

    /**
     * Récupération de la liste des fichiers disponibles ainsi que les pairs les partageant
     * @return
     */
    public static HashMap<File,List<Peer>> getAvailableFiles(){
        HashMap<File,List<Peer>> files = new HashMap<>();
        List<Peer> peers = PeersListManager.readPeers();

        //Pour chaque pair
        for (Peer p: peers) {
            //On récupère la liste des fichiers qu'il partage
            for(File f: FileClient.getFiles(p.getUrl())){
                if (files.containsKey(f)){//Si le fichier est déjà dans la map, on ajoute le pair à la ligne correspondante
                    files.get(f).add(p);
                }
                else{ //Sinon on crée une entrée dans la map
                    List<Peer> destPeers = new ArrayList<>();
                    destPeers.add(p);
                    files.put(f,destPeers);
                }
            }
        }

        return files;
    }


    /**
     * Récupération de la liste des fichiers via la map
     * @param filesMap Map des fichiers disponibles
     * @return liste des fichiers dispos
     */
    public static List<File> getFileListFromMap(HashMap<File,List<Peer>> filesMap){
        List<File> files = new ArrayList<>();

        Iterator it = filesMap.entrySet().iterator();
        while(it.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)it.next();
            files.add((File)pair.getKey());
        }

        return files;
    }

}
