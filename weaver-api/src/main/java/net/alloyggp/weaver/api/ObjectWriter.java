package net.alloyggp.weaver.api;

public interface ObjectWriter<N> {
    N write();

    <T> void put(String apply, T value, Weaver<T> valueWeaver);
}
