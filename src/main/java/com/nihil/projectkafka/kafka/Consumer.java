package com.nihil.projectkafka.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

	Logger logger = LoggerFactory.getLogger(Consumer.class);

	private Properties properties = new Properties();

	private static String TOPIC = "second_topic";

	private static String groupId = "my-group-2";

	private String serverIp;

	private KafkaConsumer<String, String> consumer;

	Consumer(@Value("${kafka.server.ip}") String ip) {
		this.serverIp = ip;
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				serverIp);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
				"earliest");

		consumer = new KafkaConsumer<String, String>(properties);

		consumer.subscribe(Collections.singleton(TOPIC));
	}

	public List<ConsumerData> pollForData() {
		ConsumerRecords<String, String> records = consumer
				.poll(Duration.ofMillis(100));

		List<ConsumerData> dataList = new ArrayList<>();

		for (ConsumerRecord<String, String> record : records) {
			dataList.add(prepareConsumerData(record));
			logger.info(" Key : {} Value : {}", record.key(), record.value());
			logger.info("Partition : {} Offset : {}", record.partition(),
					record.offset());
		}
		return dataList;
	}

	private ConsumerData prepareConsumerData(
			ConsumerRecord<String, String> record) {
		ProducerACK ack = new ProducerACK(record.topic(), record.partition(),
				record.offset(), record.timestamp(), record.key());
		return new ConsumerData(record.value(), ack);
	}
}
