package tcm.laq.bitcoinProjectLAQ.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {

    private double balance;
    private double bitcoins;
    private List<Transaction> transactions;

    public Account(){
        double balance;
        this.transactions = new ArrayList<Transaction>();
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addMoney(double money) {
        this.balance+=money;
    }
}
