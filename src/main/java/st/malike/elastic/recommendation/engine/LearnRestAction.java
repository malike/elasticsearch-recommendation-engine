package st.malike.elastic.recommendation.engine;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;

import java.io.IOException;

import static org.elasticsearch.rest.RestRequest.Method.GET;

/**
 * malike_st.
 */
public class LearnRestAction extends BaseRestHandler {

    @Inject
    public LearnRestAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(GET, "/_learn", this);
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        // Implement the REST logic here
        return null;
    }
}
