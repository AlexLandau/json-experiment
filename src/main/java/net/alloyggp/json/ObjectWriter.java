package net.alloyggp.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectWriter {
    private final ObjectNode node = new ObjectNode(JsonNodeFactory.instance);

    public <T> void put(String fieldName, T object, Weaver<T> weaver) {
        node.replace(fieldName, weaver.weave(object));
    }

    //TODO: Either make this un-reusable, or copy defensively
    public ObjectNode write() {
        return node;
    }

}
