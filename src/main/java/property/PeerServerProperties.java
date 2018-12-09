package property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server")
public class PeerServerProperties {
    private static String address;
    private static String port;

    public static String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
