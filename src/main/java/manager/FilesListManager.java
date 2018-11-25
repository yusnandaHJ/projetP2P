package manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import representation.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesListManager {

    private static final java.io.File filePersistenceFile = new java.io.File("./src/main/resources/listeFichiers.json");

    public static void saveFiles(List<File> files){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(filePersistenceFile,files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<File> readFiles(){
        List<File> files = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            files = mapper.readValue(filePersistenceFile, new TypeReference<List<File>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static List<File> getSharedList() throws IOException {
        List<File> files = new ArrayList<>();
        java.io.File dir = new java.io.File("shared/fichiers");
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
