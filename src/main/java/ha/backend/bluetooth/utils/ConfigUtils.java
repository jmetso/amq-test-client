package ha.backend.bluetooth.utils;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Janne Metso &copy; 2018
 * @since 2018-07-15
 */
public class ConfigUtils {

    public static final String CONFIG_FILE_PROPERTY = "configFile";
    private static Logger logger = LoggerFactory.getLogger("ConfigUtils.class");
    private int configUtilID = 1;
    private static PropertiesConfiguration config;

    private static long measurementUpdateLimit = 9900;
    private static Predicate<String> filterMode = (s) -> true;
    private static final Set<String> FILTER_MACS = new HashSet<>();
    private static final Map<String, String> TAG_NAMES = new HashMap<>();
    private static String[] scanCommand = {"hcitool", "lescan", "--duplicates", "--passive"};
    private static String[] dumpCommand = {"hcidump", "--raw"};

    public ConfigUtils() {
        logger.info("Initializing ConfigUtils id "+this.getNextID()+".");
        init();
    }

    private synchronized int getNextID() {
        return this.configUtilID++;
    }

    public static void init() {
        if(config == null) {
            String configFile = System.getProperty(CONFIG_FILE_PROPERTY);
            try {
                Configurations confs = new Configurations();
                FileBasedConfigurationBuilder<PropertiesConfiguration> builder = confs.propertiesBuilder(new File(configFile));
                config = builder.getConfiguration();
            } catch(org.apache.commons.configuration2.ex.ConfigurationException e) {
                Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
                logger.error("Unable to read configuration file "+configFile+".", e);
            }
        }
        measurementUpdateLimit = config.getLong("update.interval", 9900);
    }

    public static PropertiesConfiguration getConfiguration() {
        if(config == null) init();
        return config;
    }

    public static String getString(String key, String defaultValue) {
        return config.getString(key, defaultValue);
    }

    public static String[] getScanCommand() {
        return scanCommand;
    }

    public static String[] getDumpCommand() {
        return dumpCommand;
    }

    public static boolean isAllowedMAC(String mac) {
        return filterMode.test(mac);
    }

    public static long getMeasurementUpdateLimit() {
        return measurementUpdateLimit;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return config.getBoolean(key, defaultValue);
    }

}