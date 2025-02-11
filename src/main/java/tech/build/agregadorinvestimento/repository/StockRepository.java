package tech.build.agregadorinvestimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.build.agregadorinvestimento.entity.AccountStock;
import tech.build.agregadorinvestimento.entity.AccountStockId;
import tech.build.agregadorinvestimento.entity.Stock;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, String> {
}
