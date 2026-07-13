package storage;

import common.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicManager {

    private final Map<String, List<Message>> topics = new HashMap<>();

    private final LogStorage storage = new LogStorage();

    private final OffsetManager offsetManager = new OffsetManager();

    private final PartitionManager partitionManager = new PartitionManager();

    public synchronized void publish(String topic, String payload) {

        long offset = offsetManager.nextOffset(topic);

        int partition = partitionManager.getPartition(topic);

        Message message = new Message(
                offset,
                topic,
                partition,
                payload
        );

        topics.computeIfAbsent(topic, k -> new ArrayList<>());

        topics.get(topic).add(message);

        storage.append(message);

        System.out.println("Stored: " + message);
    }

    public List<Message> getMessages(String topic) {

        return storage.read(topic);
    }
}