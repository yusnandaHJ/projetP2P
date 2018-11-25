package client;

import org.springframework.web.client.RestTemplate;
import representation.Peer;

public class PeerClient {

    private static final String uri = "http://localhost:8080/peers";

    /*
    GET /peers
     */
    public static void getPeers()
    {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
    }

    /*
    POST /peers
    J'ai consideré qu'on envoyait seulement les infos de notre serveur (notre URL et id)
     */
    public static void registerPeers(String id, String url)
    {
        Peer self = new Peer(id,url);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject( uri, self, Peer.class);

        //System.out.println(result);
    }

    /*
    DELETE /peers/url
     */
    public static void unregisterPeer(String url)
    {
        RestTemplate restTemplate = new RestTemplate();


        //System.out.println(result);
    }

}
