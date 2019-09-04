package com.pef.helpers;

import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;

@Component
public class DataReader {

    public String getEventData(String outletId, String orderId, String status, String createdDateTime, String fileName) {
        try {
            ClassLoader classLoader = DataReader.class.getClassLoader();
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

    public String getOrderData(String outletId, String orderId, String createdDateTime, String fileName) {
        try {
            ClassLoader classLoader = DataReader.class.getClassLoader();
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
