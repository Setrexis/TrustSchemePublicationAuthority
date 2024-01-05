import com.google.gson.Gson;
import eu.lightest.tspa.api.v1.models.json.JsonDaneCertificate;
import eu.lightest.tspa.api.v1.models.json.JsonSchemes;
import eu.lightest.tspa.api.v1.models.json.JsonTrustListPublication;
import eu.lightest.tspa.services.PublicationService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Ignore;
import org.junit.Test;
//import sun.misc.IOUtils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;
import java.util.logging.LogManager;

import static junit.framework.TestCase.assertEquals;

public class PublicationServiceTest extends JerseyTest {

    private Log mLog = LogFactory.getLog(PublicationServiceTest.class);

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(PublicationService.class);
    }

    @Test
    public void testEndpoint() {
        Response rsp = target("/v1/test").request().get();
        mLog.debug(rsp.getStatus());
        mLog.debug(rsp.readEntity(String.class));
    }

    @Test
    public void deleteNonExistentTrustScheme() {
        mLog.debug("Delete non existing SCHEME");
        Response rsp = target("/v1/does_not_exist.lightest.nlnetlabs.nl/schemes").request().delete();

        assertEquals(404, rsp.getStatus());
        mLog.debug(rsp.readEntity(String.class));
    }

    @Test
    public void deleteNonExistentTrustSchemeWithInvalidSchemeName() {
        mLog.debug("Delete non existing SCHEME");
        Response rsp = target("/v1/does_not_exist/schemes").request().delete();

        assertEquals(404, rsp.getStatus());
        mLog.debug(rsp.readEntity(String.class));
    }



    @Test
    public void deleteNonExistentTrustList() {
        mLog.debug("Delete non existing LIST");
        Response rsp = target("/v1/does_not_exist.lightest.nlnetlabs.nl/trust-list").request().delete();

        assertEquals(404, rsp.getStatus());
        mLog.debug(rsp.readEntity(String.class));
    }

    @Test
    public void deleteNonExistentTrustListWithInvalidSchemeName() {
        mLog.debug("Delete non existing LIST");
        Response rsp = target("/v1/does_not_exist/trust-list").request().delete();

        assertEquals(404, rsp.getStatus());
        mLog.debug(rsp.readEntity(String.class));
    }


    @Test
    public void publishSimpleTrustScheme() {
        mLog.debug("Publish Trust Scheme");
        List<String> test_schemes = new Vector<>();
        test_schemes.add("junit-test-eidas.lightest-dev.iaik.tugraz.at");

        JsonSchemes schemes =  new JsonSchemes();
        schemes.setSchemes(test_schemes);

        Gson gson = new Gson();
        String request = gson.toJson(schemes);

        Entity request_entity = Entity.entity(request, MediaType.APPLICATION_JSON);
        Response rsp = target("/v1/junit-test-scheme.lightest.nlnetlabs.nl/schemes").request().put(request_entity);

        assertEquals(201, rsp.getStatus());
    }

    @Test
    public void deleteSimpleTrustScheme() {
        mLog.debug("Delete Trust Scheme");
        Response rsp = target("/v1/junit-test-scheme.lightest.nlnetlabs.nl/schemes").request().delete();

        assertEquals(200, rsp.getStatus());
    }

    @Test
    public void publishSimpleTrustList() {
        mLog.debug("Publish simple Trust List");


        JsonTrustListPublication tl = new JsonTrustListPublication();
        tl.setUrl("https://test.lightest-dev.iaik.tugraz.at");
        String request = new Gson().toJson(tl);
        Entity request_entity = Entity.entity(request, MediaType.APPLICATION_JSON);
        Response rsp = target("/v1/junit-test-list.lightest.nlnetlabs.nl/trust-list").request().put(request_entity);

        assertEquals(200, rsp.getStatus());
    }

    @Test
    @Ignore
    public void publishTrustListDaneDefault() {
        mLog.debug("Publish simple Trust List");


        JsonTrustListPublication tl = new JsonTrustListPublication();
        tl.setUrl("https://test.lightest-dev.iaik.tugraz.at");

        JsonDaneCertificate cert = new JsonDaneCertificate();
        //cert.setData(Base64.encodeBase64String("Test Data".getBytes()));

        cert.setData("ABCDEF");

        tl.setCertificate(cert);

        String request = new Gson().toJson(tl);
        Entity request_entity = Entity.entity(request, MediaType.APPLICATION_JSON);
        Response rsp = target("/v1/junit-test-list_dane.lightest.nlnetlabs.nl/trust-list").request().put(request_entity);

        assertEquals(200, rsp.getStatus());
    }


    @Test
    public void deleteUndeletableTrustList() {
       mLog.debug("Deleting peppol_eprocurement_old_scheme");

        Response rsp = target("/v1/peppol_eprocurement_old_scheme.lightest.nlnetlabs.nl/trust-list").request().delete();

        assertEquals(404, rsp.getStatus());
        mLog.debug(rsp.readEntity(String.class));
    }


    @Test
    @Ignore
    public void uploadAndStoreXmlTrustList() throws IOException {
        String xmlFileName = "src/main/resources/Pumpkin_Demo_TS_v0.1-signed-xades-baseline-b.xml";

        String xml = new String(Files.readAllBytes(Paths.get(xmlFileName)));

        Entity request_entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Response rsp = target("/v1/junit-write_dane.lightest.nlnetlabs.nl/trust-list").request().put(request_entity);
        assertEquals(200, rsp.getStatus());
    }

}
