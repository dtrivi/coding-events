package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

// Business data of our application. What our application is about. Objects for which we want to be stored in the database. It's the 'things we want to keep track of'.
// So far when we run our application, our data has been stored temporarily in memory using the EventData data layer, which just keeps that data in a collection which allows them to be retrieve. But every time we start and stop our application, we lose that data because it's not stored persistently in an external database.

@Entity // Tells Java this is a persistent class/entity class. Means Event objects can be stored outside our application in a database.
public class Event {

    @Id // Says this is a primary key
    @GeneratedValue // Wants database to generate the values of our primary key. So we don't need to use the nextId field as well as the nextId++ functionality we had in our no-arg Event constructor. We also delete the line that calls for the no-arg constructor in our main Event constructor.
    private int id; // Already functioning similarly to a primary key (our second step in setting up this Entity class). So we'll adjust it instead of remove it.

    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
    private String name;

    @Size(max = 500, message = "Description too long.")
    private String description;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

    private EventType type;

    public Event(String name, String description, String contactEmail, EventType type) {
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
        this.type = type;
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
