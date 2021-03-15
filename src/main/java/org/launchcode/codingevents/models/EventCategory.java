package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Bay
 */

// We now want to set up a relationship where our EventCategory objects to know about the Events that are in those categories. We had a ManyToOne relationship with Event to EventCategory. Now we'll do the inverse.

@Entity
public class EventCategory extends AbstractEntity {

    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    // Field that stores all of the Events in a given category. 'Final' says that the list can't change. Doesn't mean the contents of the list can't change, just the list itself.
    @OneToMany(mappedBy = "eventCategory") // We need to tell it how to determine which events are in a given category. So we use 'mappedBy', who's value has to be the name of the field in the class we're storing here that creates this relationship. The relationship is still owned by the Event. Event is going to have a foreign key or reference to an EventCategory, but in this class we need to tell Hibernate how to determine which events are in a given category.
    private final List<Event> events = new ArrayList<>();

    // Don't need to add the events ArrayList to the constructor because we've created the list in the fields declaration. (???)
    public EventCategory(@Size(min = 3, message = "Name must be at least 3 characters long") String name) {
        this.name = name;
    }

    public EventCategory() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() { // No setter because the ArrayList 'events' is set to final
        return events;
    }

    @Override
    public String toString() {
        return name;
    }

}