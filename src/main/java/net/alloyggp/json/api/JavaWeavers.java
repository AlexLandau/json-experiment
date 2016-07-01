package net.alloyggp.json.api;

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

public class JavaWeavers {
    private JavaWeavers() {
        //Not instantiable
    }

    public static Weaver<Integer> INTEGER = new Weaver<Integer>() {
        @Override
        public <N> Integer parse(N node, WeaverContext<N> context) {
            return context.parseInteger(node);
        }

        @Override
        public <N> N weave(Integer object, WeaverContext<N> context) {
            return context.persistInteger(object);
        }
    };

    public static Weaver<Long> LONG = new Weaver<Long>() {
        @Override
        public <N> Long parse(N node, WeaverContext<N> context) {
            return context.parseLong(node);
        }

        @Override
        public <N> N weave(Long object, WeaverContext<N> context) {
            return context.persistLong(object);
        }
    };

    public static Weaver<Double> DOUBLE = new Weaver<Double>() {
        @Override
        public <N> Double parse(N node, WeaverContext<N> context) {
            return context.parseDouble(node);
        }

        @Override
        public <N> N weave(Double object, WeaverContext<N> context) {
            return context.persistDouble(object);
        }
    };

    public static Weaver<Float> FLOAT = new Weaver<Float>() {
        @Override
        public <N> Float parse(N node, WeaverContext<N> context) {
            return context.parseFloat(node);
        }

        @Override
        public <N> N weave(Float object, WeaverContext<N> context) {
            return context.persistFloat(object);
        }
    };

    public static Weaver<Short> SHORT = new Weaver<Short>() {
        @Override
        public <N> Short parse(N node, WeaverContext<N> context) {
            return context.parseShort(node);
        }

        @Override
        public <N> N weave(Short object, WeaverContext<N> context) {
            return context.persistShort(object);
        }
    };

    public static Weaver<Byte> BYTE = new Weaver<Byte>() {
        @Override
        public <N> Byte parse(N node, WeaverContext<N> context) {
            return context.parseByte(node);
        }

        @Override
        public <N> N weave(Byte object, WeaverContext<N> context) {
            return context.persistByte(object);
        }
    };

    public static Weaver<Character> CHARACTER = new Weaver<Character>() {
        @Override
        public <N> Character parse(N node, WeaverContext<N> context) {
            return context.parseCharacter(node);
        }

        @Override
        public <N> N weave(Character object, WeaverContext<N> context) {
            return context.persistCharacter(object);
        }
    };

    public static Weaver<Boolean> BOOLEAN = new Weaver<Boolean>() {
        @Override
        public <N> Boolean parse(N node, WeaverContext<N> context) {
            return context.parseBoolean(node);
        }

        @Override
        public <N> N weave(Boolean object, WeaverContext<N> context) {
            return context.persistBoolean(object);
        }
    };

    public static Weaver<String> STRING = new Weaver<String>() {
        @Override
        public <N> String parse(N node, WeaverContext<N> context) {
            return context.parseString(node);
        }

        @Override
        public <N> N weave(String object, WeaverContext<N> context) {
            return context.persistString(object);
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
            protected void writeArray(List<T> list, ArrayWriter<?> writer) {
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
            protected void writeArray(Set<T> set, ArrayWriter<?> writer) {
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
            protected void writeArray(SortedSet<T> set, ArrayWriter<?> writer) {
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
            protected void writeArray(SortedSet<T> set, ArrayWriter<?> writer) {
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
            protected void writeObject(Map<String, T> object, ObjectWriter<?> writer) {
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
            protected void writeObject(Map<K, V> object, ObjectWriter<?> writer) {
                for (Map.Entry<K, V> entry : object.entrySet()) {
                    writer.put(toStringFunction.apply(entry.getKey()), entry.getValue(), valueWeaver);
                }
            }
        };
    }

    public static <E extends Enum<E>> Weaver<E> enumOf(final Class<E> enumClass) {
        return new Weaver<E>() {
            @Override
            public <N> E parse(N node, WeaverContext<N> context) {
                return context.parseEnumValue(node, enumClass);
            }

            @Override
            public <N> N weave(E object, WeaverContext<N> context) {
                return context.persistEnumValue(object, enumClass);
            }
        };
    }
}
