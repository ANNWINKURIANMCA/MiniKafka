package storage;

import java.util.HashMap;
import java.util.Map;

public class PartitionManager {

    private static final int PARTITION_COUNT = 3;

    private final Map<String, Integer> nextPartition = new HashMap<>();

    public synchronized int getPartition(String topic) {

        int partition = nextPartition.getOrDefault(topic, 0);

        nextPartition.put(
                topic,
                (partition + 1) % PARTITION_COUNT
        );

        return partition;
    }
}