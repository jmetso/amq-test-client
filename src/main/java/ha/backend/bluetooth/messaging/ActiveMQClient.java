package ha.backend.bluetooth.messaging;

import ha.backend.bluetooth.AMQClient;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
@Component
public class ActiveMQClient implements QueueClient, MessageListener {

    private Logger logger = LoggerFactory.getLogger(ActiveMQClient.class);

    private int timeToLive = 100;
    private boolean disableReads = true;
    public String username = "username";
    public String password = "password";
    public String brokerURL = "tcp://localhost:61616";
    public String myQueueName = "test";
    public String readQueue = "read";

    private MessagingClientImpl messagingClient;

    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private MessageProducer producer;

    public ActiveMQClient() {
        this.connectToBroker();
    }

    private void connectToBroker() {
        this.username = System.getenv("AMQ_USERNAME");
        this.readQueue = System.getenv("READ_QUEUE");
        this.myQueueName = System.getenv("DEFAULT_WRITE_QUEUE");
        this.password = System.getenv("AMQ_PASSWORD");
        this.brokerURL = System.getenv("AMQ_URL");
        this.disableReads = Boolean.parseBoolean(System.getenv("DISABLE_READS"));
        this.timeToLive = Integer.parseInt(System.getenv("TIME_TO_LIVE"));
        this.logger.info("Default queue is "+this.myQueueName);
        this.logger.info("Read queue is "+this.readQueue);
        this.logger.info("Username is "+this.username);
        this.logger.debug("Password is "+this.password);
        this.logger.info("Broker URL is "+this.brokerURL);
        this.logger.info("Time to live "+this.timeToLive);
        this.logger.info("Disable reads "+this.disableReads);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.username, this.password, this.brokerURL);
        try {
            this.connection = connectionFactory.createConnection();
            this.connection.start();
            this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination readQueue = this.session.createQueue(this.readQueue);
            Destination writeQueue = this.session.createQueue(this.myQueueName);
            this.consumer = this.session.createConsumer(readQueue);
            this.producer = this.session.createProducer(writeQueue);
            this.producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            this.producer.setTimeToLive(this.timeToLive*1000);
            this.logger.info("Connected to broker");
        } catch(javax.jms.JMSException e) {
            this.logger.error("Unable to connect to ActiveMQ!", e);
        }
    }

    @Override
    public void init() {
        try {
            this.consumer.setMessageListener(this);
        } catch(javax.jms.JMSException e) {
            this.logger.error("Unable to start connection to ActiveMQ!", e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        this.connection.close();
        super.finalize();
    }

    @Override
    public void sendMessage(String payload) {
        try {
            this.logger.debug("Sending message: "+payload);
            TextMessage jmsMessage = this.session.createTextMessage(payload);
            DateTimeFormatter f = DateTimeFormatter.ofPattern("y-MM-dd H:mm:ss");
            this.logger.info("JMSExpiration: "+ LocalDateTime.ofEpochSecond((int)(System.currentTimeMillis()/1000+this.timeToLive), 0, ZoneOffset.ofHours(2)).format(f));
            this.producer.send(jmsMessage);
        } catch (javax.jms.JMSException e) {
            this.logger.warn("Connection to broker lost! Trying to reconnect!", e);
            this.connectToBroker();
        }
    }

    @Override
    public void sendMessage(String queue, String payload) {
        try {
            this.logger.debug("Sending message: "+payload);
            MessageProducer producer = this.session.createProducer(this.session.createQueue(queue));
            producer.setTimeToLive(this.timeToLive*1000);
            TextMessage jmsMessage = this.session.createTextMessage(payload);
            DateTimeFormatter f = DateTimeFormatter.ofPattern("y-MM-dd H:mm:ss");
            this.logger.info("JMSExpiration: "+ LocalDateTime.ofEpochSecond((int)(System.currentTimeMillis()/1000+this.timeToLive), 0, ZoneOffset.ofHours(2)).format(f));
            producer.send(jmsMessage);
        } catch (javax.jms.JMSException e) {
            this.logger.warn("Connection to broker lost! Trying to reconnect!", e);
            this.connectToBroker();
        }
    }

    @Override
    public void setMessagingClient(MessagingClientImpl messagingClient) {
        this.messagingClient = messagingClient;
    }

    @Override
    public void onMessage(Message message) {
        if(!this.disableReads) {
            if (message instanceof TextMessage) {
                this.logger.debug("Processing text message.");
                try {
                    this.logger.info("Message: "+((TextMessage) message).getText());
                } catch (javax.jms.JMSException e) {
                    this.logger.error("Unable get content from message. Ignoring.", e);
                }
            }
        }
    }

}
