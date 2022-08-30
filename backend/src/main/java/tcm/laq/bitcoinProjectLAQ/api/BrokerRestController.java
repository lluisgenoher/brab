package tcm.laq.bitcoinProjectLAQ.api;

import org.springframework.web.bind.annotation.*;
import tcm.laq.bitcoinProjectLAQ.application.BrokerController;
import tcm.laq.bitcoinProjectLAQ.application.dto.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/brokers")
public class BrokerRestController {

    private BrokerController brokerController;

    public BrokerRestController(BrokerController brokerController) {
        this.brokerController = brokerController;
    }

    @GetMapping("")
    public List<BrokerDTO> getAllBrokers(){
        return brokerController.getAllBrokers();
    }


    @GetMapping("/{id}")
    public BrokerDTO getBroker(@PathVariable String id){
        return brokerController.getBroker(id);
    }

    @PostMapping("")
    public void postBroker(@RequestBody BrokerDTO broker){
        brokerController.setNewBroker(broker);
    }

    @PutMapping("/money/{id}")
    public void updateMoney(@PathVariable String id, @RequestBody AccountDTO account){ brokerController.updateBalance(id, account); }

    @PutMapping("/bitcoins/{id}")
    public void updateBitcoins(@PathVariable String id, @RequestBody AccountDTO account){
        brokerController.updateBitcoins(id, account); }

    @PostMapping("/auctions/{id}")
    public void addAuction(@PathVariable String id, @RequestBody AuctionDTO auction){
        brokerController.addAuction(id,auction);
    }

    @GetMapping("/auctions/{id}")
    public List<AuctionDTO> getActiveAuctions(@PathVariable String id){
        return brokerController.getActiveAuctions(id);
    }

    @GetMapping("/purchases/{id}")
    public List<PurchaseDTO> getPurchaseBitcoins(@PathVariable String id){
        return brokerController.getPurchaseBitcoins(id);
    }


    @GetMapping("/me")
    public BrokerDTO getProfileBroker(Principal principal) {
        return brokerController.getProfile(principal.getName());
    }
}
