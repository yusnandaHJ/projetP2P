package entity;

public class File {
    private String fileid;
    private long size;
    private String name;

    public File() {
    }

    public File(String fileid, long size, String name) {
        this.fileid = fileid;
        this.size = size;
        this.name = name;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
