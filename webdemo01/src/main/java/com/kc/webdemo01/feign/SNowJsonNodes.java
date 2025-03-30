//package com.kc.webdemo01.feign;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.node.NullNode;
//
///**
// * @author KCWang
// * @version 1.0
// * @date 2025/3/7 下午7:32
// */
//public class SNowJsonNodes extends JsonNodes {
//
//    public static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper()
//            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
//            .configure(SerializationFeature.INDENT_OUTPUT, true)
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//    public final static SNowJsonNodes NULL = new SNowJsonNodes(NullNode.getInstance());
//
//    public SNowJsonNodes(JsonNode jsonNode) {
//        super(jsonNode, DEFAULT_OBJECT_MAPPER);
//    }
//
//    public static SNowJsonNodes of(JsonNode jsonNode) {
//        return new SNowJsonNodes(jsonNode);
//    }
//
//    public static SNowJsonNodes of(Object value) {
//        return of(DEFAULT_OBJECT_MAPPER.convertValue(value, JsonNode.class));
//    }
//
//    public static SNowJsonNodes of(String content) {
//        try {
//            return of(DEFAULT_OBJECT_MAPPER.readTree(content));
//        } catch (JsonProcessingException e) {
//            throw SNowException.of(e);
//        }
//    }
//
//    public JsonNodes orElseThrow() {
//        JsonNodes error = this.get("error");
//        if (!error.isNull()) {
//            var detailJsonNodes = error.get("detail");
//            if (detailJsonNodes.isNull()) {
//                detailJsonNodes = error.get("error_description");
//            }
//            throw new SNowException(detailJsonNodes.asText());
//        }
//        return this.get("result");
//    }
//}