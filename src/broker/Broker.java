package broker;

import network.ClientHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Broker {

    private static final int PORT = 9092;

    public void start() {

        try {

            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("==================================");
            System.out.println(" Mini Kafka Broker Started");
            System.out.println(" Listening on port " + PORT);
            System.out.println("==================================");

            while (true) {

                Socket client = serverSocket.accept();

                System.out.println("New Client Connected: " + client.getInetAddress());

                Thread thread = new Thread(new ClientHandler(client));
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}