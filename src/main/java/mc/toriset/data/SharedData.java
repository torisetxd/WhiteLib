package mc.toriset.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class SharedData {
    private static final Map<String, Object> dataStore = new ConcurrentHashMap<>();

    public static void set(String key, Object value) {
        dataStore.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) dataStore.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key, T defaultValue) {
        return (T) dataStore.getOrDefault(key, defaultValue);
    }

    public static boolean has(String key) {
        return dataStore.containsKey(key);
    }

    public static void remove(String key) {
        dataStore.remove(key);
    }

    public static void clear() {
        dataStore.clear();
    }

    public static Integer increment(String key) {
        return transformNumeric(key, i -> i + 1, 1);
    }

    public static Integer increment(String key, int amount) {
        return transformNumeric(key, i -> i + amount, amount);
    }

    public static Integer decrement(String key) {
        return transformNumeric(key, i -> i - 1, -1);
    }

    public static Integer decrement(String key, int amount) {
        return transformNumeric(key, i -> i - amount, -amount);
    }

    public static Double incrementDouble(String key) {
        return transformDoubleNumeric(key, d -> d + 1.0, 1.0);
    }

    public static Double incrementDouble(String key, double amount) {
        return transformDoubleNumeric(key, d -> d + amount, amount);
    }

    public static Double decrementDouble(String key) {
        return transformDoubleNumeric(key, d -> d - 1.0, -1.0);
    }

    public static Double decrementDouble(String key, double amount) {
        return transformDoubleNumeric(key, d -> d - amount, -amount);
    }

    private static Integer transformNumeric(String key, Function<Integer, Integer> operation, Integer defaultValue) {
        synchronized (dataStore) {
            Object current = dataStore.get(key);
            Integer result;
            
            if (current instanceof Number) {
                result = operation.apply(((Number) current).intValue());
            } else {
                result = operation.apply(0);
            }
            
            dataStore.put(key, result);
            return result;
        }
    }

    private static Double transformDoubleNumeric(String key, Function<Double, Double> operation, Double defaultValue) {
        synchronized (dataStore) {
            Object current = dataStore.get(key);
            Double result;
            
            if (current instanceof Number) {
                result = operation.apply(((Number) current).doubleValue());
            } else {
                result = operation.apply(0.0);
            }
            
            dataStore.put(key, result);
            return result;
        }
    }
}