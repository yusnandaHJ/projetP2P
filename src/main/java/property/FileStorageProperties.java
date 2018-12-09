package property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Fichier de configuration de propriété pour Spring boot
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private static String uploadDir;
    private static String filePersistenceJson;
    private static String peerPersistenceJson;

    public static String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public static void updateUploadDir(String newUploadDir){
        uploadDir = newUploadDir;
    }

    public static String getFilePersistenceJson() {
        return filePersistenceJson;
    }

    public void setFilePersistenceJson(String filePersistenceJson) {
        this.filePersistenceJson = filePersistenceJson;
    }

    public static String getPeerPersistenceJson() {
        return peerPersistenceJson;
    }

    public void setPeerPersistenceJson(String peerPersistenceJson) {
        this.peerPersistenceJson = peerPersistenceJson;
    }
}