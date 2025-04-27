package mc.toriset.eventbus;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.function.Consumer;

public class EventBus {
    private static final Map<String, PluginInfo> plugins = new ConcurrentHashMap<>();
    private static final List<EventListener<?>> globalListeners = new CopyOnWriteArrayList<>();
    private static final Map<String, List<EventListener<?>>> channelListeners = new ConcurrentHashMap<>();
    private static final EventWaiter eventWaiter = new EventWaiter();

    public static void registerPlugin(String name) {
        plugins.put(name, new PluginInfo(name));
    }

    public static void unregisterPlugin(String name) {
        plugins.remove(name);
    }

    public static Set<String> getRegisteredPlugins() {
        return Collections.unmodifiableSet(plugins.keySet());
    }

    public static <T> void send(String sender, String channel, EventType type, T payload) {
        Event<T> event = new Event<>(channel, type, payload, sender);

        // Dispatch to listeners
        List<EventListener<?>> listeners = channelListeners.get(channel);
        if (listeners != null) {
            for (EventListener<?> listener : listeners) {
                dispatch(listener, event);
            }
        }

        for (EventListener<?> listener : globalListeners) {
            dispatch(listener, event);
        }

        // Notify waiters
        eventWaiter.notifyEvent(event);
    }

    private static <T> void dispatch(EventListener<?> rawListener, Event<T> event) {
        EventListener<T> listener = (EventListener<T>) rawListener;
        CompletableFuture.runAsync(() -> listener.onEvent(event));
    }

    public static <T> void listenAll(EventListener<T> listener) {
        globalListeners.add(listener);
    }

    public static <T> void listenChannel(String channel, EventListener<T> listener) {
        channelListeners.computeIfAbsent(channel, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public static <T> CompletableFuture<Event<T>> waitForEvent(String channel, EventType type, Class<T> payloadClass) {
        return eventWaiter.waitFor(channel, type, payloadClass);
    }
}
