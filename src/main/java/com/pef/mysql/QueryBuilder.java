package com.pef.mysql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.pef.helpers.AppLogger;
import com.pef.helpers.DataReader;
import com.pef.models.Event;
import com.pef.models.OrderData;
import com.pef.models.repo.AmountRepository;
import com.pef.models.repo.EventRepository;
import com.pef.models.repo.OrderDataRepository;
import com.pef.models.repo.PaymentCardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
public class QueryBuilder {

    private final static Logger LOGGER = AppLogger.getAppLogger().get(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private OrderDataRepository orderDataRepository;
    @Autowired
    private AmountRepository amountRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private PaymentCardsRepository paymentCardsRepository;
    @Autowired
    private DataReader dataReader;

    public void buildAndSaveData(int numberOfOutlets, int numberOfOrdersPerOutlets) throws IOException {
        String[] status = new String[]{"ACCEPTED", "AUTHORISED", "SUCCESS"};
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        for (int i = 1; i <= numberOfOutlets; ++i) {

            List<OrderData> orders = new ArrayList<>();
            List<Event> events = new ArrayList<>();
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

                String orderJson = dataReader.getOrderData(outletId, orderId, dateTimeFormat.format(createdDateTime), "order_mysql.json");

                orders.add(getOrder(orderJson));
                for (int e = 1; e <= 3; ++e) {
                    LOGGER.info("* building records for event " + status[e - 1]);
                    String eventJson = dataReader.getEventData(outletId, orderId, status[e - 1], dateTimeFormat.format(createdDateTime), "events_mysql.json");
                    events.add(getEvent(eventJson));
                }
            }
            orderDataRepository.saveAll(orders);
            LOGGER.info("OrderData Created successfully");

            eventRepository.saveAll(events);
            LOGGER.info("Events Created successfully");

        }

    }

    private Event getEvent(String eventJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        Event event = mapper.readValue(eventJson, Event.class);
        return event;
    }

    private OrderData getOrder(String orderJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        OrderData orderData = mapper.readValue(orderJson, OrderData.class);
        return orderData;
    }
}

