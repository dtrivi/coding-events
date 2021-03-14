package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

/**
 * Created by Chris Bay
 */

// Added through coding-events-demo repo since hadn't completed CHAPTER 17 Exercises or Studio

@Entity
public class EventCategory extends AbstractEntity { // Added 'extends' when we moved our id-related coding into the AbstractEntity class. Now EventCategory will inherit that code.

//    @Id // Removed this section because there's essentially a duplicate of this info in Event and EventCategory and we want to make our code more DRY. Thus, we're creating AbstractEntity with this id code so that Event and EventCategory can inherit this information from AbstractEntity.
//    @GeneratedValue
//    private int id;

    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

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

//    public int getId() { // Removed this section because there's essentially a duplicate of this info in Event and EventCategory and we want to make our code more DRY. Thus, we're creating AbstractEntity with this id code so that Event and EventCategory can inherit this information from AbstractEntity.
//        return id;
//    }

    @Override
    public String toString() {
        return name;
    }

//    @Override // Removed this section because there's essentially a duplicate of this info in Event and EventCategory and we want to make our code more DRY. Thus, we're creating AbstractEntity with this id code so that Event and EventCategory can inherit this information from AbstractEntity.
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Event event = (Event) o;
//        return id == event.id;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}