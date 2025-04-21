package tech.build.agregadorinvestimento.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.build.agregadorinvestimento.controller.dto.CreateStockDto;
import tech.build.agregadorinvestimento.entity.Stock;
import tech.build.agregadorinvestimento.service.StockService;

@RestController
@RequestMapping("/v1/stocks")
public class StockController{
    private StockService stockService;

    public StockController(StockService stockService){
        this.stockService = stockService;
    }
    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto){
        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{stockId}")
    public ResponseEntity<Stock> searchStock(@PathVariable  String stockId){
       var stock = stockService.searchStock(stockId);

       return ResponseEntity.ok().body(stock);
    }
}
