package net.alloyggp.json.api;

public abstract class ArrayWeaver<T> implements Weaver<T> {

    @Override
    public <N> T parse(N node, WeaverContext<N> context) {
//        assert node.isArray();
//        ArrayNode array = (ArrayNode) node;
        ArrayReader reader = context.createArrayReader(node);//new ArrayReader(array);
        return parseArray(reader);
    }

    protected abstract T parseArray(ArrayReader reader);

    @Override
    public <N> N weave(T object, WeaverContext<N> context) {
        ArrayWriter<N> writer = context.createArrayWriter();
        writeArray(object, writer);
        return writer.write();
    }

    protected abstract void writeArray(T object, ArrayWriter<?> writer);
}
