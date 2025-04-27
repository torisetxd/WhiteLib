package mc.toriset.eventbus;

public class Event<T> {
    private final String channel;
    private final EventType type;
    private final T payload;
    private final String sender;

    public Event(String channel, EventType type, T payload, String sender) {
        this.channel = channel;
        this.type = type;
        this.payload = payload;
        this.sender = sender;
    }

    public String getChannel() { return channel; }
    public EventType getType() { return type; }
    public T getPayload() { return payload; }
    public String getSender() { return sender; }
}
