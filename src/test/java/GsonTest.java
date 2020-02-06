import com.google.gson.Gson;
import eu.lightest.tspa.api.v1.models.json.JsonDaneCertificate;
import eu.lightest.tspa.api.v1.models.json.JsonTrustListPublication;
import org.junit.Assert;
import org.junit.Test;

public class GsonTest {

    private String mJsonTrustListRawData = "{\"url\":\"https://test.lightest-dev.iaik.tugraz.at\",\"certificate\":[{\"usage\":\"pkix-ta\",\"selector\":\"spki\",\"matching\":\"sha-256\",\"data\":\"ABCDEF\"}]}";
    private String mJsonDaneRawData = "{\"usage\":\"pkix-ta\",\"selector\":\"spki\",\"matching\":\"sha-256\",\"data\":\"ABCDEF\"}";


    @Test
    public void deserializeDaneWithEnums() {
        Gson gson = new Gson();
        JsonDaneCertificate dane = gson.fromJson(mJsonDaneRawData, JsonDaneCertificate.class);
        Assert.assertNotNull(dane);
    }


    @Test
    public void deserializeTrustListWithEnums() {
        Gson gson = new Gson();
        JsonTrustListPublication pub = gson.fromJson(mJsonTrustListRawData, JsonTrustListPublication.class);
        Assert.assertNotNull(pub);
    }
}
