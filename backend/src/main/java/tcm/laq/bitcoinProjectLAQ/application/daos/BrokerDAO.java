package tcm.laq.bitcoinProjectLAQ.application.daos;

import tcm.laq.bitcoinProjectLAQ.application.dto.*;

import java.util.List;

public interface BrokerDAO {
    BrokerDTO addBroker(BrokerDTO bidderDTO, AccountDTO account);

    List<BrokerDTO> getBrokers();

    BrokerDTO getBroker(String id);

    void updateBalance(String id, AccountDTO account);

    void updateBitcoins(String id, AccountDTO account);

    void addAuction(AuctionDTO auction);

    List<AuctionDTO> getActiveAuctions(String id);

    List<PurchaseDTO> getPurchaseBitcoins(String id);

    BrokerDTO getProfile(String email);
}
