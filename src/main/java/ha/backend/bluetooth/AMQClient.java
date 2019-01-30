package ha.backend.bluetooth;

import ha.backend.bluetooth.database.DatabaseManager;
import ha.backend.bluetooth.database.DatabaseManagerImpl;
import ha.backend.bluetooth.messaging.*;
import ha.backend.bluetooth.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bluetooth backend main class
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
@SpringBootApplication
public class AMQClient {

    public static final String TEST_MODE = "testMode";

    private static final Logger logger = LoggerFactory.getLogger(AMQClient.class);

    public static void main(String[] args) {
        SpringApplication.run(AMQClient.class, args);
    }

    public AMQClient() {
        //ConfigUtils.init();
    }

    /*
    private void run() {
        DatabaseManager databaseManager = new DatabaseManagerImpl();

        MessagingClient messagingClient = new MessagingClientImpl();

        logger.info("Running AMQClient");
        try {
            while (true) {
                Thread.sleep(60000);
            }
        } catch(InterruptedException e) {
            logger.error("Unable to sleep!", e);
        }
    }*/

}
