package tcm.laq.bitcoinProjectLAQ.application;


import org.springframework.stereotype.Component;
import tcm.laq.bitcoinProjectLAQ.application.daos.BrokerDAO;
import tcm.laq.bitcoinProjectLAQ.application.dto.*;

import java.util.List;
import java.util.UUID;

@Component
public class BrokerController {

    BrokerDAO brokerDAO;

    public BrokerController(BrokerDAO brokerDAO) {
        this.brokerDAO = brokerDAO;
    }

    public List<BrokerDTO> getAllBrokers() {
        return brokerDAO.getBrokers();
    }

    public BrokerDTO getBroker(String id) {
        return brokerDAO.getBroker(id);
    }

    public void setNewBroker(BrokerDTO broker) {
        broker.setId(UUID.randomUUID().toString());
        AccountDTO account = new AccountDTO();
        broker.setAccount(account);
        brokerDAO.addBroker(broker,account);
    }
    public void updateBalance(String id, AccountDTO account){
        brokerDAO.updateBalance(id, account);
    }
    public void updateBitcoins(String id, AccountDTO account){
        brokerDAO.updateBitcoins(id, account);
    }

    public void addAuction(String id, AuctionDTO auction) {
        auction.setBroker_id(id);
        brokerDAO.addAuction(auction);
    }

    public List<AuctionDTO> getActiveAuctions(String id) {
        return brokerDAO.getActiveAuctions(id);
    }

    public List<PurchaseDTO> getPurchaseBitcoins(String id) {
        return brokerDAO.getPurchaseBitcoins(id);
    }

    public BrokerDTO getProfile(String email) {
        return brokerDAO.getProfile(email);
    }
}
