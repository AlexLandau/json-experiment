package net.alloyggp.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public abstract class ArrayWeaver<T> implements Weaver<T> {

    @Override
    public T parse(JsonNode node) {
        assert node.isArray();
        ArrayNode array = (ArrayNode) node;
        ArrayReader reader = new ArrayReader(array);
        return parseArray(reader);
    }

    protected abstract T parseArray(ArrayReader reader);

    @Override
    public JsonNode weave(T object) {
        ArrayWriter writer = new ArrayWriter();
        writeArray(object, writer);
        return writer.write();
    }

    protected abstract void writeArray(T object, ArrayWriter writer);
}
