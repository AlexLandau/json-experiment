package net.alloyggp.json;

import com.fasterxml.jackson.databind.node.ArrayNode;

//TODO: Should probably be Iterable
public class ArrayReader {
    private final ArrayNode array;

    public ArrayReader(ArrayNode array) {
        this.array = array;
    }

    public <T> T get(int index, Weaver<T> weaver) {
        return weaver.parse(array.get(index));
    }

    public int size() {
        return array.size();
    }
}
