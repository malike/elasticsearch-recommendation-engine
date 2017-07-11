package st.malike.elastic.recommendation.engine;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.*;
import st.malike.elastic.recommendation.engine.util.JSONResponse;

import java.io.IOException;

import static org.elasticsearch.rest.RestRequest.Method.GET;

/**
 * malike_st.
 */
public class FetchRecommendedRestAction extends BaseRestHandler {

    @Inject
    public FetchRecommendedRestAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(GET, "/_getrecommended", this);
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
//        String name = restRequest.param("name");
        return channel -> {
            JSONResponse message = new JSONResponse();
            message.setStatus(true);
            XContentBuilder builder = channel.newBuilder();
            builder.startObject();
            message.toXContent(builder, restRequest);
            builder.endObject();
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
        };
    }
}
