package server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import helper.FileUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import representation.File;
import helper.FileUploadResponse;
import representation.FileList;
import service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private List<File> fileList = new ArrayList<>();

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Rest GET Handler pour récupérer les données d'un unique fichier
     *
     * @param fileId
     * @return
     */
    @GetMapping("/files/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileId, HttpServletRequest request) {
        // Récupére les fichiers en tant que Resource
        String fileName = null;
        for(File file : fileList){
            if(file.getFileid().equals(fileId)){
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
     *
     * @param
     * @return
     */
    @GetMapping("/files")
    public List<File> getFileList() {
        /*Initialiser fileList en ce basant sur listeFichier.json*/
        return this.fileList;
    }


    /**
     * Rest POST Handler pour envoyer un unique fichier
     *
     * @param file
     * @return
     */
    @PostMapping("/files/{fileId}")
    public File uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        File fileToAdd = new File(fileName, file.getSize());

        fileList.add(fileToAdd);

        return fileToAdd;
    }

    /**
     * Rest POST Handler pour envoyer une liste de fichiers
     *
     * @param
     * @return
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
     *
     * @param
     * @return
     */
    @DeleteMapping("/files/{fileId}")
    public void deleteFile() {
        //TODO
    }
}