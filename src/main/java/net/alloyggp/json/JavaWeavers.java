package net.alloyggp.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.FloatNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ShortNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class JavaWeavers {
    private JavaWeavers() {
        //Not instantiable
    }

    public static Weaver<Integer> INTEGER = new Weaver<Integer>() {
        @Override
        public Integer parse(JsonNode node) {
            if (!node.canConvertToInt()) {
                throw new RuntimeException("Value of JsonNode cannot be converted to an integer: " + node);
            }
            return node.intValue();
        }

        @Override
        public JsonNode weave(Integer i) {
            return IntNode.valueOf(i);
        }
    };

    public static Weaver<Long> LONG = new Weaver<Long>() {
        @Override
        public Long parse(JsonNode node) {
            if (!node.canConvertToLong()) {
                throw new RuntimeException("Value of JsonNode cannot be converted to a long: " + node);
            }
            return node.longValue();
        }

        @Override
        public JsonNode weave(Long l) {
            return LongNode.valueOf(l);
        }
    };

    public static Weaver<Double> DOUBLE = new Weaver<Double>() {
        @Override
        public Double parse(JsonNode node) {
            assert node.isDouble();
            return node.doubleValue();
        }

        @Override
        public JsonNode weave(Double d) {
            return DoubleNode.valueOf(d);
        }
    };

    public static Weaver<Float> FLOAT = new Weaver<Float>() {
        @Override
        public Float parse(JsonNode node) {
            assert node.isFloat();
            return node.floatValue();
        }

        @Override
        public JsonNode weave(Float f) {
            return FloatNode.valueOf(f);
        }
    };

    public static Weaver<Short> SHORT = new Weaver<Short>() {
        @Override
        public Short parse(JsonNode node) {
            assert node.canConvertToInt();
            int intValue = node.intValue();
            assert intValue >= Short.MIN_VALUE && intValue <= Short.MAX_VALUE;
            return (short) intValue;
        }

        @Override
        public JsonNode weave(Short s) {
            return ShortNode.valueOf(s);
        }
    };

    public static Weaver<Byte> BYTE = new Weaver<Byte>() {
        @Override
        public Byte parse(JsonNode node) {
            assert node.canConvertToInt();
            int intValue = node.intValue();
            assert intValue >= Byte.MIN_VALUE && intValue <= Byte.MAX_VALUE;
            return (byte) intValue;
        }

        @Override
        public JsonNode weave(Byte b) {
            return ShortNode.valueOf(b);
        }
    };

    public static Weaver<Character> CHARACTER = new Weaver<Character>() {
        @Override
        public Character parse(JsonNode node) {
            assert node.isTextual();
            String text = node.asText();
            assert text.length() == 1;
            return text.charAt(0);
        }

        @Override
        public JsonNode weave(Character c) {
            return new TextNode(String.valueOf(c));
        }
    };

    public static Weaver<Boolean> BOOLEAN = new Weaver<Boolean>() {
        @Override
        public Boolean parse(JsonNode node) {
            assert node.isBoolean();
            return node.asBoolean();
        }

        @Override
        public JsonNode weave(Boolean b) {
            return BooleanNode.valueOf(b);
        }
    };

    public static Weaver<String> STRING = new Weaver<String>() {
        @Override
        public String parse(JsonNode node) {
            assert node.isTextual();
            return node.asText();
        }

        @Override
        public JsonNode weave(String string) {
            return new TextNode(string);
        }
    };

    //TODO: Maybe also have a version that instantiates Object[] instead? (No need for the class argument)
    public static <T> Weaver<T[]> arrayOf(Class<T> clazz, final Weaver<T> innerWeaver) {
        return new ArrayWeaver<T[]>() {
            @Override
            protected T[] parseArray(ArrayReader reader) {
                @SuppressWarnings("unchecked")
                T[] list = (T[]) Array.newInstance(clazz, reader.size());
                for (int i = 0; i < reader.size(); i++) {
                    list[i] = reader.get(i, innerWeaver);
                }
                return list;
            }

            @Override
            protected void writeArray(T[] list, ArrayWriter writer) {
                for (T object : list) {
                    writer.add(object, innerWeaver);
                }
            }
        };
    }

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

    public static <T extends Comparable<T>> Weaver<SortedSet<T>> sortedSetOf(final Weaver<T> innerWeaver) {
        return new ArrayWeaver<SortedSet<T>>() {
            @Override
            protected SortedSet<T> parseArray(ArrayReader reader) {
                SortedSet<T> set = new TreeSet<T>();
                for (int i = 0; i < reader.size(); i++) {
                    set.add(reader.get(i, innerWeaver));
                }
                return set;
            }

            @Override
            protected void writeArray(SortedSet<T> set, ArrayWriter writer) {
                for (T object : set) {
                    writer.add(object, innerWeaver);
                }
            }
        };
    }

    public static <T extends Comparable<T>> Weaver<SortedSet<T>> sortedSetOf(final Weaver<T> innerWeaver, final Comparator<T> comparator) {
        return new ArrayWeaver<SortedSet<T>>() {
            @Override
            protected SortedSet<T> parseArray(ArrayReader reader) {
                SortedSet<T> set = new TreeSet<T>(comparator);
                for (int i = 0; i < reader.size(); i++) {
                    set.add(reader.get(i, innerWeaver));
                }
                return set;
            }

            @Override
            protected void writeArray(SortedSet<T> set, ArrayWriter writer) {
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

    //TODO: Version with some sort of StringWeaver for the keys.
    public static <K, V> Weaver<Map<K, V>> mapOf(final Function<K, String> toStringFunction,
            final Function<String, K> fromStringFunction, final Weaver<V> valueWeaver) {
        return new ObjectWeaver<Map<K, V>>() {
            @Override
            protected Map<K, V> parseObject(ObjectReader reader) {
                Map<K, V> result = new HashMap<>(reader.size());
                for (String fieldName : reader.getFieldNames()) {
                    result.put(fromStringFunction.apply(fieldName), reader.get(fieldName, valueWeaver));
                }
                return result;
            }

            @Override
            protected void writeObject(Map<K, V> object, ObjectWriter writer) {
                for (Map.Entry<K, V> entry : object.entrySet()) {
                    writer.put(toStringFunction.apply(entry.getKey()), entry.getValue(), valueWeaver);
                }
            }
        };
    }
}
