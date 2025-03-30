//package com.kc.webdemo01.feign;
//
///**
// * @author KCWang
// * @version 1.0
// * @date 2025/3/7 下午7:26
// */
//
//import com.fasterxml.jackson.core.*;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.*;
//import org.springframework.util.CollectionUtils;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import java.util.stream.StreamSupport;
//
//public class JsonNodes extends BaseJsonNode<JsonNodes> {
//    public static final MapTypeReference MAP_TYPE = new MapTypeReference();
//    public static final ListTypeReference LIST_TYPE = new ListTypeReference();
//
//    protected final ObjectMapper objectMapper;
//    protected final JsonNode jsonNode;
//
//    public JsonNodes(JsonNode jsonNode, ObjectMapper objectMapper) {
//        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper");
//        this.jsonNode = Objects.requireNonNullElseGet(jsonNode, NullNode::getInstance);
//    }
//
//    public JsonNodes(String content, ObjectMapper objectMapper) throws JsonProcessingException {
//        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper");
//        this.jsonNode = objectMapper.readTree(content);
//    }
//
//    @Override
//    public ObjectMapper getObjectMapper() {
//        return objectMapper;
//    }
//
//    @Override
//    public JsonNode getRawJsonNode() {
//        return jsonNode;
//    }
//
//    @Override
//    public JsonNodes newBuilder(JsonNode jsonNode) {
//        return new JsonNodes(jsonNode, objectMapper);
//    }
//
//    public Map<String, Object> asMap() {
//        return this.convertValue(MAP_TYPE);
//    }
//
//    public List<Object> asList() {
//        return this.convertValue(LIST_TYPE);
//    }
//
//    public static class MapTypeReference extends TypeReference<Map<String, Object>> {
//
//    }
//
//    public static final class ListTypeReference extends TypeReference<List<Object>> {
//
//    }
//
//    // region 扩展函数
//
//    /**
//     * 如果不是 ARRAY 则包裹为 ARRAY
//     */
//    public JsonNodes wrap() {
//        return switch (this.getNodeType()) {
//            case NULL, MISSING -> this.newArrayNode();
//            case ARRAY -> this;
//            default -> this.newArrayNode(this);
//        };
//    }
//
//    /**
//     * 如果是 ARRAY 则取第一个
//     */
//    public JsonNodes first() {
//        return this.getNodeType() == JsonNodeType.ARRAY ? this.get(0) : this;
//    }
//
//    /**
//     * 如果是 STRING 则直接 strip, ARRAY|OBJECT 则递归
//     */
//    public JsonNodes strip() {
//        return switch (this.getNodeType()) {
//            case STRING -> this.newTextNode(Optional.ofNullable(this.asText(null)).map(String::strip).orElse(null));
//            case ARRAY -> this.newArrayNode(this.stream().map(JsonNodes::strip).toList());
//            case OBJECT ->
//                    this.newObjectNode(StreamSupport.stream(Spliterators.spliterator(this.fields(), this.size(), 0), false)
//                            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().strip())));
//            default -> this;
//        };
//    }
//    // endregion
//}
//
//class IteratorProxy<T, D> implements Iterator<T> {
//    private final Iterator<D> delegate;
//    private final Function<D, T> convert;
//
//    public IteratorProxy(Iterator<D> delegate, Function<D, T> convert) {
//        this.delegate = delegate;
//        this.convert = convert;
//    }
//
//    @Override
//    public boolean hasNext() {
//        return delegate.hasNext();
//    }
//
//    @Override
//    public T next() {
//        return convert.apply(delegate.next());
//    }
//}
//
//interface AbstractJsonNode<T extends AbstractJsonNode<T>> extends TreeNode, Iterable<T> {
//
//    ObjectMapper getObjectMapper();
//
//    JsonNode getRawJsonNode();
//
//    T newBuilder(JsonNode jsonNode);
//
//    default List<T> newBuilders(List<JsonNode> jsonNodes) {
//        return jsonNodes.stream()
//                .map(this::newBuilder)
//                .toList();
//    }
//
//    /*
//    /**********************************************************
//    /* Creator
//    /**********************************************************
//     */
//    default T newArrayNode(T... children) {
//        return newArrayNode(List.of(children));
//    }
//
//    default T newArrayNode(List<T> children) {
//        ArrayNode node = this.getObjectMapper().createArrayNode();
//        if (!CollectionUtils.isEmpty(children)) {
//            children.forEach((e) -> node.add(e.getRawJsonNode()));
//        }
//        return newBuilder(node);
//    }
//
//    default T newObjectNode(String key, T value) {
//        return newObjectNode(Map.of(key, value));
//    }
//
//    default T newObjectNode(Map<String, T> kids) {
//        ObjectNode node = this.getObjectMapper().createObjectNode();
//        if (!CollectionUtils.isEmpty(kids)) {
//            kids.forEach((k, v) -> node.set(k, v.getRawJsonNode()));
//        }
//        return newBuilder(node);
//    }
//
//    default T newNullNode() {
//        return newBuilder(NullNode.getInstance());
//    }
//
//    default T newMissingNode() {
//        return newBuilder(MissingNode.getInstance());
//    }
//
//    private <V> JsonNode newJsonNode(V v, Function<V, JsonNode> action) {
//        return Objects.isNull(v) ? NullNode.getInstance() : action.apply(v);
//    }
//
//    default T newBigIntegerNode(BigInteger v) {
//        return newBuilder(newJsonNode(v, BigIntegerNode::valueOf));
//    }
//
//    default T newDecimalNode(BigDecimal v) {
//        return newBuilder(newJsonNode(v, DecimalNode::valueOf));
//    }
//
//    default T newBinaryNode(byte[] v) {
//        return newBuilder(newJsonNode(v, BinaryNode::valueOf));
//    }
//
//    default T newTextNode(String v) {
//        return newBuilder(newJsonNode(v, TextNode::valueOf));
//    }
//
//    default T newPOJONode(Object v) {
//        return newBuilder(newJsonNode(v, POJONode::new));
//    }
//
//    default T newBooleanNode(boolean v) {
//        return newBuilder(BooleanNode.valueOf(v));
//    }
//
//    default T newDoubleNode(double v) {
//        return newBuilder(DoubleNode.valueOf(v));
//    }
//
//    default T newFloatNode(float v) {
//        return newBuilder(FloatNode.valueOf(v));
//    }
//
//    default T newIntNode(int v) {
//        return newBuilder(IntNode.valueOf(v));
//    }
//
//    default T newLongNode(long v) {
//        return newBuilder(LongNode.valueOf(v));
//    }
//
//    default T newShortNode(short v) {
//        return newBuilder(ShortNode.valueOf(v));
//    }
//
//    /*
//    /**********************************************************
//    /* stream
//    /**********************************************************
//     */
//    default Stream<T> stream() {
//        return StreamSupport.stream(spliterator(), false);
//    }
//
//    default Stream<T> parallelStream() {
//        return StreamSupport.stream(spliterator(), true);
//    }
//
//    default <K> Map<K, T> toMap(Function<T, K> keyMapper) {
//        return stream().collect(Collectors.toMap(keyMapper, Function.identity()));
//    }
//
//    default List<T> toList() {
//        return stream().toList();
//    }
//
//
//    /*
//    /**********************************************************
//    /* convert
//    /**********************************************************
//     */
//    default <R> R convertValue(Class<R> toValueType) throws IllegalArgumentException {
//        return this.getObjectMapper().convertValue(this.getRawJsonNode(), toValueType);
//    }
//
//    default <R> R convertValue(TypeReference<R> toValueTypeRef) throws IllegalArgumentException {
//        return this.getObjectMapper().convertValue(this.getRawJsonNode(), toValueTypeRef);
//    }
//
//    default <R> R convertValue(JavaType toValueType) throws IllegalArgumentException {
//        return this.getObjectMapper().convertValue(this.getRawJsonNode(), toValueType);
//    }
//}
//
//abstract class BaseJsonNode<T extends BaseJsonNode<T>> implements AbstractJsonNode<T> {
//
//    public T deepCopy() {
//        return newBuilder(getRawJsonNode().deepCopy());
//    }
//
//    /*
//    /**********************************************************
//    /* TreeNode implementation
//    /**********************************************************
//     */
//
//    @Override
//    public JsonToken asToken() {
//        return getRawJsonNode().asToken();
//    }
//
//    @Override
//    public JsonParser traverse() {
//        return getRawJsonNode().traverse();
//    }
//
//    @Override
//    public JsonParser traverse(ObjectCodec codec) {
//        return getRawJsonNode().traverse(codec);
//    }
//
//    @Override
//    public JsonParser.NumberType numberType() {
//        return getRawJsonNode().numberType();
//    }
//
//    @Override
//    public int size() {
//        return getRawJsonNode().size();
//    }
//
//    public boolean isEmpty() {
//        return getRawJsonNode().isEmpty();
//    }
//
//    @Override
//    public final boolean isValueNode() {
//        return getRawJsonNode().isValueNode();
//    }
//
//    @Override
//    public final boolean isContainerNode() {
//        return getRawJsonNode().isContainerNode();
//    }
//
//    @Override
//    public boolean isMissingNode() {
//        return getRawJsonNode().isMissingNode();
//    }
//
//    @Override
//    public boolean isArray() {
//        return getRawJsonNode().isArray();
//    }
//
//    @Override
//    public boolean isObject() {
//        return getRawJsonNode().isObject();
//    }
//
//    @Override
//    public T get(int index) {
//        return newBuilder(getRawJsonNode().get(index));
//    }
//
//    @Override
//    public T get(String fieldName) {
//        return newBuilder(getRawJsonNode().get(fieldName));
//    }
//
//    @Override
//    public T path(String fieldName) {
//        return newBuilder(getRawJsonNode().path(fieldName));
//    }
//
//    @Override
//    public T path(int index) {
//        return newBuilder(getRawJsonNode().path(index));
//    }
//
//    @Override
//    public Iterator<String> fieldNames() {
//        return getRawJsonNode().fieldNames();
//    }
//
//    @Override
//    public final T at(JsonPointer ptr) {
//        return newBuilder(getRawJsonNode().at(ptr));
//    }
//
//    @Override
//    public final T at(String jsonPtrExpr) {
//        return newBuilder(getRawJsonNode().at(jsonPtrExpr));
//    }
//
//    /*
//    /**********************************************************
//    /* Public API, type introspection
//    /**********************************************************
//     */
//    public JsonNodeType getNodeType() {
//        return getRawJsonNode().getNodeType();
//    }
//
//    public final boolean isPojo() {
//        return getRawJsonNode().isPojo();
//    }
//
//    public final boolean isNumber() {
//        return getRawJsonNode().isNumber();
//    }
//
//    public boolean isIntegralNumber() {
//        return getRawJsonNode().isIntegralNumber();
//    }
//
//    public boolean isFloatingPointNumber() {
//        return getRawJsonNode().isFloatingPointNumber();
//    }
//
//    public boolean isShort() {
//        return getRawJsonNode().isShort();
//    }
//
//    public boolean isInt() {
//        return getRawJsonNode().isInt();
//    }
//
//    public boolean isLong() {
//        return getRawJsonNode().isLong();
//    }
//
//    public boolean isFloat() {
//        return getRawJsonNode().isFloat();
//    }
//
//    public boolean isDouble() {
//        return getRawJsonNode().isDouble();
//    }
//
//    public boolean isBigDecimal() {
//        return getRawJsonNode().isBigDecimal();
//    }
//
//    public boolean isBigInteger() {
//        return getRawJsonNode().isBigInteger();
//    }
//
//    public final boolean isTextual() {
//        return getRawJsonNode().isTextual();
//    }
//
//    public final boolean isBoolean() {
//        return getRawJsonNode().isBoolean();
//    }
//
//    public final boolean isNull() {
//        return getRawJsonNode().isNull();
//    }
//
//    public final boolean isBinary() {
//        return getRawJsonNode().isBinary();
//    }
//
//    public boolean canConvertToInt() {
//        return getRawJsonNode().canConvertToInt();
//    }
//
//    public boolean canConvertToLong() {
//        return getRawJsonNode().canConvertToLong();
//    }
//
//    /*
//    /**********************************************************
//    /* Public API, straight value access
//    /**********************************************************
//     */
//
//    public String textValue() {
//        return getRawJsonNode().textValue();
//    }
//
//    public byte[] binaryValue() throws IOException {
//        return getRawJsonNode().binaryValue();
//    }
//
//    public boolean booleanValue() {
//        return getRawJsonNode().booleanValue();
//    }
//
//    public Number numberValue() {
//        return getRawJsonNode().numberValue();
//    }
//
//    public short shortValue() {
//        return getRawJsonNode().shortValue();
//    }
//
//    public int intValue() {
//        return getRawJsonNode().intValue();
//    }
//
//    public long longValue() {
//        return getRawJsonNode().longValue();
//    }
//
//    public float floatValue() {
//        return getRawJsonNode().floatValue();
//    }
//
//    public double doubleValue() {
//        return getRawJsonNode().doubleValue();
//    }
//
//    public BigDecimal decimalValue() {
//        return getRawJsonNode().decimalValue();
//    }
//
//    public BigInteger bigIntegerValue() {
//        return getRawJsonNode().bigIntegerValue();
//    }
//
//    /*
//    /**********************************************************
//    /* Public API, value access with conversion(s)/coercion(s)
//    /**********************************************************
//     */
//
//    public String asText() {
//        return getRawJsonNode().asText();
//    }
//
//    public String asText(String defaultValue) {
//        return getRawJsonNode().asText(defaultValue);
//    }
//
//    public int asInt() {
//        return getRawJsonNode().asInt();
//    }
//
//    public int asInt(int defaultValue) {
//        return getRawJsonNode().asInt(defaultValue);
//    }
//
//    public long asLong() {
//        return getRawJsonNode().asLong();
//    }
//
//    public long asLong(long defaultValue) {
//        return getRawJsonNode().asLong(defaultValue);
//    }
//
//    public double asDouble() {
//        return getRawJsonNode().asDouble();
//    }
//
//    public double asDouble(double defaultValue) {
//        return getRawJsonNode().asDouble(defaultValue);
//    }
//
//    public boolean asBoolean() {
//        return getRawJsonNode().asBoolean();
//    }
//
//    public boolean asBoolean(boolean defaultValue) {
//        return getRawJsonNode().asBoolean(defaultValue);
//    }
//
//    /*
//    /**********************************************************************
//    /* Public API, extended traversal (2.10) with "required()"
//    /**********************************************************************
//     */
//
//    public T require() throws IllegalArgumentException {
//        return newBuilder(getRawJsonNode().require());
//    }
//
//    public T requireNonNull() throws IllegalArgumentException {
//        return newBuilder(getRawJsonNode().requireNonNull());
//    }
//
//    public T required(String fieldName) throws IllegalArgumentException {
//        return newBuilder(getRawJsonNode().required(fieldName));
//    }
//
//    public T required(int index) throws IllegalArgumentException {
//        return newBuilder(getRawJsonNode().required(index));
//    }
//
//    public T requiredAt(String pathExpr) throws IllegalArgumentException {
//        return newBuilder(getRawJsonNode().requiredAt(pathExpr));
//    }
//
//    public final T requiredAt(final JsonPointer path) throws IllegalArgumentException {
//        return newBuilder(getRawJsonNode().requiredAt(path));
//    }
//
//    /*
//    /**********************************************************
//    /* Public API, value find / existence check methods
//    /**********************************************************
//     */
//
//    public boolean has(String fieldName) {
//        return getRawJsonNode().has(fieldName);
//    }
//
//    public boolean has(int index) {
//        return getRawJsonNode().has(index);
//    }
//
//    public boolean hasNonNull(String fieldName) {
//        return getRawJsonNode().hasNonNull(fieldName);
//    }
//
//    public boolean hasNonNull(int index) {
//        return getRawJsonNode().hasNonNull(index);
//    }
//
//    /*
//    /**********************************************************
//    /* Public API, container access
//    /**********************************************************
//     */
//
//    @Override
//    public final Iterator<T> iterator() {
//        return elements();
//    }
//
//    public Iterator<T> elements() {
//        return new IteratorProxy<>(getRawJsonNode().elements(), this::newBuilder);
//    }
//
//    public Iterator<Map.Entry<String, T>> fields() {
//        return new IteratorProxy<>(getRawJsonNode().fields(), (e) -> new AbstractMap.SimpleEntry<>(e.getKey(), newBuilder(e.getValue())));
//    }
//
//    /*
//    /**********************************************************
//    /* Public API, find methods
//    /**********************************************************
//     */
//
//    public T findValue(String fieldName) {
//        return newBuilder(getRawJsonNode().findValue(fieldName));
//    }
//
//    public final List<T> findValues(String fieldName) {
//        return newBuilders(getRawJsonNode().findValues(fieldName));
//    }
//
//    public final List<String> findValuesAsText(String fieldName) {
//        return getRawJsonNode().findValuesAsText(fieldName);
//    }
//
//    public T findPath(String fieldName) {
//        return newBuilder(getRawJsonNode().findPath(fieldName));
//    }
//
//    public T findParent(String fieldName) {
//        return newBuilder(getRawJsonNode().findParent(fieldName));
//    }
//
//    public final List<T> findParents(String fieldName) {
//        return newBuilders(getRawJsonNode().findParents(fieldName));
//    }
//
//    public List<T> findValues(String fieldName, List<JsonNode> foundSoFar) {
//        return newBuilders(getRawJsonNode().findValues(fieldName, foundSoFar));
//    }
//
//    public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
//        return getRawJsonNode().findValuesAsText(fieldName, foundSoFar);
//    }
//
//    public List<T> findParents(String fieldName, List<JsonNode> foundSoFar) {
//        return newBuilders(getRawJsonNode().findParents(fieldName, foundSoFar));
//    }
//
//    /*
//    /**********************************************************
//    /* Public API, path handling
//    /**********************************************************
//     */
//
//    public T withArray(String propertyName) {
//        return newBuilder(getRawJsonNode().withArray(propertyName));
//    }
//
//    /*
//    /**********************************************************
//    /* Public API, comparison
//    /**********************************************************
//     */
//
//    public boolean equals(Comparator<JsonNode> comparator, JsonNode other) {
//        return comparator.compare(this.getRawJsonNode(), other) == 0;
//    }
//
//    /*
//    /**********************************************************
//    /* Overridden standard methods
//    /**********************************************************
//     */
//
//    @Override
//    public String toString() {
//        return getRawJsonNode().toString();
//    }
//
//    public String toPrettyString() {
//        return toString();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        return getRawJsonNode().equals(o) || (o instanceof JsonNodes jsonNodes && jsonNodes.getRawJsonNode().equals(getRawJsonNode()));
//    }
//}
