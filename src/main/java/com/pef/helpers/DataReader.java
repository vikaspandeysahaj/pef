package com.pef.helpers;

import com.pef.mongo.DocumentBuilder;

import java.io.File;
import java.nio.file.Files;

public class DataReader {

    public static String getEventData(String outletId, String orderId, String status, String createdDateTime) {
        try {
            String fileName = "events.json";
            ClassLoader classLoader = DocumentBuilder.class.getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            String orderJson = new String(Files.readAllBytes(file.toPath()));
            orderJson = orderJson.replace("RemoveMeWithActualReference", orderId);
            orderJson = orderJson.replace("RemoveMeWithActualOutletId", outletId);
            orderJson = orderJson.replace("RemoveMeWithActualStatus", status);
            orderJson = orderJson.replace("RemoveMeWithActualTimeStamp", createdDateTime);
            return orderJson;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getOrderData(String outletId, String orderId, String createdDateTime) {
        try {
            String fileName = "order.json";
            ClassLoader classLoader = DocumentBuilder.class.getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            String orderJson = new String(Files.readAllBytes(file.toPath()));
            orderJson = orderJson.replace("RemoveMeWithActualReference", orderId);
            orderJson = orderJson.replace("RemoveMeWithActualOutletId", outletId);
            orderJson = orderJson.replace("RemoveMeWithActualCreatedDateTime", createdDateTime);
            return orderJson;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
