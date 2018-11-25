package server;

import manager.PeersListManager;
import org.springframework.web.bind.annotation.*;
import representation.Peer;

import java.util.Iterator;
import java.util.List;

@RestController
public class PeerController {

    @GetMapping("/peers")
    public List<Peer> getPeersList() {
        return PeersListManager.readPeers();
    }

    @PostMapping("/peers")
    public Peer registerPeer() {
        List<Peer> peersList = PeersListManager.readPeers();
        Peer registeringPeer = new Peer("toto", "bgTA");
        if(peersList.stream().noneMatch(o -> o.getPeerId().equals(registeringPeer.getPeerId()))) {
            peersList.add(registeringPeer);
        }
        PeersListManager.savePeers(peersList);
        return registeringPeer;
    }

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
