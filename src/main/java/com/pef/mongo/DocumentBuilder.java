package com.pef.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.pef.helpers.AppLogger;
import com.pef.helpers.DataReader;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class DocumentBuilder {

    private final static Logger LOGGER = AppLogger.getAppLogger().get(Logger.GLOBAL_LOGGER_NAME);
    private DataReader dataReader = new DataReader();


    public void buildAndSaveDocument(int numberOfOutlets, int numberOfOrdersPerOutlets, MongoCollection<Document> orderCollection, MongoCollection<Document> interactionEvents) {


        String[] status = new String[]{"ACCEPTED", "AUTHORISED", "SUCCESS"};

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        for (int i = 1; i <= numberOfOutlets; ++i) {
            List<Document> documents = new ArrayList<>();
            List<Document> events = new ArrayList<>();
            int loopCounter = 1;
            Calendar cal = Calendar.getInstance();
            cal.set(2019,00,01);
            Date createdDateTime = cal.getTime();

            String outletId = UUID.randomUUID().toString();//outlets[i - 1];//
            LOGGER.info("======================================");
            LOGGER.info("building records for outlet number " + i);
            LOGGER.info("======================================");
            for (int j = 1; j <= numberOfOrdersPerOutlets; ++j) {
                LOGGER.info("------------------------------------");
                LOGGER.info("# building records for order number " + j);
                LOGGER.info("------------------------------------");
                String orderId = UUID.randomUUID().toString();

                if(j%55 == 0) {
                    cal.add(Calendar.DATE, 1);
                    createdDateTime = cal.getTime();

                }

                String orderJson = dataReader.getOrderData(outletId, orderId, dateTimeFormat.format(createdDateTime));
                documents.add(Document.parse(orderJson));
                for (int e = 1; e <= 3; ++e) {
                    LOGGER.info("* building records for event " + status[e - 1]);
                    String eventJson = dataReader.getEventData(outletId, orderId, status[e - 1], dateTimeFormat.format(createdDateTime));
                    events.add(Document.parse(eventJson));
                }
            }
            orderCollection.insertMany(documents);
            LOGGER.info("OrderData Created successfully");

            interactionEvents.insertMany(events);
            LOGGER.info("Events Created successfully");

        }
    }

    public static void createIndexs(MongoCollection<Document> orderCollection, MongoCollection<Document> interactionEvents){
        // Create order Index
        BasicDBObject index = new BasicDBObject("outletId",1);
        orderCollection.createIndex(index);
        index = new BasicDBObject("partitionKey",1);
        orderCollection.createIndex(index);
        index = new BasicDBObject("reference",1);
        orderCollection.createIndex(index);
        index = new BasicDBObject("amount",1);
        orderCollection.createIndex(index);
        index = new BasicDBObject("type",1);
        orderCollection.createIndex(index);
        index = new BasicDBObject("channel",1);
        orderCollection.createIndex(index);
        index = new BasicDBObject("createDateTime",1);
        orderCollection.createIndex(index);
        index = new BasicDBObject("paymentCards",1);
        orderCollection.createIndex(index);
        index = new BasicDBObject("merchantOrderReference",1);
        orderCollection.createIndex(index);
        index = new BasicDBObject("amount.currency",1);
        orderCollection.createIndex(index);


        // Create interactionEvents Index
        index = new BasicDBObject("partitionKey",1);
        interactionEvents.createIndex(index);
        index = new BasicDBObject("eventGroupType",1);
        interactionEvents.createIndex(index);
        index = new BasicDBObject("eventName",1);
        interactionEvents.createIndex(index);
        index = new BasicDBObject("status",1);
        interactionEvents.createIndex(index);
        index = new BasicDBObject("timestamp",1);
        interactionEvents.createIndex(index);
        index = new BasicDBObject("orderRef",1);
        interactionEvents.createIndex(index);
        index = new BasicDBObject("outletId",1);
        interactionEvents.createIndex(index);
        index = new BasicDBObject("merchantOrderReference",1);
        interactionEvents.createIndex(index);
    }

}
