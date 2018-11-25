package representation;

/**
 * Classe réprensentant nos entités "Pair" au sens du projetP2P
 */
public class Peer implements java.io.Serializable {
    private String peerid;
    private String url;

    public Peer() {
    }

    public Peer(String peerid, String url) {
        this.peerid = peerid;
        this.url = url;
    }

    public String getPeerId() {
        return peerid;
    }

    public void setPeerId(String id) {
        this.peerid = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
