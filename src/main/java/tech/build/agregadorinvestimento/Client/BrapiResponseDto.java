package tech.build.agregadorinvestimento.Client;

import java.util.List;

public record BrapiResponseDto(List<StockDto> results) {
}
