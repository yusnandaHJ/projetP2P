package server;

import manager.FilesListManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import property.FileStorageProperties;
import representation.File;
import representation.FileContent;
import service.FileStorageService;

import java.io.IOException;
import java.util.Iterator;
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
    public void uploadFile(@RequestBody FileContent fileContent) {
    }

    /**
     * Rest POST Handler pour envoyer une liste de fichiers
     */
    @PostMapping("/files")
    public void uploadFileMetadata(@RequestBody File file) {
    }

    /**
     * Rest DELETE Handler pour supprimer une liste de fichiers
     */
    @DeleteMapping("/files/{fileId}")
    public void deleteFile(@PathVariable String fileId) {
        List<File> fileList = FilesListManager.readFiles();
        String fileName = null;
        for (Iterator<File> fileIterator = fileList.iterator(); fileIterator.hasNext(); ) {
            File file = fileIterator.next();
            if (file.getFileId().equals(fileId)) {
                fileName = file.getName();
                java.io.File fileToDelete = new java.io.File(FileStorageProperties.getUploadDir()+"/"+ fileName);
                fileIterator.remove();
                fileToDelete.delete();
            }
        }
        FilesListManager.saveFiles(fileList);
    }
}