package manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import property.FileStorageProperties;
import representation.Peer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant le fichier de persistence des listes des pairs découvert dans un réseau
 */
public class PeersListManager {

    private static final java.io.File peerPersistenceFile = new java.io.File(FileStorageProperties.getPeerPersistenceJson());

    /**
     * Enregistre la liste de fichier dans le fichier listePairs.json
     */
    public static void savePeers(List<Peer> peers) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(peerPersistenceFile, peers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lit la liste de fichier dans le fichier listePairs.json
     */
    public static List<Peer> readPeers() {
        List<Peer> peers = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            peers = mapper.readValue(peerPersistenceFile, new TypeReference<List<Peer>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peers;
    }

    /**
     * Permet de vérifier l'integrité d'une URL
     * @param peerUrl url à verifier
     * @return true si l'url est valide, false sinon
     */
    public static boolean isUrlValid(String peerUrl){
        try
        {
            URL url = new URL(peerUrl);
            url.toURI();
            return true;
        } catch (Exception exception)
        {
            return false;
        }
    }

}
