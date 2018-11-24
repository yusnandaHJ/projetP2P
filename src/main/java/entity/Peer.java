package entity;

public class Peer {
    private String peerid;
    private String url;

    public Peer() {
    }

    public Peer(String peerid, String url) {
        this.peerid = peerid;
        this.url = url;
    }

    public String getPeerid() {
        return peerid;
    }

    public void setPeerid(String peerid) {
        this.peerid = peerid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
