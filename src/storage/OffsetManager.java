package storage;

import java.util.HashMap;
import java.util.Map;

public class OffsetManager {

    private final Map<String, Long> offsets = new HashMap<>();


    public synchronized long nextOffset(String topic) {

        long currentOffset = offsets.getOrDefault(topic, 0L);

        offsets.put(
                topic,
                currentOffset + 1
        );

        return currentOffset;
    }
}