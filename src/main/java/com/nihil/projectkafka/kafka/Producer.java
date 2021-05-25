package com.nihil.projectkafka.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Producer {

	private static String TOPIC = "second_topic";

	private KafkaProducer<String, String> producer;

	private Properties properties = new Properties();

	private Logger logger = LoggerFactory.getLogger(Producer.class);

	private String serverIp;

	Producer(@Value("${kafka.server.ip}") String ip) {
		this.serverIp = ip;
		System.out.println("Server Ip : " + this.serverIp);
		this.properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				this.serverIp);
		this.properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class.getName());
		this.properties.setProperty(
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class.getName());

		producer = new KafkaProducer<String, String>(properties);
	}

	public ProducerACK sendData(String value, String key) {
		ProducerACK ack = new ProducerACK();
		ack.setKey(key);
		Future<RecordMetadata> meta = this.producer
				.send(createProducerRecord(TOPIC, key, value), new Callback() {
					@Override
					public void onCompletion(RecordMetadata metadata,
							Exception exception) {
						if (exception == null) {
							ack.setTopic(metadata.topic());
							ack.setOffset(metadata.offset());
							ack.setPartition(metadata.partition());
							ack.setTimestamp(metadata.timestamp());
							logger.info("Acknowledgement : {}", ack);
						} else {
							logger.error("Error - {}", exception);
						}
					}
				});
		try {
			meta.get(); // simply forcing to return valid ack made it
						// synchronous
		} catch (InterruptedException | ExecutionException e) {
			logger.error("Error - {}", e);
		}
		return ack;
	}

	private ProducerRecord<String, String> createProducerRecord(String topic,
			String key, String value) {
		return new ProducerRecord<String, String>(topic, key, value);
	}
}
