package com.nihil.projectkafka.kafka;

public class ConsumerData {

	private String value;
	private ProducerACK meta;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public ProducerACK getMeta() {
		return meta;
	}
	public void setMeta(ProducerACK meta) {
		this.meta = meta;
	}

	@Override
	public String toString() {
		return "ConsumerData [value=" + value + ", meta=" + meta + "]";
	}

	public ConsumerData(String value, ProducerACK meta) {
		super();
		this.value = value;
		this.meta = meta;
	}

}
