package amqclient.messaging;

/**
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
public interface QueueClient {

    void init();
    void sendMessage(String payload);
    void sendMessage(String queue, String payload);
    void setMessagingClient(MessagingClientImpl messagingClient);
    void reconnect();
    boolean disconnect();

}
