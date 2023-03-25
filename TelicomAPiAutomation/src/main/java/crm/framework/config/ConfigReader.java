package crm.framework.config;

import crm.framework.exception.ConfigFileException;
import crm.framework.exception.TestSuitException;
import crm.framework.util.Default;
import crm.framework.util.Timeout;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConfigReader {

    private static Logger logger = Logger.getLogger(ConfigReader.class);
    private static Properties configFile;
    private String path;
    private int varDepth;
    private static final Pattern FIND_VARS_PATTERN = Pattern.compile("\\%([a-zA-Z0-9_]+)\\%");

    ConfigReader() {
        this.path = Default.CONFIG_PATH.getValue();
        this.varDepth = Default.CONFIG_VAR_DEPTH;
    }

    public ConfigReader(String path) {
        this.path = Default.CONFIG_PATH.getValue();
        this.varDepth = Default.CONFIG_VAR_DEPTH;
        this.path = path;
    }

    public Properties readConfig() {
        InputStream istream = this.getClass().getResourceAsStream(this.path);
        if (null == istream) {
            try {
                istream = new FileInputStream(this.path);
            } catch (FileNotFoundException var4) {
                logger.error("FileNotFoundException", var4);
                throw new TestSuitException(this.path, var4);
            }
        }

        configFile = new Properties(this.getDefaultProperties());

        try {
            configFile.load((InputStream)istream);
            ((InputStream)istream).close();
        } catch (Exception var3) {
            logger.error("Error", var3);
            throw new ConfigFileException(this.path);
        }

        this.resolveCommandLineArgs();
        this.resolveVariables();
        return configFile;
    }

    private Properties getDefaultProperties() {
        Properties def = new Properties();
        def.setProperty(ConfigKeys.KEY_DRIVER_URL.getKey(), String.valueOf(Default.DRIVER_URL.getValue()));
        def.setProperty(ConfigKeys.KEY_WAIT_TIMEOUT.getKey(), String.valueOf(Timeout.WAIT_TIMEOUT.getValue()));
        def.setProperty(ConfigKeys.KEY_VERIFY_TIMEOUT.getKey(), String.valueOf(Timeout.VERIFY_TIMEOUT.getValue()));
        def.setProperty(ConfigKeys.KEY_VERIFY_INTERVAL.getKey(), String.valueOf(Timeout.VERIFY_INTERVAL.getValue()));
        def.setProperty(ConfigKeys.KEY_ELEMENT_TIMEOUT.getKey(), String.valueOf(Timeout.ELEMENT_TIMEOUT.getValue()));
        def.setProperty(ConfigKeys.KEY_ELEMENTS_TIMEOUT.getKey(), String.valueOf(Timeout.ELEMENTS_TIMEOUT.getValue()));
        def.setProperty(ConfigKeys.KEY_PAGELOAD_TIMEOUT.getKey(), String.valueOf(Timeout.PAGELOAD_TIMEOUT.getValue()));
        return def;
    }

    private void resolveCommandLineArgs() {
        Iterator it = configFile.keySet().iterator();

        while(it.hasNext()) {
            String key = it.next().toString();
            String value = "";

            try {
                value = System.getProperty(key);
                value = value != null ? value : "";
            } catch (Exception var5) {
                value = "";
            }

            if (!value.equals("")) {
                configFile.setProperty(key, value);
            }
        }

    }

    private void resolveVariables() {
        boolean hasVariables = false;
        int var2 = 0;

        do {
            hasVariables = false;
            Iterator var3 = configFile.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry)var3.next();
                String value = entry.getValue().toString();
                if (value.contains("%")) {
                    hasVariables = true;
                    String newValue = this.resolve(FIND_VARS_PATTERN, value);
                    configFile.put(entry.getKey(), newValue);
                }
            }
        } while(hasVariables && var2++ < this.varDepth);

    }

    private String resolve(Pattern pattern, String text) {
        Matcher m = pattern.matcher(text);
        HashMap replacements = new HashMap();

        String k;
        String replacement;
        while(m.find()) {
            int gc = m.groupCount();
            if (gc == 1) {
                String g0 = m.group(0);
                String g1 = m.group(1);
                k = null;
                replacement = configFile.getProperty(g1);
                if (null != replacement) {
                    k = replacement.toString();
                }

                replacements.put(g0, k);
            }
        }

        String newText = text;
        Iterator var11 = replacements.entrySet().iterator();

        while(var11.hasNext()) {
            Map.Entry<String, String> en = (Map.Entry)var11.next();
            k = (String)en.getKey();
            replacement = (String)replacements.get(k);
            if (replacement != null) {
                newText = newText.replace(k, replacement);
            }
        }

        return newText;
    }
}
