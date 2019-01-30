package ha.backend.bluetooth.database.bean;

/**
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
public class BluetoothDevice {

    private String mac;
    private int localID;
    private String name;

    public BluetoothDevice() {

    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getLocalID() {
        return localID;
    }

    public void setLocalID(int localID) {
        this.localID = localID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
