package client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFile {

    private transient String file;

    public PropertyFile(String file) {
        this.file = file;
    }

    /**
     * Loads the properties of a file.
     * @return properties of the file.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public Properties getProperties() {
        try {
            InputStream inputStream;
            Properties properties = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            inputStream = classLoader.getResourceAsStream(file);

            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();
            } else {
                return null;
            }
            return properties;
        } catch (IOException ex) {
            return null;
        }
    }
}
