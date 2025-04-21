package tech.build.agregadorinvestimento.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.build.agregadorinvestimento.controller.dto.AccountStockDto;
import tech.build.agregadorinvestimento.controller.dto.AccountStockResponsedto;
import tech.build.agregadorinvestimento.controller.dto.updateAccountStockDto;
import tech.build.agregadorinvestimento.entity.Account;
import tech.build.agregadorinvestimento.entity.AccountStock;
import tech.build.agregadorinvestimento.entity.Stock;
import tech.build.agregadorinvestimento.entity.User;
import tech.build.agregadorinvestimento.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<AccountStock> associateStock(@PathVariable("accountId") String accountId,
                                                       @RequestBody AccountStockDto accountStockDto){
       AccountStock accountStock =  accountService.associateStock(accountId,accountStockDto);
        return ResponseEntity.ok().body(accountStock);
    }
    @GetMapping("/{accountId}/stocksList")
    public ResponseEntity<List<AccountStockResponsedto>>associateStockList(@PathVariable("accountId") String accountId){
        var stock = accountService.listStocks(accountId);
        return ResponseEntity.ok(stock);
    }
    @PutMapping("/investments/{accountId}")
    public ResponseEntity<Void> updateAccountStock(@PathVariable String accountId,
                                   @RequestBody updateAccountStockDto accountStock){
        accountService.updateAccountStock(accountId,accountStock);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/investments/{accountName}")
    public ResponseEntity<Void> deleteAccountStock(@PathVariable String accountId,
                                   @RequestBody AccountStockDto accountStockDto){
        accountService.deleteAccountStock(accountId,accountStockDto);
        return  ResponseEntity.ok().build();
    }
    @GetMapping("/allAccounts")
    public ResponseEntity<List<Account>> listUsers() {
        var accounts = accountService.listUsuarios();
        return ResponseEntity.ok(accounts);
    }
}
