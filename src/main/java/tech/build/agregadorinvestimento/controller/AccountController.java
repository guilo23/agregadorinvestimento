package tech.build.agregadorinvestimento.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.build.agregadorinvestimento.controller.dto.AccountStockDto;
import tech.build.agregadorinvestimento.controller.dto.AccountStockResponsedto;
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
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                              @RequestBody AccountStockDto accountStockDto){
        accountService.associateStock(accountId,accountStockDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{accountId}/stocksList")
    public ResponseEntity<List<AccountStockResponsedto>>associateStockList(@PathVariable("accountId") String accountId){
        var stock = accountService.listStocks(accountId);
        return ResponseEntity.ok(stock);
    }
}
