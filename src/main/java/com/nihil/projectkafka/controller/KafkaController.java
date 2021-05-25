package com.nihil.projectkafka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nihil.projectkafka.kafka.Consumer;
import com.nihil.projectkafka.kafka.ConsumerData;
import com.nihil.projectkafka.kafka.Producer;
import com.nihil.projectkafka.kafka.ProducerACK;
import com.nihil.projectkafka.kafka.ProducerRequest;

@RestController
public class KafkaController {

	@Autowired
	Producer producer;

	@Autowired
	Consumer consumer;

	@RequestMapping("/")
	public String index() {
		return "You are hitting the server!";
	}

	@PostMapping("/sendToKafka")
	public ProducerACK sendToKafka(@RequestBody ProducerRequest data) {
		return producer.sendData(data.getValue(), data.getKey());
	}

	@GetMapping("/runConsumer")
	public List<ConsumerData> runConsumer() {
		return consumer.pollForData();
	}

}
