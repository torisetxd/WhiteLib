package mc.toriset.eventbus;

public interface EventListener<T> {
    void onEvent(Event<T> event);
}
