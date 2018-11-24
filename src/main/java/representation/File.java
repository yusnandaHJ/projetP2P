package representation;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class File {
    private String fileid;
    private String name;
    private long size;

    public File() {
    }

    public File(String name) throws IOException {
        Path file = FileSystems.getDefault().getPath("shared",name);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

        this.name = file.getFileName().toString();
        this.size = attr.size();
        this.fileid = DigestUtils.sha256Hex(this.name+this.size);

    }

    public File(String name, long size) {
        this.name = name;
        this.size = size;
        this.fileid = DigestUtils.sha256Hex(this.name+this.size);
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
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
}

