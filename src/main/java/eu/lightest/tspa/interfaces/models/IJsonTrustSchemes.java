package eu.lightest.tspa.interfaces.models;

import java.util.List;

public interface IJsonTrustSchemes {
    void setTrustSchemes(List<String> schemes);
    List<String> getTrustSchemes();
}
