package server;

import manager.FilesListManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import representation.File;
import service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Classe controller des requêtes rest concernant les fichiers côté serveur
 */
@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private String storageDirectoryPath = "./shared/";

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Rest GET Handler pour récupérer les données d'un unique fichier
     */
    @GetMapping("/files/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileId, HttpServletRequest request) {
        // Récupére les fichiers en tant que Resource
        String fileName = null;

        for (File file : FilesListManager.readFiles()) {
            if (file.getFileid().equals(fileId)) {
                fileName = file.getName();
            }
        }

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Détermine le type de fichier
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Impossible de déterminer le type du fichier.");
        }
        //Type de fichier par défaut
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Rest GET Handler pour récupérer une liste de fichiers
     */
    @GetMapping("/files")
    public List<File> getFileList() {
        return FilesListManager.readFiles();
    }

    /**
     * Rest POST Handler pour envoyer un unique fichier
     */
    @PostMapping("/files/{fileId}")
    public File uploadFile(@RequestParam("file") MultipartFile file) {
        List<File> fileList = FilesListManager.readFiles();
        String fileName = fileStorageService.storeFile(file);

        File fileToAdd = new File(fileName, file.getSize());

        if (fileList.stream().noneMatch(o -> o.getFileid().equals(fileToAdd.getFileid()))) {
            fileList.add(fileToAdd);
            FilesListManager.saveFiles(fileList);
        }
        return fileToAdd;
    }

    /**
     * Rest POST Handler pour envoyer une liste de fichiers
     */
    @PostMapping("/files")
    public ResponseEntity<List<File>> uploadFiles(@RequestBody List<File> files) {
        //Example
        //files.stream().forEach(f -> f.getFileid());
        //System.out.println(files.get(0).getSize());


        // TODO: call persistence layer to update
        return new ResponseEntity<>(files, HttpStatus.OK);
        //return files.getFiles();
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
            if (file.getFileid().equals(fileId)) {
                fileName = file.getName();
                java.io.File fileToDelete = new java.io.File(this.storageDirectoryPath + fileName);
                fileIterator.remove();
                fileToDelete.delete();
            }
        }
        FilesListManager.saveFiles(fileList);
    }
}