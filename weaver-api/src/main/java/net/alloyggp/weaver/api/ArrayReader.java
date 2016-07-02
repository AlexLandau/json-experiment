package net.alloyggp.weaver.api;

public interface ArrayReader {
    int size();

    <T> T get(int index, Weaver<T> weaver);
}
