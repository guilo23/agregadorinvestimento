package tech.build.agregadorinvestimento.controller.dto;

public record AccountStockResponsedto(String stockId,
                                      String description,
                                      int quantity,
                                      double purchasePrice,
                                      double price) {

}
