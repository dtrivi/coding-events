package org.launchcode.codingevents.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
public class Event extends AbstractEntity {

    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
    private String name;

    // Relate EventDetails class to this Event class
    // Cascade allows you to specify in the relationship of two objects, how ORM operations are applied to sub-objects. So now, whenever an Event is saved, we want to also save the eventDetails object data.
    @OneToOne(cascade = CascadeType.ALL) // Creates a foreign key column on our Event table that references the EventDetails table
    @Valid // Need this because when we want to validate our new Event objects in the controller, when we add @Valid to the Event, it won't validate objects contained within the Event. It'll only validate the top level fields, like name and category.
    @NotNull // Need this to go down into the fields to actually check their values
    private EventDetails eventDetails;

    @ManyToOne
    @NotNull(message = "Category is required")
    private EventCategory eventCategory;

    public Event(String name, EventCategory eventCategory) {
        this.name = name;
        this.eventCategory = eventCategory;
    }

    public Event() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    @Override
    public String toString() {
        return name;
    }

}
