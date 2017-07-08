package st.malike.elastic.recommendation.engine.util;

/**
 * @author malike_st
 */
public class Enums {

    public enum JSONResponseMessage {
        SUCCESS,
        MISSING_PARAM,
        ERROR
    }


    public enum RecommendatationEngine{
        WEKA,
        SPARK,
        MAHOUT
    }
}
