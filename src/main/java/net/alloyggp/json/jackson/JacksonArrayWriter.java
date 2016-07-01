package net.alloyggp.json.jackson;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import net.alloyggp.json.api.ArrayWriter;
import net.alloyggp.json.api.Weaver;

public class JacksonArrayWriter implements ArrayWriter<JsonNode> {
    //TODO: Should this just add to an ArrayNode directly?
    //Requires ___.
    private final List<JsonNode> nodes = new ArrayList<>();

    @Override
    public ArrayNode write() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
        arrayNode.addAll(nodes);
        return arrayNode;
    }

    //TODO: Either make this un-reusable, or copy defensively
    @Override
    public <T> void add(T object, Weaver<T> weaver) {
        nodes.add(weaver.weave(object, JacksonWeaverContext.INSTANCE));
    }
}
