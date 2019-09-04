package com.pef.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String eventGroupType;
    private String eventName;
    private String status;
    private Date timestamp;
    private String orderRef;
    private String payload;
    private String outletId;
}
