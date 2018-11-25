package client;

import org.springframework.web.client.RestTemplate;
import representation.Peer;

/**
 * Classe des fonctions gérant les pairs côté client
 */
public class PeerClient {

    private static final String uri = "http://localhost:8080/peers";

    /**
     * Fonction de gestion du GET de l'URL /peers côté client
     */
    public static void getPeers() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
    }

    /**
     * Fonction de gestion du POST de l'URL /peers côté client
     * Nous avons considéré que nous envoyons seulements les informations de notre serveur (notre URL et id)
     */
    public static void registerPeers(String id, String url) {
        Peer self = new Peer(id, url);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(uri, self, Peer.class);

        //System.out.println(result);
    }

    /**
     * Fonction de gestion du DELETE de l'URL /peers/url côté client
     */
    public static void unregisterPeer(String url) {
        RestTemplate restTemplate = new RestTemplate();

        //System.out.println(result);
    }

}
