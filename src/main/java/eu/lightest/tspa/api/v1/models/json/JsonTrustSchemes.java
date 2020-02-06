package eu.lightest.tspa.api.v1.models.json;

import com.google.gson.annotations.SerializedName;
import eu.lightest.tspa.interfaces.models.IJsonTrustSchemes;

import java.util.List;

public class JsonTrustSchemes implements IJsonTrustSchemes {

    @SerializedName("schemes")
    public List<String> mSchemes;

    @Override
    public void setTrustSchemes(List<String> schemes) {
        mSchemes = schemes;
    }

    @Override
    public List<String> getTrustSchemes() {
        return mSchemes;
    }
}
