package eu.lightest.tspa.interfaces.models;

import eu.lightest.tspa.api.v1.models.json.JsonDaneCertificate;

import java.util.List;

public interface IJsonTrustListPublication {

    void setUrl(String url);
    String getUrl();

    void setCertificate(JsonDaneCertificate cert);
    int getCertificateCount();
    IJsonDaneCertificate getCertificate(int n);
    List<JsonDaneCertificate> getCertificates();

    boolean verify();
}
