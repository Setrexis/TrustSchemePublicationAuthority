package eu.lightest.tspa.api.v1.factory.models;

import eu.lightest.tspa.interfaces.models.IJsonDaneCertificate;
import eu.lightest.tspa.api.v1.models.json.JsonDaneCertificate;

public class JsonDaneCertificateFactory {
    public static IJsonDaneCertificate newJsonDaneCertificate(){
        return new JsonDaneCertificate();
    }
}
