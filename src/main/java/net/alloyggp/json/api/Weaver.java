package net.alloyggp.json.api;

public interface Weaver<T> {
    <N> T parse(N node, WeaverContext<N> context);
    <N> N weave(T object, WeaverContext<N> context);
}
