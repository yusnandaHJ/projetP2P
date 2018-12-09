package representation;

import org.apache.commons.codec.digest.DigestUtils;
import property.FileStorageProperties;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Classe réprensentant nos entités "Fichier" au sens du projetP2P
 * Attention à ne pas confondre avec java.io.File
 */
public class File {
    private String fileId;
    private String name;
    private long size;
    private String storageDirectoryPath = FileStorageProperties.getUploadDir();

    public File() {
    }

    public File(String name) throws IOException {
        Path file = FileSystems.getDefault().getPath(FileStorageProperties.getUploadDir(), name);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

        this.name = file.getFileName().toString();
        this.size = attr.size();
        this.fileId = DigestUtils.sha256Hex(this.name + this.size);

    }

    public File(String name, long size) {
        this.name = name;
        this.size = size;
        this.fileId = DigestUtils.sha256Hex(this.name + this.size);
    }

    @Override
    public int hashCode()
    {
        return fileId.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        return this.fileId.equals(((File)o).getFileId());
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return name + " - " + size + " octets";
    }
}

