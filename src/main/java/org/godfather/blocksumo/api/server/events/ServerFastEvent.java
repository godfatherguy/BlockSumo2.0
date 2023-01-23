package org.godfather.blocksumo.api.server.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.function.Consumer;

public class ServerFastEvent<T extends Event> implements FastEvent<T> {

    private final Class<T> eventClass;
    private final Consumer<T> consumer;
    private final EventPriority priority;

    public ServerFastEvent(Class<T> eventClass, Consumer<T> consumer, EventPriority priority) {
        this.eventClass = eventClass;
        this.consumer = consumer;
        this.priority = priority;
    }

    @Override
    public Class<T> getEventClass() {
        return eventClass;
    }

    @Override
    public EventPriority priority() {
        return priority;
    }

    @Override
    public void accept(T t) {
        consumer.accept(t);
    }

    public static <T extends Event> FastEventBuilder<T> builder(Class<T> eventClass) {
        return new FastEventBuilder<>(eventClass);
    }

    public static class FastEventBuilder<T extends Event> {

        private final Class<T> eventClass;
        private Consumer<T> consumer;
        private EventPriority priority = EventPriority.LOW;

        protected FastEventBuilder(Class<T> eventClass) {
            this.eventClass = eventClass;
        }

        public FastEventBuilder<T> consumer(Consumer<T> consumer) {
            this.consumer = consumer;
            return this;
        }

        public FastEventBuilder<T> priority(EventPriority priority) {
            this.priority = priority;
            return this;
        }

        public ServerFastEvent<T> build() {
            return new ServerFastEvent<>(eventClass, consumer, priority);
        }
    }
}
