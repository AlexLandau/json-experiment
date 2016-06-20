package net.alloyggp.json;

import com.fasterxml.jackson.databind.JsonNode;

public interface Weaver<T> {
    T parse(JsonNode node);
    JsonNode weave(T object);
}
