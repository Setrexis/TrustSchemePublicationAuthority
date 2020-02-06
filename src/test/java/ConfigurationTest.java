import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationTest {

    @Test
    @Ignore
    public void readTestConfigurationFile() throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("config.properties");
        String query_status = config.getString("secret.ns.query.status");

        assertEquals("Query Status is set to the wrong value! Recieved " + query_status, query_status, "false");
    }
}
