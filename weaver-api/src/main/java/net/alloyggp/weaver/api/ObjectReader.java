package net.alloyggp.weaver.api;

public interface ObjectReader {
    int size();

    Iterable<String> getFieldNames();

    <T> T get(String fieldName, Weaver<T> valueWeaver);
}
