package org.primshits.currency_exchange.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO {
    private String code;
    private String fullName;
    private String sign;
}
