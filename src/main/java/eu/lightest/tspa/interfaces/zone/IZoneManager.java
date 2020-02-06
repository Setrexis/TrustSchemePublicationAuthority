package eu.lightest.tspa.interfaces.zone;

import eu.lightest.tspa.api.exceptions.InvalidStatusCodeException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.IOException;

public interface IZoneManager {
    void setConfiguration(PropertiesConfiguration config);

    int publishTrustList(String schemeName, String data) throws IOException, InvalidStatusCodeException;

    int deleteTrustList(String schemeName) throws IOException, InvalidStatusCodeException;

    int publishTrustSchemes(String schemeName, String data) throws IOException, InvalidStatusCodeException;

    int deleteTrustSchemes(String schemeName) throws IOException, InvalidStatusCodeException;

    int publishTrustListFromUrl(String schemeName, String url) throws IOException, InvalidStatusCodeException;
}
