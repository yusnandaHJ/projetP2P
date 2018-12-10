package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import property.PeerServerProperties;
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
     */
    public static boolean registerPeers(String peerUrl) {
        Peer self = new Peer("http://"+PeerServerProperties.getAddress()+":"+PeerServerProperties.getPort());
        RestTemplate restTemplate = new RestTemplate();
        //Ajout d'un timeout en cas de non disponibilité du serveur
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(1000);

        try{
            //On envoie au serveur les informations sur notre pair
            restTemplate.postForObject(peerUrl + "/peers", self, Peer.class);
        }
        catch (RestClientException e){
            return false;
        }

        return true;
    }

    /**
     * Fonction de gestion du DELETE de l'URL /peers/url côté client
     */
    public static boolean unregisterPeer(String peerUrl) {
        Peer self = new Peer("http://"+PeerServerProperties.getAddress()+":"+PeerServerProperties.getPort());
        RestTemplate restTemplate = new RestTemplate();
        //Ajout d'un timeout en cas de non disponibilité du serveur
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(300);
        String desturi = peerUrl + "/peers"+"/{url}";
        Map<String, String> params = new HashMap<>();
        params.put("url",self.getUrl());

        try{
            //On envoie au serveur les informations pour supprimer le pair
            restTemplate.delete(desturi,params);
        }
        catch (RestClientException e){
            return false;
        }
        return true;
    }

}
