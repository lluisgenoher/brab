package tcm.laq.bitcoinProjectLAQ.application.scheduler;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tcm.laq.bitcoinProjectLAQ.application.dto.AuctionDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidDTO;
import tcm.laq.bitcoinProjectLAQ.persistence.BidderDAO;

import java.util.ArrayList;
import java.util.List;

@Component
public class Scheduler {
    BidderDAO bidderDAO;
    public Scheduler(BidderDAO bidderDAO) {
        this.bidderDAO = bidderDAO;
    }

    @Scheduled(cron="0 01 00 * * *")
    public void doSomething() {
        List<AuctionDTO> ended = bidderDAO.getEndedAuctions();
        for (AuctionDTO a: ended) {
            ArrayList<BidDTO> bids = bidderDAO.getBidsFromAuctions(a.getAuction_id());
            for(BidDTO b: bids){
                if(a.getBitcoins() >= b.getBitcoins()){
                    bidderDAO.giveBTCToBidder(b.getBidder_id(),b.getBitcoins());
                    bidderDAO.removeBlockedMoneyFromBidder(b.getBidder_id(),b.getMoneyBid());
                    bidderDAO.giveMoneyToBroker(a.getBroker_id(),b.getMoneyBid());
                    a.setBitcoins(a.getBitcoins()-b.getBitcoins());
                    bidderDAO.acceptBid(b.getBID_ID());
                }
                else{
                    bidderDAO.returnBlockedMoneyToBidder(b.getBidder_id(),b.getMoneyBid());
                }
            }
            if(a.getBitcoins()>0) bidderDAO.returnBTCToBroker(a.getBroker_id(),a.getBitcoins());
        }
    }
}
