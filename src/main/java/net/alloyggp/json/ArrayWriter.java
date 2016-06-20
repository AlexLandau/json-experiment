package net.alloyggp.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class ArrayWriter {
    //TODO: Should this just add to an ArrayNode directly?
    //Requires ___.
    private final List<JsonNode> nodes = new ArrayList<>();

    public ArrayNode write() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
        arrayNode.addAll(nodes);
        return arrayNode;
    }

    //TODO: Either make this un-reusable, or copy defensively
    public <T> void add(T object, Weaver<T> weaver) {
        nodes.add(weaver.weave(object));
    }
}
