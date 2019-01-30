package ha.backend.bluetooth.messaging;

import ha.backend.bluetooth.utils.ConfigUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActiveMQClientTest {

    ActiveMQClient client;

    @Before
    public void setUp() {
        System.setProperty(ConfigUtils.CONFIG_FILE_PROPERTY, "config.txt");
        ConfigUtils.init();
        System.setProperty("AMQ_USERNAME", "user");
        System.setProperty("AMQ_PASSWORD", "-pas1word");
    }

    @After
    public void tearDown() {
        System.clearProperty(ConfigUtils.CONFIG_FILE_PROPERTY);
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