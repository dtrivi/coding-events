package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// We're encapsulating the behavior of storing Event objects so that nobody else needs to know how they're stored. This decouples our application from having to know how things are stored internally.
public class EventData { // Class is responsible for storing Event objects. Will just contain static methods and properties. Use it to have a single point of operation for how our Event objects are stored.

    // need a place to put events. that will be our main property of the class. It's final so that once this property or field is initiated, it can't change. Doesn't mean data inside the Map can't change, just means the Map can't change. So we couldn't put a totally different collection of events in here.
    private static final Map<Integer, Event> events = new HashMap<>(); // This is a place to keep our events. It's static, so there will only be one of these. Declared it to be of type Map with Integer/Event pairs. Want to code to interface types whenever possible (so Map over HashMap, who this gets initialized to a HashMap anyway). The Integers are going to be our ids which will make it easier to do some of the behaviors we'll implement below (which will be easy because we'll be able to retrieve an Event object by just its id.

    // get all events
    public static Collection<Event> getAll() { // Collection is an interface. This extends the Iterable<E> interface, which has behaviors related to iterating or looping over a collection. Which is what we need here.
        return events.values(); // don't need to return the keys. Just want to return the values since our values are events
    }

    // get a single event
    public static Event getById(int id) { // retrieves the item with the specific key from our map that matches the id that is passed in
        return events.get(id);
    }

    // add an event
    public static void add(Event event) {
        events.put(event.getId(), event); // event.getId() is the key in our key/value pairs for our Map, while the event is the value. The key is the ID, the value is the event itself.
    }

    // remove an event
    public static void remove(int id) {
        events.remove(id); // extracts a single event from the events Map by finding its id.
    }

}
