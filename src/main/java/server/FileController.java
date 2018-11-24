package server;

import org.springframework.web.bind.annotation.*;
import representation.File;
import helper.FileUploadResponse;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {

    @GetMapping("/test")
    public File test() throws IOException {
        //Example
        return new File("001","test.json");
    }

    /**
     * Rest GET Handler pour récupérer un unique fichier
     * @param fileId
     * @return
     */
    @GetMapping("/files/{fileId}")
    public File getFile(@PathVariable String fileId){
        //Example
        return new File("0001","fichier1",12);
    }

    /**
     * Rest GET Handler pour récupérer une liste de fichiers
     * @param
     * @return
     */
    @GetMapping("/files")
    public List<File> getFiles() {
        //Example
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("0001","fichier1",12));
        fileList.add(new File("0002","fichier2",98));

        return fileList;
    }

    /**
     * Rest POST Handler pour envoyer un unique fichier
     * @param fileId
     * @return
     */
    @PostMapping("/files/{fileId}")
    public FileUploadResponse uploadFile(@PathVariable String fileId) {
        //Example
        FileUploadResponse response = new FileUploadResponse();
        response.setCle1(fileId);
        response.setCle2("Toto");
        response.setCle3("nintendo switch");

        return response;
    }

    /**
     * Rest POST Handler pour envoyer une liste de fichiers
     * @param
     * @return
     */
    @PostMapping("/files")
    public List<FileUploadResponse> uploadFiles() {
        //Example
        List<FileUploadResponse> responseList = new ArrayList<>();

        FileUploadResponse response1 = new FileUploadResponse();
        response1.setCle1("fichier1");
        response1.setCle2("toto");
        response1.setCle3("nintendo switch1");

        FileUploadResponse response2 = new FileUploadResponse();
        response2.setCle1("fichier2");
        response2.setCle2("toto");
        response2.setCle3("nintendo switch2");

        responseList.add(response1);
        responseList.add(response2);
        return responseList;
    }


    /**
     * Rest DELETE Handler pour supprimer une liste de fichiers
     * @param
     * @return
     */
    @DeleteMapping("/files/{fileId}")
    public void deleteFile() {
        //TODO
    }
}