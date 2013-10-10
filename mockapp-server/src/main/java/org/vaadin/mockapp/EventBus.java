package org.vaadin.mockapp;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author petter@vaadin.com
 */
public class EventBus implements Serializable {

    private final Set<Subscriber> subscribers = new HashSet<Subscriber>();

    /**
     *
     * @param event
     */
    public void publish(@NotNull Object event) {
        for (Subscriber subscriber : new LinkedList<Subscriber>(subscribers)) {
            subscriber.onEventReceived(event);
        }
    }

    /**
     *
     * @param subscriber
     */
    public void subscribe(@NotNull Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    /**
     *
     * @param subscriber
     */
    public void unsubscribe(@NotNull Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     *
     */
    public interface Subscriber extends Serializable {
        void onEventReceived(@NotNull Object event);
    }

}
