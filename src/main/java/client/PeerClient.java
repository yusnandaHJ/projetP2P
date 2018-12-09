package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import representation.Peer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe des fonctions gérant les pairs côté client
 */
public class PeerClient {

    /**
     * Fonction de gestion du GET de l'URL /peers côté client
     */
    public static List<Peer> getPeers(String peerUrl) {
        RestTemplate restTemplate = new RestTemplate();
        List<Peer> peers = new ArrayList<>();
        String result = restTemplate.getForObject(peerUrl + "/peers", String.class);

        try {
            peers = new ObjectMapper().readValue(result, new TypeReference<List<Peer>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(result);
        return peers;
    }

    /**
     * Fonction de gestion du POST de l'URL /peers côté client
     * Nous avons considéré que nous envoyons seulements les informations de notre serveur (notre URL et id)
     */
    public static void registerPeers(String peerUrl,String url) {
        Peer self = new Peer(url);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(peerUrl + "/peers", self, Peer.class);
    }

    /**
     * Fonction de gestion du DELETE de l'URL /peers/url côté client
     */
    public static void unregisterPeer(String peerUrl, String url) {
        String desturi = peerUrl + "/peers"+"/{url}";
        Map<String, String> params = new HashMap<>();
        params.put("url",url);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(desturi,params);
    }

}
