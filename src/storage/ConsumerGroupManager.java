package storage;

import java.util.*;

public class ConsumerGroupManager {

    private final Map<String, List<String>> groups = new HashMap<>();

    public synchronized void joinGroup(String groupId, String consumerId) {

        groups.computeIfAbsent(groupId, k -> new ArrayList<>());

        if (!groups.get(groupId).contains(consumerId)) {
            groups.get(groupId).add(consumerId);
        }

        System.out.println(
                consumerId + " joined group " + groupId
        );
    }

    public List<String> getConsumers(String groupId) {

        return groups.getOrDefault(
                groupId,
                new ArrayList<>()
        );
    }
}