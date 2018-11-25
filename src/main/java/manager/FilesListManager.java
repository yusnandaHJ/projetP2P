package manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import representation.File;

import java.io.IOException;
import java.util.ArrayList;
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
}
