package ua.od.vassio.protect.report.receipt.ui.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 21:27
 */
public class Config {
    private static final String CURRENT_FILE_NAME = ".config";
    private static Properties properties;
    private static File configFile;

    static {
        properties = new Properties();
        File currentDir = null;
        try {
            currentDir = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            configFile = new File(currentDir + CURRENT_FILE_NAME);
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
        } catch (Exception e) {
            throw new RuntimeException("" + currentDir, e);
        }
    }


    public static void save(String parameterName, Object value) {
        properties.put(parameterName, value);
        try {
            properties.store(new FileOutputStream(configFile), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String load(String parameterName) {
        try {
            properties.load(new FileInputStream(configFile));
            return (String) properties.get(parameterName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String load(String parameterName, String defaultValue) {
        String value = load(parameterName);
        return value == null ? defaultValue : value;
    }
}
