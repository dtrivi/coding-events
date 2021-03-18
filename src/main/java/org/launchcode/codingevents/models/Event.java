package org.launchcode.codingevents.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


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

    // Since it's many-to-many, we need to make this field a collection (List)
    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();

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

    public List<Tag> getTags() {
        return tags;
    }

    // This is a way for someone to add tags to without accessing or touching our collection directly.
    public void addTag (Tag tag) {
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return name;
    }

}
