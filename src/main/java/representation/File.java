package representation;

public class File {
    String fileid;
    String name;
    int size;


    public File(String fileid, String name, int size) {
        this.fileid = fileid;
        this.name = name;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
