package amqclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AMQClient main class
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
    }

}
