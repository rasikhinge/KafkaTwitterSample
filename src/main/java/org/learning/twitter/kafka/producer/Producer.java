package org.learning.twitter.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {
    public static final String TOPIC = "first_topic";
    private final KafkaProducer<String, String> producer;

    public Producer() {
        Properties properties = new Properties();
        String bootstrapServers = "127.0.0.1:9092";
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        producer = new KafkaProducer<>(properties);

    }

    public void send(String msg) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, msg);
        producer.send(record);

    }

    public void close() {
        producer.close();
    }
}
