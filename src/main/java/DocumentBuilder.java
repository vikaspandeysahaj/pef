import org.bson.Document;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DocumentBuilder {

    public static CustomDocuments getOrderDocument(int numberOfOutlets, int numberOfOrdersPerOutlets) {

        String[] status = new String[]{"asd","asd","asd"};
        CustomDocuments customDocuments = new CustomDocuments();
        List<Document> documents = new ArrayList<>();
        List<Document> events = new ArrayList<>();

        for (int i = 1; i<=numberOfOutlets; ++i){
            String outletId = UUID.randomUUID().toString();
            for (int j = 1; j<=numberOfOrdersPerOutlets; ++j){
                String orderId = UUID.randomUUID().toString();
                String orderJson = getData(outletId, orderId);
                documents.add(Document.parse(orderJson));

                for(int e=1; e<=3;++e){
                    String eventJson = getEventData(outletId, orderId, status[e-1]);
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
}
