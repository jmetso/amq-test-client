package ha.backend.bluetooth.database;

import ha.backend.bluetooth.database.bean.BluetoothDevice;

/**
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
public interface DatabaseManager {

    BluetoothDevice getDevice(String mac);

}
