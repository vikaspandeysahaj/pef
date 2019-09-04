import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


public class InsertingDocument {

    private final static Logger LOGGER = AppLogger.getAppLogger().get(Logger.GLOBAL_LOGGER_NAME);


    private static int numberOfOutlets = 30;
    private static int numberOfOrdersPerOutlets = 20000;


    public static void main( String args[] ) throws IOException {

        // Creating a Mongo client
       // MongoClient mongo = new MongoClient("localhost", 27017);
        MongoCredential credential = MongoCredential.createScramSha256Credential("root", "mongospike", "equalexperts123".toCharArray());
        MongoClient mongo = new MongoClient(new ServerAddress("52.21.218.229",27017), Collections.singletonList(credential));


        LOGGER.info("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("mongospike");

        // Retrieving a collection
        MongoCollection<Document> orderCollection = database.getCollection("orders");
        LOGGER.info("Collection orders selected successfully");

        MongoCollection<Document> interactionEvents = database.getCollection("interactionEvents");
        LOGGER.info("Collection orders selected successfully");

        LOGGER.info("============SEED STARTED===========");
        LOGGER.info(new Date().toString());
        LOGGER.info("====================================");

        DocumentBuilder.buildAndSaveDocument(numberOfOutlets, numberOfOrdersPerOutlets, orderCollection, interactionEvents);


        //DocumentBuilder.createIndexs(orderCollection, interactionEvents);

        LOGGER.info("============SEED ENDED===========");
        LOGGER.info(new Date().toString());
        LOGGER.info("====================================");
        mongo.close();
        System.exit(0);

    }

}
