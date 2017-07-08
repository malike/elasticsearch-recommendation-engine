package st.malike.elastic.recommendation.engine;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.*;
import st.malike.elastic.recommendation.engine.util.Enums;
import st.malike.elastic.recommendation.engine.util.JSONResponse;

import java.io.IOException;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestRequest.Method.POST;

/**
 * @author malike_st
 */
public class LearnRecommendationRestAction extends BaseRestHandler {

    @Inject
    public LearnRecommendationRestAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(POST, "/_learnrecommendation", this);
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        return channel -> {
            JSONResponse message = new JSONResponse();
            message.setStatus(true);
            message.setCount(1L);
            message.setMessage(Enums.JSONResponseMessage.SUCCESS.toString());
            XContentBuilder builder = channel.newBuilder();
            builder.startObject();
            message.toXContent(builder, restRequest);
            builder.endObject();
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
        };
    }
}
