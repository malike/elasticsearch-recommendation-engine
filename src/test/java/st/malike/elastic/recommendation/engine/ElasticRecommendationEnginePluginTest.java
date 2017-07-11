package st.malike.elastic.recommendation.engine;

import com.google.gson.Gson;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import st.malike.elastic.recommendation.engine.util.Enums;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

/**
 * @author malike_st
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ElasticRecommendationEnginePluginTest {

    private static Node node;
    private static ElasticsearchClusterRunner runner;
    private static final String CLUSTER_NAME = "RECOMMENDATION_CLUSTER";
    private static final String CLUSTER_HOST_ADDRESS = "localhost:9201-9210";
    private static final String INDEX = "recommendationindex";
    private static final int DOC_SIZE = 1000;
    private Map param;

    @BeforeClass
    public static void setUp() throws IOException {

        runner = new ElasticsearchClusterRunner();

        runner.onBuild(new ElasticsearchClusterRunner.Builder() {
            @Override
            public void build(final int number, final Settings.Builder settingsBuilder) {
                settingsBuilder.put("http.cors.allow-origin", "*");
                settingsBuilder.put("http.cors.enabled", true);
                settingsBuilder.putArray("discovery.zen.ping.unicast.hosts", CLUSTER_HOST_ADDRESS);
            }
        }).build(ElasticsearchClusterRunner.newConfigs().clusterName(CLUSTER_NAME).numOfNode(1)
                .pluginTypes("st.malike.elastic.recommendation.engine.ElasticRecommendationEnginePlugin"));

        runner.ensureYellow();

        //setupup dummy data
        final String type = "recommendationtype";

        // create an index
        runner.createIndex(INDEX, (Settings) null);

        // create documents
        for (int i = 1; i <= DOC_SIZE; i++) {
            runner.insert(INDEX, type, String.valueOf(i),
                    "{"
                    + "\"name\":\"Product " + i + "\","
                    + "\"id\":" + i
                    + "}");
        }
        runner.refresh();

        SearchResponse searchResponse = runner.search(INDEX, type, null, null, 0, 10);
        assertEquals(DOC_SIZE, searchResponse.getHits().getTotalHits());

        node = runner.node();
    }

    @AfterClass
    public static void tearDown() throws IOException {
        runner.close();
        runner.clean();
    }

    @Before
    public void setUpTest() {

        param = new HashMap();
    }

    @Test
    public void learnUsingWeka() {
        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_learnrecommendation")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true))
                .body("message", Matchers.is(Enums.JSONResponseMessage.SUCCESS.toString()));
    }

}
