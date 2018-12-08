package representation;

/**
 * Classe réprensentant nos entités "Pair" au sens du projetP2P
 */
public class Peer implements java.io.Serializable {
    private String url;

    public Peer() {
    }

    public Peer(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Peer{" +
                "url='" + url + '\'' +
                '}';
    }
}
