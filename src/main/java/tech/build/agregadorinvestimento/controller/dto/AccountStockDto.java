package tech.build.agregadorinvestimento.controller.dto;

public record AccountStockDto(String stockId,
                              double purchasePrice ,
                              int quantity) {
}
