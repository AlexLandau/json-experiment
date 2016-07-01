package net.alloyggp.json.api;

public abstract class ObjectWeaver<T> implements Weaver<T> {
    @Override
    public <N> T parse(N node, WeaverContext<N> context) {
        ObjectReader reader = context.createObjectReader(node);
        return parseObject(reader);
    }

    protected abstract T parseObject(ObjectReader reader);

    @Override
    public <N> N weave(T object, WeaverContext<N> context) {
        ObjectWriter<N> writer = context.createObjectWriter();
        writeObject(object, writer);
        return writer.write();
    }

    protected abstract void writeObject(T object, ObjectWriter<?> writer);
}
