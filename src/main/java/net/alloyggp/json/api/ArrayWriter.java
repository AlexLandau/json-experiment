package net.alloyggp.json.api;

public interface ArrayWriter<N> {
    N write();

    <T> void add(T object, Weaver<T> innerWeaver);
}
