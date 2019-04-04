package amqclient.messaging;

/**
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
public interface QueueClient {

    void init();
    boolean sendMessage(String payload);
    boolean sendMessage(String queue, String payload);
    void setMessagingClient(MessagingClientImpl messagingClient);
    boolean reconnect();
    boolean disconnect();

}
