package com.pef.models;

import com.fasterxml.jackson.annotation.JsonRawValue;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.SQLException;

@Entity
public class PaymentCards {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String expiry;
    private String cardholderName;
    private String maskedPanNumber;
    private String paymentMethod;
    private String paymentType;
    private String paymentReference;
    private String cardScheme;
    private String  payload;

}
