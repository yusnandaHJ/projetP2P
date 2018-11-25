package manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import representation.Peer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant le fichier de persistence des listes des pairs découvert dans un réseau
 */
public class PeersListManager {

    private static final java.io.File peerPersistenceFile = new java.io.File("./src/main/resources/listePairs.json");

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

}
