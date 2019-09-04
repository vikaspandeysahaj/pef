package com.pef.models;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;
import java.util.List;

@Entity
public class OrderData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String reference;
    private String action;
    private String language;
    private String type;
    private String status;
    private String channel;
    private Date createDateTime;
    private String outletId;
    private String emailAddress;
    private boolean aborted;
    private String referrer;
    private String lastEventName;

    @OneToMany
    private List<PaymentCards> paymentCards;

    @OneToOne
    private Amount amount;

    @Lob
    private Blob billingAddress;

    @Lob
    private Blob orderSummary;



}
