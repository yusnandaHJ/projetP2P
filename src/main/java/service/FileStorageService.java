package service;

import manager.FilesListManager;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import property.FileStorageProperties;
import representation.FileContent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Classe service utile pour le stockage de fichier
 */
@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    /**
     * Constructeur configurant le stockage de fichier en se basant sur les propriétés définie par FileStorageProperties
     * et Sprint boot
     */
    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("Le dossier d'enregistrement ne peut pas être créé", e);
        }
    }

    /**
     * Stocker un fichier
     */
    public void storeFile(FileContent fileContent, representation.File fileMetadata) throws IOException {
        String filePath = FileStorageProperties.getUploadDir()+"/"+fileMetadata.getName();
        FileUtils.writeByteArrayToFile(new File(filePath),fileContent.decode());
    }

    /**
     * Charge le fichier en tant qu'objet Resource
     */
    public File loadFile(String fileName) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return new File(filePath.toString());
    }

    public static FileContent getFileContentByFileMetadata(representation.File fileMetadata) throws IOException {
        FileContent fileContent = null;
        java.io.File dir = new java.io.File(FileStorageProperties.getUploadDir());
        java.io.File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (java.io.File child : directoryListing) {
                if (child.getName().equals(fileMetadata.getName())) {
                    representation.File childMetadata = new representation.File(child.getName(), child.length());
                    if (childMetadata.getFileId().equals(fileMetadata.getFileId())) {
                        fileContent = new FileContent(child);
                    }
                }
            }
        }
        return fileContent;
    }

    public static representation.File getFileMetadataByFileId(String fileId) throws IOException {
        representation.File fileMetadata = null;

        List<representation.File> fileMetadataList = FilesListManager.readFiles();

        for(representation.File file : fileMetadataList){
            if(file.getFileId().equals(fileId)){
                fileMetadata = file ;
            }
        }
        return fileMetadata;
    }
}