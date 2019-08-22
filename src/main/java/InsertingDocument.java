import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


public class InsertingDocument {

    private final static Logger LOGGER = AppLogger.getAppLogger().get(Logger.GLOBAL_LOGGER_NAME);

    private static int numberOfOutlets = 5;
    private static int numberOfOrdersPerOutlets = 10;

    public static void main( String args[] ) throws IOException {

        // Creating a Mongo client
        MongoClient mongo = new MongoClient("localhost", 27017);

        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "niDB",
                "password".toCharArray());
        LOGGER.info("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("niDB");

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("orders");
        LOGGER.info("Collection orders selected successfully");

        MongoCollection<Document> interactionEvents = database.getCollection("interactionEvents");
        LOGGER.info("Collection orders selected successfully");

        LOGGER.info("============SEED STARTED===========");
        LOGGER.info(new Date().toString());
        LOGGER.info("====================================");

        CustomDocuments documentsToInsert = DocumentBuilder.getOrderDocument(numberOfOutlets, numberOfOrdersPerOutlets);
        List<Document> documents = documentsToInsert.orderDocuments;
        collection.insertMany(documents);
        LOGGER.info("Order Created successfully");

        List<Document> eventDocuments = documentsToInsert.eventsDocuments;
        interactionEvents.insertMany(eventDocuments);
        LOGGER.info("Events Created successfully");

        LOGGER.info("============SEED ENDED===========");
        LOGGER.info(new Date().toString());
        LOGGER.info("====================================");


    }

}
