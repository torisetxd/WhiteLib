package mc.toriset.eventbus;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class EventWaiter {
    private static class WaitRequest<T> {
        final Predicate<Event<?>> condition;
        final CompletableFuture<Event<T>> future;

        WaitRequest(Predicate<Event<?>> condition, CompletableFuture<Event<T>> future) {
            this.condition = condition;
            this.future = future;
        }
    }

    private final List<WaitRequest<?>> waitRequests = new CopyOnWriteArrayList<>();

    public <T> CompletableFuture<Event<T>> waitFor(String channel, EventType type, Class<T> payloadClass) {
        CompletableFuture<Event<T>> future = new CompletableFuture<>();
        WaitRequest<T> request = new WaitRequest<>(event ->
                event.getChannel().equals(channel) &&
                event.getType() == type &&
                payloadClass.isInstance(event.getPayload()),
            future
        );
        waitRequests.add(request);
        return future;
    }

    public <T> void notifyEvent(Event<T> event) {
        for (WaitRequest<?> request : waitRequests) {
            if (request.condition.test(event)) {
                completeRequest(request, event);
            }
        }
    }

    private <T> void completeRequest(WaitRequest<?> rawRequest, Event<T> event) {
        WaitRequest<T> request = (WaitRequest<T>) rawRequest;
        request.future.complete(event);
        waitRequests.remove(request);
    }
}
