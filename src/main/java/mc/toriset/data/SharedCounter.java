package mc.toriset.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SharedCounter {
    private static final Map<String, AtomicInteger> intCounters = new ConcurrentHashMap<>();
    private static final Map<String, AtomicLong> longCounters = new ConcurrentHashMap<>();

    public static int increment(String key) {
        return intCounters.computeIfAbsent(key, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public static int increment(String key, int amount) {
        return intCounters.computeIfAbsent(key, k -> new AtomicInteger(0)).addAndGet(amount);
    }

    public static int decrement(String key) {
        return intCounters.computeIfAbsent(key, k -> new AtomicInteger(0)).decrementAndGet();
    }

    public static int decrement(String key, int amount) {
        return intCounters.computeIfAbsent(key, k -> new AtomicInteger(0)).addAndGet(-amount);
    }

    public static long incrementLong(String key) {
        return longCounters.computeIfAbsent(key, k -> new AtomicLong(0)).incrementAndGet();
    }

    public static long incrementLong(String key, long amount) {
        return longCounters.computeIfAbsent(key, k -> new AtomicLong(0)).addAndGet(amount);
    }

    public static long decrementLong(String key) {
        return longCounters.computeIfAbsent(key, k -> new AtomicLong(0)).decrementAndGet();
    }

    public static long decrementLong(String key, long amount) {
        return longCounters.computeIfAbsent(key, k -> new AtomicLong(0)).addAndGet(-amount);
    }

    public static int get(String key) {
        AtomicInteger counter = intCounters.get(key);
        return counter != null ? counter.get() : 0;
    }

    public static long getLong(String key) {
        AtomicLong counter = longCounters.get(key);
        return counter != null ? counter.get() : 0L;
    }

    public static void set(String key, int value) {
        intCounters.computeIfAbsent(key, k -> new AtomicInteger(0)).set(value);
    }

    public static void setLong(String key, long value) {
        longCounters.computeIfAbsent(key, k -> new AtomicLong(0)).set(value);
    }

    public static void reset(String key) {
        AtomicInteger counter = intCounters.get(key);
        if (counter != null) {
            counter.set(0);
        }
    }

    public static void resetLong(String key) {
        AtomicLong counter = longCounters.get(key);
        if (counter != null) {
            counter.set(0L);
        }
    }

    public static void remove(String key) {
        intCounters.remove(key);
    }

    public static void removeLong(String key) {
        longCounters.remove(key);
    }

    public static void clear() {
        intCounters.clear();
        longCounters.clear();
    }
}