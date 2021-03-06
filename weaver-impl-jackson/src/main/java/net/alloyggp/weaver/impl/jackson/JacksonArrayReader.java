package net.alloyggp.weaver.impl.jackson;

import com.fasterxml.jackson.databind.node.ArrayNode;

import net.alloyggp.weaver.api.ArrayReader;
import net.alloyggp.weaver.api.Weaver;

//TODO: Should probably be Iterable
public class JacksonArrayReader implements ArrayReader {
    private final ArrayNode array;

    public JacksonArrayReader(ArrayNode array) {
        this.array = array;
    }

    @Override
    public <T> T get(int index, Weaver<T> weaver) {
        return weaver.parse(array.get(index), JacksonWeaverContext.INSTANCE);
    }

    @Override
    public int size() {
        return array.size();
    }
}
