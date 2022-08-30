package tcm.laq.bitcoinProjectLAQ.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tcm.laq.bitcoinProjectLAQ.application.BidderController;
import tcm.laq.bitcoinProjectLAQ.application.dto.AccountDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.AuctionDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidderDTO;

import java.security.Principal;
import java.util.List;

@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/bidders")
@RestController
public class BidderRestController {
    BidderController bidderController;

    public BidderRestController(BidderController bidderController){
        this.bidderController=bidderController;
    }


    @GetMapping("")
    public List<BidderDTO> getAllBidders(){
        return bidderController.getAllBidders();
    }

    @GetMapping("/{id}")
    public BidderDTO getBidder(@PathVariable String id){
        return bidderController.getBidder(id);
    }

    @PostMapping("")
    public void postBidder(@RequestBody BidderDTO bidder){
        bidderController.setNewBidder(bidder);
    }

    @GetMapping("/active-auctions")
    public List<AuctionDTO> getActiveAuctions(){
        return bidderController.getActiveAuctions();
    }

    @PostMapping("/bid/{id}") //ID de la auction.
    public void doABid(@PathVariable String id, @RequestBody BidDTO bidDTO){
        bidderController.doABid(id,bidDTO);
    }

    @PutMapping("/money/{id}")
    public void updateMoney(@PathVariable String id, @RequestBody AccountDTO account){ bidderController.updateBalance(id, account); }

    @PutMapping("/blocked-money/{id}/{blockedMoney}")
    public void updateBlockedMoney(@PathVariable String id, @PathVariable String blockedMoney){ bidderController.updateBlockedMoney(id, blockedMoney); }

    @GetMapping("/bid/{id}")
    public List<BidDTO> getUserBids(@PathVariable String id){
        return bidderController.getUserBids(id);
    }

    @GetMapping("/me")
    public BidderDTO getProfileBidder(Principal principal) {
        return bidderController.getProfile(principal.getName());
    }
}

