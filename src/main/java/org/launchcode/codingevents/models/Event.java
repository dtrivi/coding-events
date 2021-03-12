package org.launchcode.codingevents.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Event {

    private int id;
    private static int nextId = 1;

    // Adding validation annotations to restrict the range of the values that can be used here.
    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
    private String name;

    @Size(max = 500, message = "Description too long.")
    private String description;

    @NotBlank(message = "Email is required.") // Have to add this because because a blank email will still pass the @Email validation.
    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

    private EventType type; // Create a property to use our enum with our events, then add it to the constructor, then add a getter and setter.

    public Event(String name, String description, String contactEmail, EventType type) {
        this(); // Calls a different constructor within the same class. We had to move nextId to the no-arg constructor because SpringBoot will always call that first(?). So because we utilize the no-arg constructor in the displayCreateEventForm handler, we weren't actually getting to the nextId field in our Event class. Thus, when we added a new event, it wouldn't build off previous events we added. Each event got assigned an event id of 0.
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
        this.type = type;
    }

    public Event() { // Had to create a "no-arg" constructor (no argument constructor) after passing in an empty new Event object into displayEventForm handler. This constructor doesn't initialize any fields.
        this.id = nextId;
        nextId++;
    }

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

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public int getId() { // Don't want to create a setter because we don't want someone to set our id's for us in this case. Also, don't want to expose nextId. It will remain private and only to be used INSIDE the class. Just used for the id initialization.
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
