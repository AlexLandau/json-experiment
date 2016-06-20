package net.alloyggp.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class ObjectWeaver<T> implements Weaver<T> {

    @Override
    public T parse(JsonNode node) {
        assert node.isObject();
        ObjectNode jsonObject = (ObjectNode) node;
        ObjectReader reader = new ObjectReader(jsonObject);
        return parseObject(reader);
    }

    protected abstract T parseObject(ObjectReader reader);

    @Override
    public JsonNode weave(T object) {
        ObjectWriter writer = new ObjectWriter();
        writeObject(object, writer);
        return writer.write();
    }

    protected abstract void writeObject(T object, ObjectWriter writer);

}
