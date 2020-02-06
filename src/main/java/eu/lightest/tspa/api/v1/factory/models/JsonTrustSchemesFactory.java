package eu.lightest.tspa.api.v1.factory.models;

import eu.lightest.tspa.interfaces.models.IJsonTrustSchemes;
import eu.lightest.tspa.api.v1.models.json.JsonTrustSchemes;

public class JsonTrustSchemesFactory {
    public static IJsonTrustSchemes newJsonTrustSchemes() {
        return new JsonTrustSchemes();
    }
}
