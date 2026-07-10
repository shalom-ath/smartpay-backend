package com.shalom.smartpay.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(
            topics = "transaction-events",
            groupId = "smartpay-group"
    )
    public void consume(TransactionEvent event) {

        if (event.getAmount().doubleValue() > 50000) {

            System.out.println("==================================");

            System.out.println(" FRAUD ALERT ");

            System.out.println("Sender : " + event.getSenderEmail());

            System.out.println("Receiver : " + event.getReceiverEmail());

            System.out.println("Amount : " + event.getAmount());

            System.out.println("==================================");

        } else {

            System.out.println("Transaction Verified.");

        }

    }

}