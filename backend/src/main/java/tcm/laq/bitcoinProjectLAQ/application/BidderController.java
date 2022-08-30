package tcm.laq.bitcoinProjectLAQ.application;

import org.springframework.stereotype.Component;
import tcm.laq.bitcoinProjectLAQ.application.daos.BidderDAO;
import tcm.laq.bitcoinProjectLAQ.application.dto.AccountDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.AuctionDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidderDTO;

import java.util.List;
import java.util.UUID;

@Component
public class BidderController {
    BidderDAO bidderDAO;

    public BidderController(BidderDAO bidderDAO){
        this.bidderDAO=bidderDAO;
    }

    public void setNewBidder(BidderDTO bidder){
        bidder.setId(UUID.randomUUID().toString());
        AccountDTO account = new AccountDTO();
        bidder.setAccount(account);
        bidderDAO.addBidder(bidder, account);
    }

    public BidderDTO getBidder(String id){
        return bidderDAO.getBidder(id);
    }

    public List<BidderDTO> getAllBidders(){
        return bidderDAO.getBidders();
    }

    public List<AuctionDTO> getActiveAuctions() {
        return bidderDAO.getActiveAuctions();
    }

    public void doABid(String id, BidDTO bidDTO) {
        bidDTO.setBID_ID(UUID.randomUUID().toString());
        bidderDAO.doABid(id,bidDTO);
    }

    public void updateBalance(String id, AccountDTO account) {
        bidderDAO.updateBalance(id, account);
    }

    public void updateBlockedMoney(String id, String blockedMoney) {
        bidderDAO.updateBlockedMoney(id, blockedMoney);
    }

    public List<BidDTO> getUserBids(String id) {
       return  bidderDAO.getUserBids(id);
    }

    public BidderDTO getProfile(String email) {
        return bidderDAO.getProfile(email);
    }
}
