package server;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeerController {

    @GetMapping("/peers")
    public void getPeers() {
        //TODO
    }

    @PostMapping("/peers")
    public void registerPeer() {
        //TODO
    }

    @DeleteMapping("/peers/{peerId}")
    public void unregisterPeer() {
        //TODO
    }
}
