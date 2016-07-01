package net.alloyggp.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.alloyggp.json.api.ObjectWriter;
import net.alloyggp.json.api.Weaver;

public class JacksonObjectWriter implements ObjectWriter<JsonNode> {
    private final ObjectNode node = new ObjectNode(JsonNodeFactory.instance);

    @Override
    public <T> void put(String fieldName, T object, Weaver<T> weaver) {
        node.replace(fieldName, weaver.weave(object, JacksonWeaverContext.INSTANCE));
    }

    //TODO: Either make this un-reusable, or copy defensively
    @Override
    public ObjectNode write() {
        return node;
    }

}
