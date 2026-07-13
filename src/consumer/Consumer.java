package consumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Consumer {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 9092);

                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()
                                )
                        );

                PrintWriter out =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true
                        );

                Scanner scanner = new Scanner(System.in)
        ) {

            System.out.println(in.readLine());

            System.out.print("Enter topic: ");
            String topic = scanner.nextLine();

            System.out.print("Enter offset: ");
            String offset = scanner.nextLine();

            out.println("consume:" + topic + ":" + offset);

            String response;

            while ((response = in.readLine()) != null) {

                if ("END".equals(response)) {
                    break;
                }

                System.out.println(response);
            }

            System.out.println("Finished reading messages.");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}