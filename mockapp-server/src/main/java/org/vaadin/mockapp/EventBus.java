package org.vaadin.mockapp;


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
     * @param event
     */
    public void publish(Object event) {
        for (Subscriber subscriber : new LinkedList<Subscriber>(subscribers)) {
            subscriber.onEventReceived(event);
        }
    }

    /**
     * @param subscriber
     */
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * @param subscriber
     */
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     *
     */
    public interface Subscriber extends Serializable {
        void onEventReceived(Object event);
    }

}
