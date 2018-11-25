package server;

import manager.PeersListManager;
import org.springframework.web.bind.annotation.*;
import representation.Peer;

import java.util.Iterator;
import java.util.List;

/**
 * Classe controller des requêtes rest concernant les fichiers côté serveur
 */
@RestController
public class PeerController {

    /**
     * Rest GET Handler pour récupérer la liste des pairs découverts
     */
    @GetMapping("/peers")
    public List<Peer> getPeersList() {
        return PeersListManager.readPeers();
    }

    /**
     * Rest POST Handler pour envoyer une liste de pairs découverts
     */
    @PostMapping("/peers")
    public Peer registerPeer() {
        List<Peer> peersList = PeersListManager.readPeers();
        Peer registeringPeer = new Peer("testpeerid", "testURL");
        if(peersList.stream().noneMatch(o -> o.getPeerId().equals(registeringPeer.getPeerId()))) {
            peersList.add(registeringPeer);
        }
        PeersListManager.savePeers(peersList);
        return registeringPeer;
    }

    /**
     * Rest DELETE Handler pour se déconnecter d'un pair
     */
    @DeleteMapping("/peers/{peerId}")
    public void unregisterPeer(@PathVariable String peerId) {
        List<Peer> peersList = PeersListManager.readPeers();
        for(Iterator<Peer> peerIterator = peersList.iterator(); peerIterator.hasNext();){
            Peer peer = peerIterator.next();
            if(peer.getPeerId().equals(peerId)){
                peerIterator.remove();
            }
        }
        PeersListManager.savePeers(peersList);
    }
}
