package ha.backend.bluetooth.database;

import ha.backend.bluetooth.database.bean.BluetoothDevice;
import ha.backend.bluetooth.utils.ConfigUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-08-07
 */
public class DatabaseManagerImplTest {

    private DatabaseManagerImpl databaseManager;

    @Before
    public void setUp() {
        System.setProperty("configFile", "src/test/resources/config.txt");
        ConfigUtils.init();
        this.databaseManager = new DatabaseManagerImpl();
    }

    @After
    public void tearDown() {
        this.databaseManager = null;
        System.clearProperty("configFile");
    }

    @Test
    public void getDevice() {
        String mac = "F773E2A225D1";
        BluetoothDevice bd = this.databaseManager.getDevice(mac);
        assertNotNull("Device", bd);
        assertEquals("Mac", mac, bd.getMac());
        assertEquals("Name", "Test", bd.getName());
        assertEquals("LocalID", 1, bd.getLocalID());
    }

    @Test
    public void getDevice_Unknown() {
        String mac = "unknown";
        BluetoothDevice bd = this.databaseManager.getDevice(mac);
        assertNull(bd);
    }

}