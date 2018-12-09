package server;

import manager.PeersListManager;
import org.springframework.web.bind.annotation.*;
import property.PeerServerProperties;
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
    public void registerPeer(@RequestBody Peer peer) {
        List<Peer> peersList = PeersListManager.readPeers();
        Peer registeringPeer = new Peer(peer.getUrl());
        if(peersList.stream().noneMatch(o -> o.getUrl().equals(registeringPeer.getUrl()))) {
            peersList.add(registeringPeer);
        }
        PeersListManager.savePeers(peersList);
    }

    /**
     * Rest DELETE Handler pour se déconnecter d'un pair
     */
    @DeleteMapping("/peers/{url}")
    public void unregisterPeer(@PathVariable String url) {
        List<Peer> peersList = PeersListManager.readPeers();
        for(Iterator<Peer> peerIterator = peersList.iterator(); peerIterator.hasNext();){
            Peer peer = peerIterator.next();
            if(peer.getUrl().equals(url)){
                peerIterator.remove();
            }
        }
        PeersListManager.savePeers(peersList);
    }
}
