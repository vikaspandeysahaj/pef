package com.pef;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pef.helpers.AppLogger;
import com.pef.mongo.DocumentBuilder;
import com.pef.mysql.QueryBuilder;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@SpringBootApplication
@Service
public class Application implements WebMvcConfigurer {

    private final static Logger LOGGER = AppLogger.getAppLogger().get(Logger.GLOBAL_LOGGER_NAME);
    private static int numberOfOutlets = 30;
    private static int numberOfOrdersPerOutlets = 10000;


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
//        runMongoSpike();
        runMysqlSpike();
    }


    public static void runMongoSpike() throws IOException {

        // Creating a Mongo client
         MongoClient mongo = new MongoClient("localhost", 27017);

//        MongoCredential credential = MongoCredential.createScramSha256Credential("root", "mongospike", "equalexperts123".toCharArray());
//        MongoClient mongo = new MongoClient(new ServerAddress("52.21.218.229",27017), Collections.singletonList(credential));

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
        DocumentBuilder documentBuilder = new DocumentBuilder();
        documentBuilder.buildAndSaveDocument(numberOfOutlets, numberOfOrdersPerOutlets, orderCollection, interactionEvents);

        //com.pef.mongo.DocumentBuilder.createIndexs(orderCollection, interactionEvents);

        LOGGER.info("============SEED ENDED===========");
        LOGGER.info(new Date().toString());
        LOGGER.info("====================================");
        mongo.close();
        System.exit(0);

    }

    @Autowired
    private  static QueryBuilder queryBuilder;

    @Autowired
    public void setSomeThing(QueryBuilder queryBuilder1){
        queryBuilder = queryBuilder1;
    }

    private static void runMysqlSpike() throws IOException {


        LOGGER.info("============SEED STARTED===========");
        LOGGER.info(new Date().toString());
        LOGGER.info("====================================");

        queryBuilder.buildAndSaveData(numberOfOutlets, numberOfOrdersPerOutlets);

        LOGGER.info("============SEED ENDED===========");
        LOGGER.info(new Date().toString());
        LOGGER.info("====================================");
        System.exit(0);

    }
}
