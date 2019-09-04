package com.pef.models;

import com.fasterxml.jackson.annotation.JsonRawValue;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<PaymentCards> paymentCards;

    @OneToOne(cascade = CascadeType.ALL)
    private Amount amount;
    private String billingAddress;
    private String orderSummary;



}
