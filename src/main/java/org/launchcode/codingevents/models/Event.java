package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
public class Event extends AbstractEntity {

    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
    private String name;

    @Size(max = 500, message = "Description too long.")
    private String description;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

//    private EventType type; // Before we had the ability to store objects in a database, we were keeping track of our event categories/types using our EventType Enum. But that only allowed us to use one of the four event types we set up in the EventType enum.

    @ManyToOne // Tells Hibernate that when we're creating this object that we should be able to relate them in the database where we'll have one EventCategory per Event (aka can have many events in the same category).
    @NotNull(message = "Category is required")
    private EventCategory eventCategory; // This replaces the variable type (of EventType object), meaning anywhere we were referencing this variable needs to change to eventCategory. That means updating the constructor and getter and setters for this new field.

    public Event(String name, String description, String contactEmail, EventCategory eventCategory) {
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
        this.eventCategory = eventCategory;
    }

    public Event() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    @Override
    public String toString() {
        return name;
    }

}
