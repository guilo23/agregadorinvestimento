package tech.build.agregadorinvestimento.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tv_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "accountName")
    private String accountName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_Id")
    @JsonBackReference
    private User user;

    private String userName;

    private double balance;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    @PrimaryKeyJoinColumn
    private BillingAdrees adrees;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<AccountStock> accountStocks;

    public Account() {

    }
    public Account(UUID accountId, String accountName, User user, String userName, double balance, List<AccountStock> accountStocks) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.user = user;
        this.userName = userName;
        this.balance = balance;
        this.accountStocks = accountStocks;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BillingAdrees getAdrees() {
        return adrees;
    }

    public void setAdrees(BillingAdrees adrees) {
        this.adrees = adrees;
    }

    public List<AccountStock> getAccountStocks() {
        return accountStocks;
    }

    public void setAccountStocks(List<AccountStock> accountStocks) {
        this.accountStocks = accountStocks;
    }
}



//        "description":"napoleo conta de investimento",
//        "Street":"Rua napoleon",
//        "number": "543"
//        }
//
//        "username":"napoleo",
//        "email":"napo@gmail.com",
//        "password": "123"
//        }
