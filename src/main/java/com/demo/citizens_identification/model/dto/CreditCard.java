package com.demo.citizens_identification.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreditCard {

    private String number;
    private String userName;
    private String dateTo;
    private String cvv;

    @Builder
    public CreditCard(String number, String userName, String dateTo, String cvv) {
        this.number = number;
        this.userName = userName;
        this.dateTo = dateTo;
        this.cvv = cvv;
    }
}
