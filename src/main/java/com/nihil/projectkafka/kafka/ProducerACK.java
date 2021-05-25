package com.nihil.projectkafka.kafka;

public class ProducerACK {

	private String topic;
	private int partition;
	private long offset;
	private long timestamp;
	private String key;

	public ProducerACK() {

	}

	public ProducerACK(String topic, int partition, long offset, long timestamp,
			String key) {
		super();
		this.topic = topic;
		this.partition = partition;
		this.offset = offset;
		this.timestamp = timestamp;
		this.key = key;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getPartition() {
		return partition;
	}
	public void setPartition(int partition) {
		this.partition = partition;
	}
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "ProducerACK [topic=" + topic + ", partition=" + partition
				+ ", offset=" + offset + ", timestamp=" + timestamp + ", key="
				+ key + "]";
	}

}
