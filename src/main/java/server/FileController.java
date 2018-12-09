package server;

import manager.FilesListManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import representation.File;
import representation.FileContent;
import service.FileStorageService;

import java.io.IOException;
import java.util.List;

/**
 * Classe controller des requêtes rest concernant les fichiers côté serveur
 */
@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Rest GET Handler pour récupérer les données d'un unique fichier
     */
    @GetMapping("/files/{fileId}")
    public FileContent getFile(@PathVariable String fileId) throws IOException {
        // Récupére les fichiers en tant que Resource
        String fileName = null;

        for (File file : FilesListManager.readFiles()) {
            if (file.getFileId().equals(fileId)) {
                fileName = file.getName();
            }
        }
        try{
            return new FileContent(fileStorageService.loadFile(fileName)) ;
        }catch(IOException e){
            throw new IOException("Le fichier n'a pas pu être chargé");
        }
    }

    /**
     * Rest GET Handler pour récupérer une liste de fichiers
     */
    @GetMapping("/files")
    public List<File> getFileList() throws IOException {
        return FilesListManager.readFiles();
    }

    /**
     * Rest POST Handler pour envoyer un unique fichier
     */
    @PostMapping("/files/{fileId}")
    public void uploadFile(@PathVariable String fileId, @RequestBody FileContent fileContent) throws IOException {
        File fileMetadata = FileStorageService.getFileMetadataByFileId(fileId);
        fileStorageService.storeFile(fileContent,fileMetadata);
    }

    /**
     * Rest POST Handler pour envoyer une liste de fichiers
     */
    @PostMapping("/files")
    public void uploadFileMetadata(@RequestBody File file) {
        List<File> fileList = FilesListManager.readFiles();
        fileList.add(file);
        FilesListManager.saveFiles(fileList);
    }

    /**
     * Rest DELETE Handler pour supprimer une liste de fichiers
     */
    @DeleteMapping("/files/{fileId}")
    public void deleteFile(@PathVariable String fileId) {
        FilesListManager.deleteLocalFile(fileId);
    }
}
