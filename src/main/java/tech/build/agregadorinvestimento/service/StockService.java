package tech.build.agregadorinvestimento.service;

import org.springframework.stereotype.Service;
import tech.build.agregadorinvestimento.controller.dto.CreateStockDto;
import tech.build.agregadorinvestimento.entity.Stock;
import tech.build.agregadorinvestimento.repository.StockRepository;

@Service
public class StockService {
    private StockRepository stockRepository;
    public   StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }
    public void createStock(CreateStockDto createStockDto){
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );
        stockRepository.save(stock);
    }
}
