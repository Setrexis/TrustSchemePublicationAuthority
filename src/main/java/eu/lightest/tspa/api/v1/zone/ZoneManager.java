package eu.lightest.tspa.api.v1.zone;

import com.google.gson.Gson;
import eu.lightest.tspa.api.exceptions.InvalidStatusCodeException;
import eu.lightest.tspa.api.v1.models.json.JsonTrustListPublication;
import eu.lightest.tspa.interfaces.zone.IZoneManager;
import okhttp3.*;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class ZoneManager implements IZoneManager {

    private static final String NAMES = "names";
    private static final String TRUST_LIST = "trust-list";
    private static final String SCHEMES = "schemes";
    private static final String SEP = "/";
    private static final String HTTPS = "http://";

    private static final String CONFIG_PROPERTY_NAMESERVER = "nameserver.address";
    private static final String CONFIG_PROPERTY_BEARER_TOKEN = "nameserver.token";
    private PropertiesConfiguration mConfig;

    private OkHttpClient mClient = new OkHttpClient();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("Application/Json");

    private boolean mNsQueryStatus = true;

    private Log mLog = LogFactory.getLog(ZoneManager.class);

    public void setConfiguration(PropertiesConfiguration config) {
        mConfig = config;
        mNsQueryStatus = mConfig.getBoolean("secret.ns.query.status");
    }

    public int publishTrustList(String schemeName, String data) throws IOException, InvalidStatusCodeException {
        String endpoint = buildFullPath(buildTrustListEndpointBySchemeName(schemeName));

        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, data);

        mLog.debug(data);

        Request request = buildRestPutRequest(endpoint, body);

        int statusCode = sendRequest(request);

        mLog.debug("PUT " + endpoint);

        return statusCode;
    }

    public int deleteTrustList(String schemeName) throws IOException, InvalidStatusCodeException {
        String endpoint = buildFullPath(buildTrustListEndpointBySchemeName(schemeName));

        Request request = buildRestDeleteRequest(endpoint);

        int statusCode = sendRequest(request);

        mLog.debug("DELETE " + endpoint);

        return statusCode;
    }

    public int publishTrustSchemes(String schemeName, String service) throws IOException, InvalidStatusCodeException {
        String endpoint = buildFullPath(buildSchemeEndpointByServiceName(schemeName));

        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, service);

        Request request = buildRestPutRequest(endpoint, body);

        int statusCode = sendRequest(request);

        mLog.debug("PUT " + endpoint);

        return statusCode;
    }

    public int deleteTrustSchemes(String service) throws IOException, InvalidStatusCodeException {
        String endpoint = buildFullPath(buildSchemeEndpointByServiceName(service));

        Request request = buildRestDeleteRequest(endpoint);

        int statusCode = sendRequest(request);

        mLog.debug("DELETE " + endpoint);

        return statusCode;
    }

    @Override
    public int publishTrustListFromUrl(String schemeName, String url) throws IOException, InvalidStatusCodeException {
        JsonTrustListPublication json = new JsonTrustListPublication();
        json.setUrl(url);

        Gson gson = new Gson();
        String data = gson.toJson(json);
        return publishTrustList(schemeName, data);
    }

    private int sendRequest(Request request) throws InvalidStatusCodeException, IOException {
        int statusCode = 0;

        try {
            if ( mNsQueryStatus ) {
                mLog.debug("Request: " + request);

                Response response = mClient.newCall(request).execute();
                statusCode = response.code();

                mLog.info("Response: " + statusCode);
                mLog.debug(response);

                if ( statusCode < 200 || statusCode >= 400 ) {
                    mLog.debug("Error during the publication! Wrong status code received!");
                    throw new InvalidStatusCodeException(response.body().string(), statusCode);
                }
            }


        } catch (IOException e) {
            mLog.debug(e.getMessage(), e);
            mLog.debug(e);
            throw e;
        }

        return statusCode;
    }

    @Override
    public String toString() {
        return "This is Zone Manager for API v1";
    }

    private String buildTrustListEndpointBySchemeName(String schemeName) {
        return NAMES + SEP + schemeName + SEP + TRUST_LIST;
    }

    private String buildSchemeEndpointByServiceName(String serviceName) {
        return NAMES + SEP + serviceName + SEP + SCHEMES;
    }

    private String buildFullPath(String endpoint) {
        return HTTPS + getNameServer() + SEP + endpoint;
    }

    private String getNameServer() {
        if (mConfig == null) {
            mLog.info("How did this happen?!");
            return null;
        }
        return mConfig.getString(CONFIG_PROPERTY_NAMESERVER);
    }

    private String getBearerToken() {
        return mConfig.getString(CONFIG_PROPERTY_BEARER_TOKEN);
    }

    private Request buildRestPutRequest(String endpoint, RequestBody body) {
        return new Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", "Bearer " + getBearerToken())
                .put(body)
                .build();
    }

    private Request buildRestDeleteRequest(String endpoint) {
        return new Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", "Bearer " + getBearerToken())
                .delete()
                .build();
    }


}
