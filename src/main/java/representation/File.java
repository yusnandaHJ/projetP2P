package representation;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class File {
    String fileid;
    String name;
    long size;

    public File() {
    }

    public File(String fileid, String name) throws IOException {
        Path file = FileSystems.getDefault().getPath("shared",name);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

        this.fileid = fileid;
        this.name = file.getFileName().toString();
        this.size = attr.size();
    }

    public File(String fileid, String name, long size) {
        this.fileid = fileid;
        this.name = name;
        this.size = size;
    }

    /*public File(String path){
        Path file = FileSystems.getDefault();
    }*/

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
