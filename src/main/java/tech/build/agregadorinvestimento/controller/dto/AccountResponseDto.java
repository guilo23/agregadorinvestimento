package tech.build.agregadorinvestimento.controller.dto;

public record AccountResponseDto(String accountId,
                                 String username,
                                 String accountName,
                                 double balance) {
}
