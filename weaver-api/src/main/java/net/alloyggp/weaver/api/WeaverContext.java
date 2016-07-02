package net.alloyggp.weaver.api;

public interface WeaverContext<N> {
    ArrayReader createArrayReader(N node);
    ObjectReader createObjectReader(N node);
    ArrayWriter<N> createArrayWriter();
    ObjectWriter<N> createObjectWriter();

    Integer parseInteger(N node);
    N persistInteger(Integer object);

    Long parseLong(N node);
    N persistLong(Long object);

    Double parseDouble(N node);
    N persistDouble(Double object);

    Float parseFloat(N node);
    N persistFloat(Float object);

    Short parseShort(N node);
    N persistShort(Short object);

    Byte parseByte(N node);
    N persistByte(Byte object);

    Character parseCharacter(N node);
    N persistCharacter(Character object);

    Boolean parseBoolean(N node);
    N persistBoolean(Boolean object);

    String parseString(N node);
    N persistString(String object);

    <E extends Enum<E>> E parseEnumValue(N node, Class<E> enumClass);
    <E extends Enum<E>> N persistEnumValue(E object, Class<E> enumClass);
}
