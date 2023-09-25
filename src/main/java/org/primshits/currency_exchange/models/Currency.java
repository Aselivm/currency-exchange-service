package org.primshits.currency_exchange.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    private int id;
    private String code;
    private String fullName;
    private String sign;
}
