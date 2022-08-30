package tcm.laq.bitcoinProjectLAQ.application.daos;

import tcm.laq.bitcoinProjectLAQ.application.dto.AccountDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.AuctionDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidderDTO;

import java.util.List;

public interface BidderDAO {
    BidderDTO addBidder(BidderDTO bidderDTO, AccountDTO accountDTO);

    List<BidderDTO> getBidders();

    BidderDTO getBidder(String id);

    List<AuctionDTO> getActiveAuctions();

    void doABid(String id, BidDTO bidDTO);

    void updateBalance(String id, AccountDTO account);

    void updateBlockedMoney(String id, String blockedMoney);

    List<BidDTO> getUserBids(String id);

    BidderDTO getProfile(String name);

    /************************SCHEDULER************************/
    List<AuctionDTO> getEndedAuctions();
    List<BidDTO> getBidsFromAuctions(String auctionID);

    void giveBTCToBidder(String bidderID, double bitcoins);
    void removeBlockedMoneyFromBidder(String bidderID, double money);
    void giveMoneyToBroker(String brokerID, double money);
    void returnBlockedMoneyToBidder(String bidderID, double money);
    void returnBTCToBroker(String broker_id, double bitcoins);

    public void acceptBid(String bidID);


}
