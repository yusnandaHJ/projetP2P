package property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Fichier de configuration de propriété pour Spring boot
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private static String uploadDir;

    public static String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public static void updateUploadDir(String newUploadDir){
        uploadDir = newUploadDir;
    }
}