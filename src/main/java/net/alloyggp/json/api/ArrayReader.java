package net.alloyggp.json.api;

public interface ArrayReader {

    int size();

    <T> T get(int index, Weaver<T> weaver);

}
