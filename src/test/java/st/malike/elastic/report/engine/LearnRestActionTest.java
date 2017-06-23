package st.malike.elastic.report.engine;

import org.elasticsearch.client.Response;
import org.junit.Test;

import static org.elasticsearch.test.rest.ESRestTestCase.entityAsMap;
import static org.hamcrest.Matchers.hasEntry;

/**
 * malike_st.
 */
public class LearnRestActionTest extends AbstractITCase {


    @Test
    public void testGenerateReport() throws Exception {
        Response response = client.performRequest("GET", "/_learn");
        assertThat(entityAsMap(response), hasEntry("status", true));
    }

}