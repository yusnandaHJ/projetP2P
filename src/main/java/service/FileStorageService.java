package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import property.FileStorageProperties;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Impossible de charger le fichier \"" + fileName + "\"", e);
        }
    }

    /**
     * Charge le fichier en tant qu'objet Resource
     */
    public File loadFile(String fileName) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return new File(filePath.toString());
    }
}