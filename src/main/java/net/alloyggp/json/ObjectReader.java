package net.alloyggp.json;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.alloyggp.json.api.Weaver;

public class ObjectReader {
    private final ObjectNode node;

    public ObjectReader(ObjectNode node) {
        this.node = node;
    }

    //TODO: Find some way(s) to distinguish absent vs. null
    @Nullable
    public <T> T get(String fieldName, Weaver<T> weaver) {
        JsonNode field = node.get(fieldName);
        if (field == null || field instanceof NullNode) {
            return null;
        }
        return weaver.parse(field);
    }

    public int size() {
        return node.size();
    }

    //Return Iterable instead of Iterator to support for-each loops.
    public Iterable<String> getFieldNames() {
        return () -> node.fieldNames();
    }
}
