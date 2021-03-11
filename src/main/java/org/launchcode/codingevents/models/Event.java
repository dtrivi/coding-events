package org.launchcode.codingevents.models;

import java.util.Objects;

public class Event {

    private int id; // This variable is the start of a unique integer identifier for our class. Each new Event object will have its own unique integer, which will be a way to id each object and tell them apart.
    private static int nextId = 1; // Static counter. Static means this field belongs to class rather than any one instance of the object. Thus, every single Event object will share the same static 'nextId' field. By initializing this to 1, this is going to be the id of the next object created, and then we can update that counter to 2, so the second object would have the id '2'.

    private String name;
    private String description;

    public Event(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = nextId; // Initialize id
        nextId++; // Then we increment the nextId field to make sure it's unique, which should initialize the id correctly.
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
