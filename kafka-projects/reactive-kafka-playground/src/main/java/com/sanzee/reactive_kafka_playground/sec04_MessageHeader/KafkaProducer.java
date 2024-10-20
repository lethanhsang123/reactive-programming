package com.sanzee.reactive_kafka_playground.sec04_MessageHeader;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.Map;

/*
    goal: To produce and consume millions events
 */
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    public static void main(String[] args) {
        var producerConfig = Map.<String, Object>of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );

        var options = SenderOptions.<String, String>create(producerConfig);   // Is a parameter that controls how many unacknowledged records (in-flight records) can be processed concurrently.

        var flux = Flux.range(1, 1_000_000).map(KafkaProducer::createSenderRecord);

        var sender = KafkaSender.create(options);
        sender.send(flux)
                .doOnNext(r -> log.info("Correlation id: {}", r.correlationMetadata()))
                .doOnComplete(sender::close)
                .subscribe();

    }

    private static SenderRecord<String, String, String> createSenderRecord(Integer i) {
        var headers = new RecordHeaders();
        headers.add("client-id", "some-client".getBytes());
        headers.add("tracing-id", "123".getBytes());
        var pr = new ProducerRecord<>("order-events", i.toString(), "order-" + i);
        return SenderRecord.create(pr, pr.key());
    }


}
