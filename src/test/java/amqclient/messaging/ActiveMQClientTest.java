package amqclient.messaging;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ActiveMQClientTest {

    ActiveMQClient client;

    @Before
    public void setUp() {
        System.setProperty("AMQ_USERNAME", "user");
        System.setProperty("AMQ_PASSWORD", "-pas1word");
    }

    @After
    public void tearDown() {
        System.clearProperty("AMQ_USERNAME");
        System.clearProperty("AMQ_PASSWORD");
    }

    @Ignore
    @Test
    public void init() {
        client = new ActiveMQClient();
        client.init();
        client.sendMessage("Huuhaa");
    }
}