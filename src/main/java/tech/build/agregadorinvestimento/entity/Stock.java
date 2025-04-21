package tech.build.agregadorinvestimento.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="tb_stock")
public class Stock {

    @Id
    @Column(name="stock_id")
    private String stockId;

    @Column(name = "descrption")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "variaçao_percentual")
    double variacaoPercentual;

    public Stock() {
    }

    public Stock(String stockId, String description,double price,double variacaoPercentual) {
        this.stockId = stockId;
        this.description = description;
        this.price = price;
        this.variacaoPercentual=variacaoPercentual;
    }

    public String getStockId() {
        return stockId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
