import broker.Broker;

public class Main {

    public static void main(String[] args) {

        System.out.println("Mini Kafka Starting...");

        Broker broker = new Broker();
        broker.start();

    }
}