package mc.toriset.mongo;

import java.util.function.Consumer;

public class SubscriberAdapter<T> implements org.reactivestreams.Subscriber<T> {
        private final Consumer<T> onNext;
        private final Consumer<Throwable> onError;
        private final Runnable onComplete;
        private org.reactivestreams.Subscription subscription;

        public SubscriberAdapter(Consumer<T> onNext, Consumer<Throwable> onError, Runnable onComplete) {
            this.onNext = onNext;
            this.onError = onError;
            this.onComplete = onComplete;
        }

        public SubscriberAdapter(Consumer<T> onNext, Consumer<Throwable> onError, Runnable onComplete, Consumer<Throwable> errorHandler) {
            this.onNext = onNext;
            this.onError = error -> {
                onError.accept(error);
                if (errorHandler != null) {
                    errorHandler.accept(error);
                }
            };
            this.onComplete = onComplete;
        }

        @Override
        public void onSubscribe(org.reactivestreams.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(T item) {
            onNext.accept(item);
        }

        @Override
        public void onError(Throwable throwable) {
            onError.accept(throwable);
        }

        @Override
        public void onComplete() {
            onComplete.run();
        }
    }