package net.alloyggp.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.FloatNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ShortNode;
import com.fasterxml.jackson.databind.node.TextNode;

import net.alloyggp.json.api.ArrayReader;
import net.alloyggp.json.api.ArrayWriter;
import net.alloyggp.json.api.ObjectReader;
import net.alloyggp.json.api.ObjectWriter;
import net.alloyggp.json.api.WeaverContext;

public class JacksonWeaverContext implements WeaverContext<JsonNode> {
    public static final JacksonWeaverContext INSTANCE = new JacksonWeaverContext();

    private JacksonWeaverContext() {
        //Singleton
    }

    @Override
    public ArrayReader createArrayReader(JsonNode node) {
        assert node.isArray();
        ArrayNode array = (ArrayNode) node;
        return new JacksonArrayReader(array);
    }

    @Override
    public ObjectReader createObjectReader(JsonNode node) {
        assert node.isObject();
        ObjectNode jsonObject = (ObjectNode) node;
        return new JacksonObjectReader(jsonObject);
    }

    @Override
    public ArrayWriter<JsonNode> createArrayWriter() {
        return new JacksonArrayWriter();
    }

    @Override
    public ObjectWriter<JsonNode> createObjectWriter() {
        return new JacksonObjectWriter();
    }

    @Override
    public Integer parseInteger(JsonNode node) {
        if (!node.canConvertToInt()) {
            throw new RuntimeException("Value of JsonNode cannot be converted to an integer: " + node);
        }
        return node.intValue();
    }

    @Override
    public JsonNode persistInteger(Integer i) {
        return IntNode.valueOf(i);
    }

    @Override
    public Long parseLong(JsonNode node) {
        if (!node.canConvertToLong()) {
            throw new RuntimeException("Value of JsonNode cannot be converted to a long: " + node);
        }
        return node.longValue();
    }

    @Override
    public JsonNode persistLong(Long l) {
        return LongNode.valueOf(l);
    }

    @Override
    public Double parseDouble(JsonNode node) {
        assert node.isDouble();
        return node.doubleValue();
    }

    @Override
    public JsonNode persistDouble(Double d) {
        return DoubleNode.valueOf(d);
    }

    @Override
    public Float parseFloat(JsonNode node) {
        assert node.isFloat();
        return node.floatValue();
    }

    @Override
    public JsonNode persistFloat(Float f) {
        return FloatNode.valueOf(f);
    }

    @Override
    public Short parseShort(JsonNode node) {
        assert node.canConvertToInt();
        int intValue = node.intValue();
        assert intValue >= Short.MIN_VALUE && intValue <= Short.MAX_VALUE;
        return (short) intValue;
    }

    @Override
    public JsonNode persistShort(Short s) {
        return ShortNode.valueOf(s);
    }

    @Override
    public Byte parseByte(JsonNode node) {
        assert node.canConvertToInt();
        int intValue = node.intValue();
        assert intValue >= Byte.MIN_VALUE && intValue <= Byte.MAX_VALUE;
        return (byte) intValue;
    }

    @Override
    public JsonNode persistByte(Byte b) {
        return ShortNode.valueOf(b);
    }

    @Override
    public Character parseCharacter(JsonNode node) {
        assert node.isTextual();
        String text = node.asText();
        assert text.length() == 1;
        return text.charAt(0);
    }

    @Override
    public JsonNode persistCharacter(Character c) {
        return new TextNode(String.valueOf(c));
    }

    @Override
    public Boolean parseBoolean(JsonNode node) {
        assert node.isBoolean();
        return node.asBoolean();
    }

    @Override
    public JsonNode persistBoolean(Boolean b) {
        return BooleanNode.valueOf(b);
    }

    @Override
    public String parseString(JsonNode node) {
        assert node.isTextual();
        return node.asText();
    }

    @Override
    public JsonNode persistString(String string) {
        return new TextNode(string);
    }

    @Override
    public <E extends Enum<E>> E parseEnumValue(JsonNode node, Class<E> enumClass) {
        String name = node.asText();
        return Enum.valueOf(enumClass, name);
    }

    @Override
    public <E extends Enum<E>> JsonNode persistEnumValue(E object, Class<E> enumClass) {
        String name = object.name();
        return new TextNode(name);
    }

}
