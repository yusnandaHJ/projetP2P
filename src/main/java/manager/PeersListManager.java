package manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import representation.Peer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PeersListManager {

    private static final java.io.File peerPersistenceFile = new java.io.File("./src/main/resources/listePairs.json");

    public static void savePeers(List<Peer> peers) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(peerPersistenceFile, peers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    //public static void savePeers(List<Peer> peers){
    //    try {
    //        FileOutputStream out = new FileOutputStream("./shared/listePairs.json");
    //        ObjectOutputStream oos = new ObjectOutputStream(out);
    //        oos.writeObject(peers);
    //        oos.flush();
    //    } catch (Exception e) {
    //        System.out.println("Problem serializing: " + e);
    //    }
    //}
//
    //public static List<Peer> readPeers(){
    //    List<Peer> peers = new ArrayList<>();
    //    try {
    //        FileInputStream in = new FileInputStream("./shared/listePairs.json");
    //        ObjectInputStream ois = new ObjectInputStream(in);
    //        peers = (List) (ois.readObject());
    //        ois.close();
    //        in.close();
    //        return peers;
    //    } catch (Exception e) {
    //        System.out.println("Problem serializing: " + e);
    //    }
    //    return peers;
    //}
}
