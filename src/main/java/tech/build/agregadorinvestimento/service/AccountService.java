package tech.build.agregadorinvestimento.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tech.build.agregadorinvestimento.Client.BrapiClient;
import tech.build.agregadorinvestimento.controller.dto.AccountStockResponsedto;
import tech.build.agregadorinvestimento.controller.dto.AccountStockDto;
import tech.build.agregadorinvestimento.controller.dto.updateAccountStockDto;
import tech.build.agregadorinvestimento.entity.*;
import tech.build.agregadorinvestimento.repository.AccountRepository;
import tech.build.agregadorinvestimento.repository.AccountStockRepository;
import tech.build.agregadorinvestimento.repository.StockRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String TOKEN;
    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;
    private BrapiClient brapiClient;
    private StockService stockService;

    public AccountService(AccountRepository accountRepository,
                          StockRepository stockRepository,
                          AccountStockRepository accountStockRepository,
                          BrapiClient brapiClient,
                          StockService stockService) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
        this.stockService = stockService;
    }

    public AccountStock associateStock(String accountId, AccountStockDto dto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockService.searchStock(dto.stockId());

        // DTO -> ENTITY
        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(
                id,
                dto.purchasePrice(),
                stock,
                account,
                dto.quantity()
        );
        account.setBalance(account.getBalance() + (stock.getPrice()*dto.quantity()));
        accountStockRepository.save(entity);
        return entity;
    }
    public List<AccountStockResponsedto> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(as ->
                        new AccountStockResponsedto(
                                as.getStock().getStockId(),
                                as.getStock().getDescription(),
                                as.getQuantity(),
                                as.getPurchasePrice(),
                                as.getStock().getPrice()
                        ))
                .toList();
    }
    private double getTotal(Integer quantity, String stockId) {

        var response = brapiClient.getQuote(TOKEN, stockId);

        var price = response.results().getFirst().regularMarketPrice();

        return quantity * price;
    }

    public void updateAccountStock(String accountId, updateAccountStockDto accountStockDto) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<AccountStock> accountStocks = account.getAccountStocks();
        boolean updated = false;

        for (AccountStock existingStock : accountStocks) {
            if (existingStock.getId() != null &&
                    existingStock.getId().getStockId() != null &&
                    existingStock.getId().getStockId().equals(accountStockDto.stock_id())) {

                existingStock.setQuantity(accountStockDto.quantity());
                updated = true;
                break;
            }
        }


        if (updated) {
            accountRepository.save(account);
        } else {
            System.out.println("AccountStock não encontrado para atualização.");
        }
    }

    @Transactional
    public void deleteAccountStock(String accountId, AccountStockDto accountStock) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<AccountStock> accountStocks = account.getAccountStocks();

        boolean removed = accountStocks.removeIf(stock -> stock.getId().getStockId().equals(accountStock.stockId()));

        if (removed) {
            accountRepository.save(account); // Salvar a Account para persistir a remoção da lista
        } else {
            System.out.println("AccountStock não encontrado para remoção.");
        }
    }
    public List<Account> listUsuarios() {
        return accountRepository.findAll();
    }
}