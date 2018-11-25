package property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Fichier de configuration de propriété pour Spring boot
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}