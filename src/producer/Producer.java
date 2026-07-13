package producer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Producer {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 9092);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true);

                Scanner scanner = new Scanner(System.in);
        ) {

            System.out.println(in.readLine());

            while (true) {

                System.out.print("Enter topic: ");
                String topic = scanner.nextLine();

                System.out.print("Enter message: ");
                String msg = scanner.nextLine();

                out.println(topic + ":" + msg);

                System.out.println(in.readLine());

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}