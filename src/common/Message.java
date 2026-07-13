package common;

public class Message {

    private final long offset;
    private final String topic;
    private final int partition;
    private final String payload;

    public Message(long offset,
                   String topic,
                   int partition,
                   String payload) {

        this.offset = offset;
        this.topic = topic;
        this.partition = partition;
        this.payload = payload;
    }

    public long getOffset() {
        return offset;
    }

    public String getTopic() {
        return topic;
    }

    public int getPartition() {
        return partition;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {

        return offset
                + " | P"
                + partition
                + " | ["
                + topic
                + "] "
                + payload;
    }
}