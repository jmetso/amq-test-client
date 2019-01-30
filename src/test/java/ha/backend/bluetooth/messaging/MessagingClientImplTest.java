package ha.backend.bluetooth.messaging;

import ha.backend.bluetooth.AMQClient;
import ha.backend.bluetooth.database.DatabaseManager;
import ha.backend.bluetooth.database.bean.BluetoothDevice;
import ha.backend.bluetooth.utils.ConfigUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
public class MessagingClientImplTest {

    private MessagingClientImpl messagingClient;
    private QueueClientMock queueClientMock;

    @Before
    public void setUp() {
        System.setProperty(AMQClient.TEST_MODE, "true");
        System.setProperty(ConfigUtils.CONFIG_FILE_PROPERTY, "src/test/resource/config.txt");
        ConfigUtils.init();
        this.queueClientMock = new QueueClientMock();

        this.messagingClient = new MessagingClientImpl();
        this.messagingClient.setQueueClient(this.queueClientMock);
    }

    @After
    public void tearDown() {
        this.messagingClient = null;
        this.queueClientMock = null;

        System.clearProperty(AMQClient.TEST_MODE);
        System.clearProperty(ConfigUtils.CONFIG_FILE_PROPERTY);
    }

    /*
    RuuviMeasurement{time=null, name=null, mac=F773E2A225D1, dataFormat=3, temperature=27.62, humidity=32.5, pressure=100956.0, accelerationX=0.032, accelerationY=0.021, accelerationZ=1.013, batteryVoltage=3.133, txPower=null, movementCounter=null, measurementSequenceNumber=null, tagID=null, rssi=-87, accelerationTotal=1.0137228418063786, accelerationAngleFromX=88.19105429778547, accelerationAngleFromY=88.81299168304713, accelerationAngleFromZ=2.1638415461390688, absoluteHumidity=8.660608794635051, dewPoint=9.688781587548714, equilibriumVaporPressure=3697.9455102709157, airDensity=1.1649145266596532}
     */
    @Test
    public void send() {
    }


    private class QueueClientMock implements QueueClient {

        LinkedList<String> messages = new LinkedList<>();

        @Override
        public void init() {

        }

        @Override
        public void sendMessage(String payload) {
            messages.add(payload);
        }

        @Override
        public void setMessagingClient(MessagingClientImpl messagingClient) {

        }

        @Override
        public void sendMessage(String queue, String payload) {

        }
    }

    private class DatabaseManagerMock implements DatabaseManager {
        @Override
        public BluetoothDevice getDevice(String mac) {
            if("F773E2A225D1".equalsIgnoreCase(mac)) {
                BluetoothDevice bd = new BluetoothDevice();
                bd.setLocalID(1);
                bd.setMac("F773E2A225D1");
                return bd;
            }
            return null;
        }
    }
}