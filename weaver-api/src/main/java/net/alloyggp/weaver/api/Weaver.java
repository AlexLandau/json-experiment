package net.alloyggp.weaver.api;

public interface Weaver<T> {
    <N> T parse(N node, WeaverContext<N> context);
    <N> N weave(T object, WeaverContext<N> context);
}
