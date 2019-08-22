import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;


public class InsertingDocument {

    private static int numberOfOutlets = 5;
    private static int numberOfOrdersPerOutlets = 10;

    public static void main( String args[] ) {


        // Creating a Mongo client
        MongoClient mongo = new MongoClient("localhost", 27017);

        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "niDB",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("niDB");

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("orders");
        System.out.println("Collection orders selected successfully");

        MongoCollection<Document> interactionEvents = database.getCollection("interactionEvents");
        System.out.println("Collection orders selected successfully");

        CustomDocuments documentsToInsert = DocumentBuilder.getOrderDocument(numberOfOutlets, numberOfOrdersPerOutlets);
        List<Document> documents = documentsToInsert.orderDocuments;
        collection.insertMany(documents);
        System.out.println("Order Created successfully");

        List<Document> eventDocuments = documentsToInsert.eventsDocuments;
        interactionEvents.insertMany(eventDocuments);
        System.out.println("Events Created successfully");



    }

}
