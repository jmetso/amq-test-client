package ha.backend.bluetooth.messaging;

import ha.backend.bluetooth.AMQClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
public class MessagingClientImpl implements MessagingClient {

    private static final Logger logger = LoggerFactory.getLogger(MessagingClientImpl.class);

    private QueueClient queueClient;

    public MessagingClientImpl() {
        if(!"true".equalsIgnoreCase(System.getProperty(AMQClient.TEST_MODE, "false"))) {
            this.queueClient = new ActiveMQClient();
            this.queueClient.setMessagingClient(this);
            this.queueClient.init();
        }
    }

    @Override
    public void sendToServer(String message) {
        logger.debug("Message: "+message);
        this.queueClient.sendMessage(message);
    }

    @Override
    public void receiveMessage(String payload) {
        logger.warn("Receiving messages not implemented!");
    }

    void setQueueClient(QueueClient queueClient) {
        this.queueClient = queueClient;
    }

}
