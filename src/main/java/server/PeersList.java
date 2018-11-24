package server;

import representation.Peer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PeersList {
    public static void savePeers(List<Peer> peers){
        try {
            FileOutputStream out = new FileOutputStream("shared/listePairs");
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(peers);
            oos.flush();
        } catch (Exception e) {
            System.out.println("Problem serializing: " + e);
        }
    }

    public static List<Peer> readPeers(){
        List<Peer> peers = new ArrayList<>();
        try {
            FileInputStream in = new FileInputStream("shared/listePairs");
            ObjectInputStream ois = new ObjectInputStream(in);
            peers = (List) (ois.readObject());
            ois.close();
            in.close();
            return peers;
        } catch (Exception e) {
            System.out.println("Problem serializing: " + e);
        }
        return peers;
    }
}
