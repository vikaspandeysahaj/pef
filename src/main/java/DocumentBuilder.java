import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class DocumentBuilder {

    private final static Logger LOGGER = AppLogger.getAppLogger().get(Logger.GLOBAL_LOGGER_NAME);

    public static CustomDocuments getOrderDocument(int numberOfOutlets, int numberOfOrdersPerOutlets) {

        String[] status = new String[]{"ACCEPTED", "AUTHORISED", "SUCCESS"};
        CustomDocuments customDocuments = new CustomDocuments();
        List<Document> documents = new ArrayList<>();
        List<Document> events = new ArrayList<>();
        for (int i = 1; i <= numberOfOutlets; ++i) {
            String outletId = UUID.randomUUID().toString();
            LOGGER.info("======================================");
            LOGGER.info("building records for outlet number " + i);
            LOGGER.info("======================================");
            for (int j = 1; j <= numberOfOrdersPerOutlets; ++j) {
                LOGGER.info("------------------------------------");
                LOGGER.info("# building records for order number " + j);
                LOGGER.info("------------------------------------");
                String orderId = UUID.randomUUID().toString();
                String orderJson = getData(outletId, orderId);
                documents.add(Document.parse(orderJson));
                for (int e = 1; e <= 3; ++e) {
                    LOGGER.info("* building records for event " + status[e - 1]);
                    String eventJson = getEventData(outletId, orderId, status[e - 1]);
                    events.add(Document.parse(eventJson));
                }
            }
        }
        customDocuments.orderDocuments = documents;
        customDocuments.eventsDocuments = events;
        return customDocuments;

    }

    private static String getEventData(String orderId, String outletId, String status) {
        try {
            String fileName = "events.json";
            ClassLoader classLoader = DocumentBuilder.class.getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            String orderJson = new String(Files.readAllBytes(file.toPath()));
            orderJson = orderJson.replace("RemoveMeWithActualReference", orderId);
            orderJson = orderJson.replace("RemoveMeWithActualOutletId", outletId);
            orderJson = orderJson.replace("RemoveMeWithActualStatus", status);
            return orderJson;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getData(String outletId, String orderId) {
        try {
            String fileName = "order.json";
            ClassLoader classLoader = DocumentBuilder.class.getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            String orderJson = new String(Files.readAllBytes(file.toPath()));
            orderJson = orderJson.replace("RemoveMeWithActualReference", orderId);
            orderJson = orderJson.replace("RemoveMeWithActualOutletId", outletId);
            return orderJson;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void buildAndSaveDocument(int numberOfOutlets, int numberOfOrdersPerOutlets, MongoCollection<Document> orderCollection, MongoCollection<Document> interactionEvents) {


        String[] status = new String[]{"ACCEPTED", "AUTHORISED", "SUCCESS"};
        for (int i = 1; i <= numberOfOutlets; ++i) {
            List<Document> documents = new ArrayList<>();
            List<Document> events = new ArrayList<>();

            String outletId = UUID.randomUUID().toString();
            LOGGER.info("======================================");
            LOGGER.info("building records for outlet number " + i);
            LOGGER.info("======================================");
            for (int j = 1; j <= numberOfOrdersPerOutlets; ++j) {
                LOGGER.info("------------------------------------");
                LOGGER.info("# building records for order number " + j);
                LOGGER.info("------------------------------------");
                String orderId = UUID.randomUUID().toString();
                String orderJson = getData(outletId, orderId);
                documents.add(Document.parse(orderJson));
                for (int e = 1; e <= 3; ++e) {
                    LOGGER.info("* building records for event " + status[e - 1]);
                    String eventJson = getEventData(outletId, orderId, status[e - 1]);
                    events.add(Document.parse(eventJson));
                }
            }
            orderCollection.insertMany(documents);
            LOGGER.info("Order Created successfully");

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
