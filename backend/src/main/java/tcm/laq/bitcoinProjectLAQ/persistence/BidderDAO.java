package tcm.laq.bitcoinProjectLAQ.persistence;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tcm.laq.bitcoinProjectLAQ.application.dto.*;
import tcm.laq.bitcoinProjectLAQ.application.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BidderDAO implements tcm.laq.bitcoinProjectLAQ.application.daos.BidderDAO {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<BidderDTO> bidderRowMapper = (resultSet, i) -> {
        BidderDTO bidder = new BidderDTO();

        bidder.setId(resultSet.getString("id"));
        bidder.setName(resultSet.getString("name"));
        bidder.setSurname(resultSet.getString("surname"));
        bidder.setPassword(resultSet.getString("password"));
        bidder.setEmail(resultSet.getString("email"));
        return bidder;
    };
    private final RowMapper<AccountDTO> accountRowMapper = (resultSet, i) -> {
        AccountDTO accountdto = new AccountDTO();

        accountdto.setBalance(resultSet.getDouble("balance"));
        accountdto.setBitcoins(resultSet.getDouble("bitcoins"));
        return accountdto;
    };
    private final RowMapper<AuctionDTO> auctionRowMapper = (resultSet, i) -> {
        AuctionDTO auctiondto = new AuctionDTO();

        auctiondto.setAuction_id(resultSet.getString("id"));
        auctiondto.setBroker_id(resultSet.getString("broker_id"));
        auctiondto.setStartDate(resultSet.getDate("startdate"));
        auctiondto.setEndDate(resultSet.getDate("enddate"));
        auctiondto.setBitcoins(resultSet.getDouble("bitcoins"));
        auctiondto.setStartBid(resultSet.getDouble("startbid"));
        return auctiondto;
    };

    private final RowMapper<BidDTO> bidRowMapper = (resultSet, i) -> {
        BidDTO biddto = new BidDTO();

        biddto.setBID_ID(resultSet.getString("bid_id"));
        biddto.setBitcoins(resultSet.getDouble("bitcoins"));
        biddto.setMoneyBid(resultSet.getDouble("moneybid"));
        biddto.setBidAccepted(resultSet.getBoolean("bidaccepted"));
        biddto.setAuction_id(resultSet.getString("auction_id"));
        biddto.setBidder_id(resultSet.getString("bidder_id"));
        biddto.setAdmin_id(null);
        return biddto;
    };

    public BidderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSetExtractorImpl<BidderDTO> biddersRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(BidderDTO.class);

    ResultSetExtractorImpl<AuctionDTO> auctionsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(AuctionDTO.class);

    ResultSetExtractorImpl<AccountDTO> accountsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(AccountDTO.class);

    ResultSetExtractorImpl<BidDTO> bidsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(BidDTO.class);

    @Override
    public BidderDTO addBidder(BidderDTO bidderDTO, AccountDTO accountDTO) {
        final var query = "INSERT INTO absuser(id, name, surname, password, email) values(?,?,?,?,?)";
        jdbcTemplate.update(query, bidderDTO.getId(), bidderDTO.getName(), bidderDTO.getSurname(), bidderDTO.getPassword(), bidderDTO.getEmail());
        final var query1 = "INSERT INTO bidder(id, blocked_money) values(?,?)";
        jdbcTemplate.update(query1,bidderDTO.getId(),bidderDTO.getBlockedMoney());
        final var query2 = "INSERT INTO account(absuser_id, balance, bitcoins) values(?,?,?)";
        jdbcTemplate.update(query2,bidderDTO.getId(),accountDTO.getBalance(),accountDTO.getBitcoins());
        final var query3 = "insert into authorities(email, u_role) values(?,?)";
        jdbcTemplate.update(query3,bidderDTO.getEmail(),"ROLE_BIDDER");

        return this.getBidder(bidderDTO.getId());
    }

    @Override
    public List<BidderDTO> getBidders() {
        final var query = "select a.id as id, a.name as name, a.surname as surname, a.password as password, b.blocked_money as blocked_money, a.email as email, ac.balance as account_balance, ac.bitcoins as account_bitcoins " +
                            " from absuser a join bidder b on a.id=b.id" +
                            " left join account ac on a.id=ac.absuser_id";
        return jdbcTemplate.query(query,biddersRowMapper);
    }

    @Override
    public BidderDTO getBidder(String id) {
        final var query = "select a.id as id, a.name as name, a.surname as surname, a.password as password, b.blocked_money as blocked_money, a.email as email, ac.balance as account_balance, ac.bitcoins as account_bitcoins " +
                            " from absuser a join bidder b on a.id=b.id " +
                            " left join account ac on a.id=ac.absuser_id " +
                            " where a.id=?";
        List<BidderDTO> result;
        try {
            result = jdbcTemplate.query(query, biddersRowMapper, id);
            return result.get(0);
        }catch(EmptyResultDataAccessException e){
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public List<AuctionDTO> getActiveAuctions() {
        final var query = "select auction_id, startDate, endDate, bitcoins, startBid from auction where endDate>sysdate and startDate<sysdate";

        return jdbcTemplate.query(query,auctionsRowMapper);
    }

    @Override
    public void doABid(String id, BidDTO bidDTO) {
        final var query = "INSERT INTO bid(bid_id, moneybid, bidaccepted, auction_auction_id,admin_id, bidder_id, bitcoins) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(query,bidDTO.getBID_ID(), bidDTO.getMoneyBid(),false,id,null,bidDTO.getBidder_id(),bidDTO.getBitcoins());
    }

    @Override
    public void updateBalance(String id, AccountDTO account) {
        final var query = "update account set balance = ? where absuser_id = ?";
        jdbcTemplate.update(query,account.getBalance(), id);
    }

    @Override
    public void updateBlockedMoney(String id, String blockedMoney) {
        final var query = "update bidder set blocked_money = ? where id = ?";
        jdbcTemplate.update(query, blockedMoney, id);
    }

    @Override
    public List<BidDTO> getUserBids(String id) {
        final var query = "select b.bitcoins as bitcoins,b.moneybid as moneybid ,b.bidaccepted as bidaccepted, bidder_id as bidder_id, auction_auction_id as auction_id from bid b " +
                "join auction a on b.auction_auction_id=a.auction_id " +
                "where b.bidder_id= ? and a.enddate<sysdate";
        return jdbcTemplate.query(query,bidsRowMapper,id);
    }

    @Override
    public BidderDTO getProfile(String email) {
        final var query = "select a.id as id, a.name as name, a.surname as surname, a.password as password, b.blocked_money as blocked_money, a.email as email, ac.balance as account_balance, ac.bitcoins as account_bitcoins " +
                " from absuser a join bidder b on a.id=b.id " +
                " left join account ac on a.id=ac.absuser_id " +
                " where a.email=?";
        List<BidderDTO> result;
        try {
            result = jdbcTemplate.query(query, biddersRowMapper, email);
            return result.get(0);
        }catch(EmptyResultDataAccessException e){
            throw new UserNotFoundException(email);
        }
    }

    /********************************************************

    ************************SCHEDULER************************

    **********************************************************/


    @Override
    public List<AuctionDTO> getEndedAuctions() {
        final var query = "select auction_id, startDate, endDate, bitcoins, startBid, broker_id from auction where endDate<sysdate";

        return jdbcTemplate.query(query,auctionsRowMapper);
    }

    @Override
    public ArrayList<BidDTO> getBidsFromAuctions(String auctionID) {
        final var query = "select bid_id as bid_id, bitcoins as bitcoins, moneybid as moneybid, bidaccepted as bidaccepted, auction_auction_id as auction_id, bidder_id as bidder_id " +
                        " from bid where auction_auction_id = ? " +
                        " order by moneybid/bitcoins desc";

        return (ArrayList<BidDTO>) jdbcTemplate.query(query,bidsRowMapper,auctionID);
    }

    @Override
    public void giveBTCToBidder(String bidderID, double bitcoins) {
        final var query = "update account set bitcoins = bitcoins + ? where absuser_id = ?";
        jdbcTemplate.update(query,bitcoins, bidderID);
    }

    @Override
    public void removeBlockedMoneyFromBidder(String bidderID, double money) {
        final var query = "update bidder set blocked_money = blocked_money - ? where absuser_id = ?";
        jdbcTemplate.update(query,money, bidderID);
    }

    @Override
    public void giveMoneyToBroker(String brokerID, double money) {
        final var query = "update account set balance = balance + ? where absuser_id = ?";
        jdbcTemplate.update(query,money, brokerID);
    }

    @Override
    public void returnBlockedMoneyToBidder(String bidderID, double money) {
        final var query = "update account set balance = balance + ? where absuser_id = ?";
        jdbcTemplate.update(query,money, bidderID);

        final var query2 = "update bidder set blocked_money = blocked_money - ? where absuser_id = ?";
        jdbcTemplate.update(query2,money, bidderID);
    }


    public void returnBTCToBroker(String broker_id, double bitcoins) {
        final var query = "update account set bitcoins = bitcoins + ? where absuser_id = ?";
        jdbcTemplate.update(query, bitcoins, broker_id);
    }

    public void acceptBid(String bidID) {
        final var query = "update bid set bidaccepted = 1 where bid_id = ?";
        jdbcTemplate.update(query, bidID);
    }
}
