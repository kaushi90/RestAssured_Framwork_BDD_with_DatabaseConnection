package crm.framework.config;

import crm.framework.exception.ConfigFileException;
import crm.framework.exception.TestSuitException;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;



public class DefaultConfig implements ImplementConfig {

    private static Logger logger = Logger.getLogger(DefaultConfig.class);
    private Properties configFile;
    private static ImplementConfig config = null;

    public DefaultConfig() {
        ConfigReader reader = new ConfigReader();

        try {
            this.configFile = reader.readConfig();
        } catch (ConfigFileException var3) {
            logger.error("ConfigFileException", var3);
            throw new TestSuitException("ConfigFileException ", var3);
        }
    }

    public DefaultConfig(String path) {
        ConfigReader reader = new ConfigReader(path);

        try {
            this.configFile = reader.readConfig();
        } catch (ConfigFileException var4) {
            logger.error(var4);
            throw new TestSuitException("ConfigFileException ", var4);
        }
    }

    public DefaultConfig(Properties properties) {
        this.configFile = properties;
    }

    public static ImplementConfig getConfig() {
        if (config == null) {
            config = new DefaultConfig();
        }

        return config;
    }

    public static ImplementConfig getConfig(String path) {
        if (config == null) {
            config = new DefaultConfig(path);
        }

        return config;
    }

    public String getValue(String key) {
        String value = this.configFile.getProperty(key);
        return value == null ? "" : value;
    }

    public void setValue(String key, String value) {
        this.configFile.setProperty(key, value);
    }

    public Long getLongValue(String key, long defaultValue) {
        String longStr = this.getValue(key).trim();
        logger.trace("Getting config value for " + key);
        if (longStr != null && !"".equals(longStr)) {
            logger.trace(" config key " + key + " found");
            long foundValue = Long.parseLong(longStr);
            logger.trace("Config " + key + "=" + foundValue);
            return foundValue;
        } else {
            logger.trace(" config key " + key + " was not set. Returning default value=" + defaultValue);
            return defaultValue;
        }
    }

    public void printAll() {
        Iterator var1 = this.configFile.entrySet().iterator();

        while(var1.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry)var1.next();
            logger.debug(entry.getKey() + "=" + entry.getValue());
        }

    }

}
