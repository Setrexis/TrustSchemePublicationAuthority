package eu.lightest.tspa.api.v1.factory.models;

import eu.lightest.tspa.interfaces.models.IJsonSchemes;
import eu.lightest.tspa.api.v1.models.json.JsonSchemes;

public class JsonSchemesFactory {
    public static IJsonSchemes newJsonSchemes() {
        return new JsonSchemes();
    }
}
