package storage;

import common.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogStorage {

    private static final String DATA_FOLDER = "data";

    public LogStorage() {

        File folder = new File(DATA_FOLDER);

        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public synchronized void append(Message message) {

        // Create topic folder
        File topicFolder = new File(DATA_FOLDER, message.getTopic());

        if (!topicFolder.exists()) {
            topicFolder.mkdirs();
        }

        // Create partition log
        File file = new File(
                topicFolder,
                "partition-" + message.getPartition() + ".log"
        );

        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(
                    message.getOffset()
                    + "|"
                    + message.getPayload()
            );

            writer.write(System.lineSeparator());

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    public List<Message> read(String topic) {

        List<Message> messages = new ArrayList<>();

        File topicFolder = new File(DATA_FOLDER, topic);

        if (!topicFolder.exists()) {
            return messages;
        }

        File[] files = topicFolder.listFiles();

        if (files == null) {
            return messages;
        }

        for (File file : files) {

            String name = file.getName();

            if (!name.startsWith("partition-")) {
                continue;
            }

            int partition = Integer.parseInt(
                    name.replace("partition-", "")
                        .replace(".log", "")
            );

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split("\\|", 2);

                    long offset = Long.parseLong(parts[0]);

                    String payload = parts[1];

                    messages.add(
                            new Message(
                                    offset,
                                    topic,
                                    partition,
                                    payload
                            )
                    );
                }

            } catch (IOException e) {

                e.printStackTrace();

            }
        }

        return messages;
    }
}