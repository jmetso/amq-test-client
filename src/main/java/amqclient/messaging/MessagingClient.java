package amqclient.messaging;

/**
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
public interface MessagingClient {

    void sendToServer(String message);
    void receiveMessage(String payload);

}
