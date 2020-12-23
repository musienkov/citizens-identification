package com.demo.citizens_identification.model.entity;

import com.demo.citizens_identification.model.dto.CreditCard;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserEntity {

    private UUID id;
    private String hash;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private List<String> emails;
    private String phoneNumber;
    private String additionalPhoneNumber;
    private List<CreditCard> creditCards;

    @Builder
    public UserEntity(UUID id, String hash, String name, String surname, Date dateOfBirth, List<String> emails, String phoneNumber, String additionalPhoneNumber, List<CreditCard> creditCards) {
        this.id = id;
        this.hash = hash;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.emails = emails;
        this.phoneNumber = phoneNumber;
        this.additionalPhoneNumber = additionalPhoneNumber;
        this.creditCards = creditCards;
    }
}
