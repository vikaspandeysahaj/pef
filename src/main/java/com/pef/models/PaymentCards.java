package com.pef.models;

import javax.persistence.*;
import java.sql.Blob;

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
    @Lob
    private Blob payload;
}
