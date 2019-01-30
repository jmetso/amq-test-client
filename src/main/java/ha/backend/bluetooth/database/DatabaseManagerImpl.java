package ha.backend.bluetooth.database;

import ha.backend.bluetooth.database.bean.BluetoothDevice;
import ha.backend.bluetooth.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sqlite.SQLiteDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
public class DatabaseManagerImpl implements DatabaseManager {

    private static Logger logger = LoggerFactory.getLogger(DatabaseManagerImpl.class);
    private JdbcTemplate jdbcTemplate;
    private Map<String, BluetoothDevice> deviceCache = new HashMap<>();

    public DatabaseManagerImpl() {
        final String DB_URL = "jdbc:sqlite:db/bluetooth.db";
        final String DB_URL_KEY = "database.url";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        String dbUrl = ConfigUtils.getString(DB_URL_KEY, DB_URL);
        dataSource.setUrl(dbUrl);

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.createDatabaseTables();
    }

    @Override
    public BluetoothDevice getDevice(String mac) {
        if(this.deviceCache.containsKey(mac)) {
            return this.deviceCache.get(mac);
        } else {
            final String select = "SELECT * FROM Devices WHERE Mac=?";
            try {
                BluetoothDevice bd = this.jdbcTemplate.queryForObject(select, this::mapBluetoothDevice, mac);
                this.deviceCache.put(mac, bd);
                return bd;
            } catch (org.springframework.dao.EmptyResultDataAccessException e) {
                logger.debug("Unable to get bluetooth device with mac " + mac + ".");
            }
            return null;
        }
    }

    private BluetoothDevice mapBluetoothDevice(ResultSet resultSet, int i) throws SQLException {
        logger.debug("i: "+i);
        BluetoothDevice bd = new BluetoothDevice();
        bd.setMac(resultSet.getString("Mac"));
        bd.setLocalID(resultSet.getInt("LocalID"));
        bd.setName(resultSet.getString("Name"));
        return bd;
    }

    private void createDatabaseTables() {
        final String createBluetoothDevices = "CREATE TABLE IF NOT EXISTS Devices (Mac TEXT PRIMARY_KEY, LocalID INTEGER, Name TEXT)";
        logger.debug("Creating database tables ...");
        this.jdbcTemplate.execute(createBluetoothDevices);
        logger.debug("Creating database tables done");
    }

}
