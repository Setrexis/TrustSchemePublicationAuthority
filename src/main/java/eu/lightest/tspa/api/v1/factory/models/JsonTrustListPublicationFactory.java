package eu.lightest.tspa.api.v1.factory.models;

import eu.lightest.tspa.interfaces.models.IJsonTrustListPublication;
import eu.lightest.tspa.api.v1.models.json.JsonTrustListPublication;

public class JsonTrustListPublicationFactory {
    public static IJsonTrustListPublication newJsonTrustListPublication() {
        return new JsonTrustListPublication();
    }
}
