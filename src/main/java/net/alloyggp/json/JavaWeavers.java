package net.alloyggp.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class JavaWeavers {
    private JavaWeavers() {
        //Not instantiable
    }

    public static Weaver<Integer> INTEGER = new Weaver<Integer>() {
        @Override
        public JsonNode weave(Integer i) {
            return IntNode.valueOf(i);
        }

        @Override
        public Integer parse(JsonNode node) {
            if (!node.canConvertToInt()) {
                throw new RuntimeException("Value of JsonNode cannot be converted to an integer: " + node);
            }
            return node.intValue();
        }
    };

    public static Weaver<Long> LONG = new Weaver<Long>() {
        @Override
        public JsonNode weave(Long l) {
            return LongNode.valueOf(l);
        }

        @Override
        public Long parse(JsonNode node) {
            if (!node.canConvertToLong()) {
                throw new RuntimeException("Value of JsonNode cannot be converted to a long: " + node);
            }
            return node.longValue();
        }
    };

    public static Weaver<String> STRING = new Weaver<String>() {
        @Override
        public JsonNode weave(String string) {
            return new TextNode(string);
        }

        @Override
        public String parse(JsonNode node) {
            assert node.isTextual();
            return node.asText();
        }
    };

    public static <T> Weaver<List<T>> listOf(final Weaver<T> innerWeaver) {
        return new ArrayWeaver<List<T>>() {
            @Override
            protected List<T> parseArray(ArrayReader reader) {
                List<T> list = new ArrayList<T>(reader.size());
                for (int i = 0; i < reader.size(); i++) {
                    list.add(reader.get(i, innerWeaver));
                }
                return list;
            }

            @Override
            protected void writeArray(List<T> list, ArrayWriter writer) {
                for (T object : list) {
                    writer.add(object, innerWeaver);
                }
            }
        };
    }

    public static <T> Weaver<Set<T>> setOf(final Weaver<T> innerWeaver) {
        return new ArrayWeaver<Set<T>>() {
            @Override
            protected Set<T> parseArray(ArrayReader reader) {
                Set<T> set = new HashSet<T>(reader.size());
                for (int i = 0; i < reader.size(); i++) {
                    set.add(reader.get(i, innerWeaver));
                }
                return set;
            }

            @Override
            protected void writeArray(Set<T> set, ArrayWriter writer) {
                for (T object : set) {
                    writer.add(object, innerWeaver);
                }
            }
        };
    }

    public static <T> Weaver<Map<String, T>> mapOf(final Weaver<T> valueWeaver) {
        return new ObjectWeaver<Map<String, T>>() {
            @Override
            protected Map<String, T> parseObject(ObjectReader reader) {
                Map<String, T> result = new HashMap<>(reader.size());
                for (String fieldName : reader.getFieldNames()) {
                    result.put(fieldName, reader.get(fieldName, valueWeaver));
                }
                return result;
            }

            @Override
            protected void writeObject(Map<String, T> object, ObjectWriter writer) {
                for (Map.Entry<String, T> entry : object.entrySet()) {
                    writer.put(entry.getKey(), entry.getValue(), valueWeaver);
                }
            }
        };
    }
}
