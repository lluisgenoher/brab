package tcm.laq.bitcoinProjectLAQ.persistence;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tcm.laq.bitcoinProjectLAQ.application.dto.*;
import tcm.laq.bitcoinProjectLAQ.application.exception.UserNotFoundException;

import java.util.Date;
import java.util.List;

@Repository
public class BrokerDAO  implements tcm.laq.bitcoinProjectLAQ.application.daos.BrokerDAO {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<BrokerDTO> brokerRowMapper = (resultSet, i) -> {
        BrokerDTO brokerdto = new BrokerDTO();

        brokerdto.setId(resultSet.getString("id"));
        brokerdto.setName(resultSet.getString("name"));
        brokerdto.setSurname(resultSet.getString("surname"));
        brokerdto.setPassword(resultSet.getString("password"));
        brokerdto.setEmail(resultSet.getString("email"));
        return brokerdto;
    };

    private final RowMapper<AuctionDTO> auctionRowMapper = (resultSet, i) -> {
        AuctionDTO auctiondto = new AuctionDTO();

        auctiondto.setAuction_id(resultSet.getString("auction_id"));
        auctiondto.setBroker_id(resultSet.getString("broker_id"));
        auctiondto.setStartDate(resultSet.getDate("startdate"));
        auctiondto.setEndDate(resultSet.getDate("enddate"));
        auctiondto.setBitcoins(resultSet.getDouble("bitcoins"));
        auctiondto.setStartBid(resultSet.getDouble("startbid"));
        return auctiondto;
    };

    private final RowMapper<AccountDTO> accountRowMapper = (resultSet, i) -> {
        AccountDTO accountdto = new AccountDTO();

        accountdto.setBalance(resultSet.getDouble("balance"));
        accountdto.setBitcoins(resultSet.getDouble("bitcoins"));
        return accountdto;
    };

    private final RowMapper<PurchaseDTO>  purchaseRowMapper = (resultSet, i) -> {
        PurchaseDTO purchasedto = new PurchaseDTO();

        purchasedto.setId(resultSet.getString("purchase_id"));
        purchasedto.setAmountOfBitcoins(resultSet.getDouble("amountOfBitcoins"));
        purchasedto.setAmountOfMoney(resultSet.getDouble("amountOfMoney"));
        purchasedto.setBuyDate(resultSet.getDate("buydate"));

        return purchasedto;
    };

    public BrokerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSetExtractorImpl<BrokerDTO> brokersRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(BrokerDTO.class);

    ResultSetExtractorImpl<AuctionDTO> auctionsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(AuctionDTO.class);

    ResultSetExtractorImpl<PurchaseDTO> purchasesRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(PurchaseDTO.class);

    @Override
    public BrokerDTO addBroker(BrokerDTO brokerDTO, AccountDTO accountDTO) {
        final var query = "INSERT INTO absuser(id, name, surname, password, email) values(?,?,?,?,?)";
        jdbcTemplate.update(query, brokerDTO.getId(), brokerDTO.getName(), brokerDTO.getSurname(), brokerDTO.getPassword(), brokerDTO.getEmail());
        final var query1 = "INSERT INTO broker(id) values(?)";
        jdbcTemplate.update(query1,brokerDTO.getId());
        final var query2 = "INSERT INTO account(absuser_id, balance, bitcoins) values(?,?,?)";
        jdbcTemplate.update(query2,brokerDTO.getId(),accountDTO.getBalance(),accountDTO.getBitcoins());
        final var query3 = "insert into authorities(email, u_role) values(?,?)";
        jdbcTemplate.update(query3,brokerDTO.getEmail(),"ROLE_BROKER");

        return this.getBroker(brokerDTO.getId());
    }

    @Override
    public List<BrokerDTO> getBrokers() {
        final var query = "select a.id as id, a.name as name, a.surname as surname, a.password as password, a.email as email, ac.balance as account_balance, ac.bitcoins as account_bitcoins " +
                            " from absuser a join broker b on a.id=b.id " +
                            " left join account ac on a.id=ac.absuser_id";
        return jdbcTemplate.query(query,brokersRowMapper);
    }

    @Override
    public BrokerDTO getBroker(String id) {
        final var query = "select a.id as id, a.name as name, a.surname as surname, a.password as password, a.email as email, ac.balance as account_balance, ac.bitcoins as account_bitcoins " +
                " from absuser a join broker b on a.id=b.id " +
                " left join account ac on a.id=ac.absuser_id" +
                " where a.id=?";
        List<BrokerDTO> result;
        try {
            result = jdbcTemplate.query(query, brokersRowMapper, id);;
            return result.get(0);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public void updateBalance(String id, AccountDTO account) {
        final var query = "update account set balance = ? where absuser_id = ?";
        jdbcTemplate.update(query,account.getBalance(), id);
    }

    @Override
    public void updateBitcoins(String id, AccountDTO account) {
        final var queryBitcoins = "select absuser_id, balance, bitcoins from account where absuser_id = ?";
        AccountDTO accountBitcoins = jdbcTemplate.queryForObject(queryBitcoins,accountRowMapper,id);
        System.out.println(accountBitcoins.getBalance());
        System.out.print(account.getBalance());

        final var query = "update account set bitcoins = ? where absuser_id = ?";
        jdbcTemplate.update(query,account.getBitcoins(), id);

        if(accountBitcoins.getBitcoins()<account.getBitcoins()){
            final var queryPurchase = "insert into purchase(purchase_id, amountOfBitcoins, amountOfMoney, buydate, broker_id, admin_id) values(?,?,?,?,?,?)";
            PurchaseDTO purchaseDTO = new PurchaseDTO();
            jdbcTemplate.update(queryPurchase,purchaseDTO.getId(),account.getBitcoins()-accountBitcoins.getBitcoins(),accountBitcoins.getBalance()-account.getBalance(), new Date(), id, null);
        }

    }

    @Override
    public void addAuction(AuctionDTO auction) {
        final var query = "INSERT INTO auction(auction_id,admin_id, broker_id, startdate, enddate, bitcoins, startbid) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(query, auction.getAuction_id(), null, auction.getBroker_id(), auction.getStartDate(), auction.getEndDate(), auction.getBitcoins(), auction.getStartBid());
    }

    @Override
    public List<AuctionDTO> getActiveAuctions(String id) {
        final var query = "select auction_id, broker_id, startdate, enddate, bitcoins, startbid " +
                " from auction " +
                " where broker_id = ?";
        return jdbcTemplate.query(query,auctionsRowMapper,id);
    }

    @Override
    public List<PurchaseDTO> getPurchaseBitcoins(String id) {
        final var query = "select purchase_id, amountOfBitcoins, amountOfMoney, buydate " +
                " from purchase " +
                " where broker_id = ?" +
                "order by 4 desc";
        return jdbcTemplate.query(query,purchasesRowMapper,id);
    }

    @Override
    public BrokerDTO getProfile(String email) {
        final var query = "select a.id as id, a.name as name, a.surname as surname, a.password as password, a.email as email, ac.balance as account_balance, ac.bitcoins as account_bitcoins " +
                " from absuser a join broker b on a.id=b.id " +
                " left join account ac on a.id=ac.absuser_id " +
                " where a.email=?";
        List<BrokerDTO> result;
        try {
            result = jdbcTemplate.query(query, brokersRowMapper, email);
            return result.get(0);
        }catch(EmptyResultDataAccessException e){
            throw new UserNotFoundException(email);
        }
    }
}
