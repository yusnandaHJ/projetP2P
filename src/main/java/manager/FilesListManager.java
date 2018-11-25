package manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import representation.File;

import java.io.IOException;
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
}
