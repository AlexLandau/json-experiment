package net.alloyggp.json.api;

public interface ObjectWriter<T> {

    T write();

    <T> void put(String apply, T value, Weaver<T> valueWeaver);

}
