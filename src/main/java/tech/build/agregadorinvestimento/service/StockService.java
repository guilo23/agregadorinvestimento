package tech.build.agregadorinvestimento.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.build.agregadorinvestimento.Client.BrapiClient;
import tech.build.agregadorinvestimento.controller.dto.CreateStockDto;
import tech.build.agregadorinvestimento.entity.Stock;
import tech.build.agregadorinvestimento.repository.StockRepository;

@Service
public class StockService {
    @Value("#{environment.TOKEN}")
    private String TOKEN;
    private StockRepository stockRepository;
    private BrapiClient brapiClient;

    public StockService( BrapiClient brapiClient, StockRepository stockRepository) {
        this.brapiClient = brapiClient;
        this.stockRepository = stockRepository;
    }
    public void createStock(CreateStockDto createStockDto){
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description(),
                31.25,
                10
        );
        stockRepository.save(stock);
    }
    public Stock searchStock(String stockId){

        var response = brapiClient.getQuote(TOKEN, stockId);
        var stock = new Stock(
                stockId,
                response.results().getFirst().shortName(),
                response.results().getFirst().regularMarketPrice(),
                response.results().getFirst().regularMarketChangePercent()
        );
        stockRepository.save(stock);
        return stock;
    }
}
