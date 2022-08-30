package tcm.laq.bitcoinProjectLAQ.application.dto;

import tcm.laq.bitcoinProjectLAQ.domain.Transaction;

import java.util.List;
import java.util.UUID;

public class AccountDTO {
    private double balance;
    private double bitcoins;
    private List<TransactionDTO> transactions;

    public AccountDTO(){
        this.balance=0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBitcoins() {
        return bitcoins;
    }

    public void setBitcoins(double bitcoins) {
        this.bitcoins = bitcoins;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
