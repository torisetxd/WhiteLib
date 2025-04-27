package mc.toriset.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class TypedDataUtils {
    public static String getString(String key, String defaultValue) {
        Object value = SharedData.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        return defaultValue;
    }

    public static Integer getInt(String key, Integer defaultValue) {
        Object value = SharedData.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return defaultValue;
    }

    public static Double getDouble(String key, Double defaultValue) {
        Object value = SharedData.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return defaultValue;
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        Object value = SharedData.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return defaultValue;
    }

    public static Long getLong(String key, Long defaultValue) {
        Object value = SharedData.get(key);
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return defaultValue;
    }

    public static UUID getUUID(String key, UUID defaultValue) {
        Object value = SharedData.get(key);
        if (value instanceof UUID) {
            return (UUID) value;
        } else if (value instanceof String) {
            try {
                return UUID.fromString((String) value);
            } catch (IllegalArgumentException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(String key, List<T> defaultValue) {
        Object value = SharedData.get(key);
        if (value instanceof List) {
            return (List<T>) value;
        }
        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getMap(String key, Map<K, V> defaultValue) {
        Object value = SharedData.get(key);
        if (value instanceof Map) {
            return (Map<K, V>) value;
        }
        return defaultValue;
    }

    public static List<String> getStringList(String key) {
        return getList(key, new ArrayList<>());
    }

    public static List<Integer> getIntList(String key) {
        return getList(key, new ArrayList<>());
    }

    public static List<Double> getDoubleList(String key) {
        return getList(key, new ArrayList<>());
    }

    public static Map<String, Object> getObjectMap(String key) {
        return getMap(key, new HashMap<>());
    }

    public static <T> T getOrCreate(String key, Function<String, T> factory) {
        T value = SharedData.get(key);
        if (value == null) {
            value = factory.apply(key);
            SharedData.set(key, value);
        }
        return value;
    }
}