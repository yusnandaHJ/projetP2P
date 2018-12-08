package manager;


import client.FileClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
     * Renvoie la liste de fichier dans le répertoire /shared
     */
    public static List<File> getSharedList() throws IOException {
        List<File> files = new ArrayList<>();
        java.io.File dir = new java.io.File("./shared");
        java.io.File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (java.io.File child : directoryListing) {
                files.add(new File(child.getName()));
            }
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }
        return files;
    }

    public static HashMap<File,List<Peer>> getAvailableFiles(){
        HashMap<File,List<Peer>> files = new HashMap<>();

        /*File file1 = new File("test1",64);
        File file2 = new File("test2",128);
        File file3 = new File("test1",64);
        File file4 = new File("test3",1777);
        File file5 = new File("test4",64);

        List<Peer> peers1 = new ArrayList<>();

        peers1.add(new Peer("1","aaaaaaaaaaaaaa"));
        files.put(file1,peers1);

        files.put(file2,peers1);

        List<Peer> peers2 = new ArrayList<>(peers1);


        peers2.remove(0);
        peers2.add(new Peer("2","zzzzzzzzzzzzzzz"));
        Peer test = new Peer("2","zzzzzzzzzzzzzzz");
        if (files.containsKey(file3)){
            files.get(file3).add(test);
        }
        else{
            files.put(file3,peers2);
        }

        files.put(file4,peers2);

        List<Peer> peers3 = new ArrayList<>(peers2);
        peers3.add(new Peer("3","bbboooo"));
        files.put(file5,peers3);*/

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
