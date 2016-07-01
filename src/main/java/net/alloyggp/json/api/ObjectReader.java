package net.alloyggp.json.api;

public interface ObjectReader {

    int size();

    Iterable<String> getFieldNames();

    <T> T get(String fieldName, Weaver<T> valueWeaver);

}
