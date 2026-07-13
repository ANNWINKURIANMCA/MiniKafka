package network;

import storage.TopicManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    private static final TopicManager topicManager = new TopicManager();


    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {

        try (
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream()
                                )
                        );

                PrintWriter out =
                        new PrintWriter(
                                clientSocket.getOutputStream(),
                                true
                        )
        ) {


            out.println("Welcome to Mini Kafka!");


            String message;


            while ((message = in.readLine()) != null) {


                // CONSUMER REQUEST
                if (message.startsWith("consume:")) {


                    String[] parts = message.split(":", 3);


                    if (parts.length != 3) {

                        out.println(
                                "Invalid consume format"
                        );

                        continue;
                    }


                    String topic = parts[1];

                    long offset = Long.parseLong(parts[2]);


                    var messages =
                            topicManager.getMessages(topic);


                    for (var msg : messages) {

                        if (msg.getOffset() >= offset) {

                            out.println(msg);

                        }
                    }


                    out.println("END");

                    continue;
                }



                // PRODUCER REQUEST
                String[] parts = message.split(":", 2);


                if (parts.length != 2) {

                    out.println(
                            "Invalid format. Use topic:message"
                    );

                    continue;
                }


                String topic = parts[0];

                String payload = parts[1];


                topicManager.publish(
                        topic,
                        payload
                );


                out.println(
                        "Stored Successfully"
                );
            }


        } catch (IOException e) {

            System.out.println(
                    "Client disconnected."
            );
        }
    }
}