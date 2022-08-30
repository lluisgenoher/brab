package tcm.laq.bitcoinProjectLAQ.application.dto;

import tcm.laq.bitcoinProjectLAQ.domain.Auction;
import tcm.laq.bitcoinProjectLAQ.domain.Purchase;

import java.util.List;
import java.util.UUID;

public class BrokerDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private List<Auction> activeAuctions;
    private List<Purchase> purchases;
    private AccountDTO account;

    public BrokerDTO(){
        this.id=(UUID.randomUUID()).toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Auction> getActiveAuctions() {
        return activeAuctions;
    }

    public void setActiveAuctions(List<Auction> activeAuctions) {
        this.activeAuctions = activeAuctions;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

}
