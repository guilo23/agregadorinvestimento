package tech.build.agregadorinvestimento.entity;

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

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    @PrimaryKeyJoinColumn
    private BillingAdrees adrees;

    @OneToMany(mappedBy = "account")
    private List<AccountStock> accountStocks;

    public Account() {

    }

    public Account(UUID accountId, String description, User user, BillingAdrees adrees, List<AccountStock> accountStocks) {
        this.accountId = accountId;
        this.description = description;
        this.user = user;
        this.adrees = adrees;
        this.accountStocks = accountStocks;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
