package net.alloyggp.json.jackson;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.alloyggp.json.api.ObjectReader;
import net.alloyggp.json.api.Weaver;

public class JacksonObjectReader implements ObjectReader {
    private final ObjectNode node;

    public JacksonObjectReader(ObjectNode node) {
        this.node = node;
    }

    //TODO: Find some way(s) to distinguish absent vs. null
    @Override
    @Nullable
    public <T> T get(String fieldName, Weaver<T> weaver) {
        JsonNode field = node.get(fieldName);
        if (field == null || field instanceof NullNode) {
            return null;
        }
        return weaver.parse(field, JacksonWeaverContext.INSTANCE);
    }

    @Override
    public int size() {
        return node.size();
    }

    //Return Iterable instead of Iterator to support for-each loops.
    @Override
    public Iterable<String> getFieldNames() {
        return () -> node.fieldNames();
    }
}
