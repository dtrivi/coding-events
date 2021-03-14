package org.launchcode.codingevents.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass // Says the fields in this class should be pushed down into the tables for the objects that extend it (/use our ORM system). Thus, we want our private id field to be stored in the table that goes with Event or EventCategory. The id field is our primary key so we still want to keep it in our tables even though it's not specifically contained in our two model classes.
public abstract class AbstractEntity { // An abstract class is a class you can only use by extending. The purpose of this class isn't to create more AbstractEntity objects, it's for us to collect shared data and behavior that we can share across other class. We don't want to actually intantiate this class. So we make it abstract to enfore the rule that nobody gets to create an AbstractEntity object.

    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
//        Event event = (Event) o; // Our equals method is casting our input parameter to the class that we're in. Since we copied and pasted this from Event, we need to change Event to AbstractEntity. You can make large sweeping changes like that using the Refactor option in the right-click drop down menu when you hover over the variable.
        AbstractEntity entity = (AbstractEntity) o;
//        return id == event.id; // id has private access in AbstractEntity. That means it can only be referenced in this class. However, we our event object is of type Event. So Event can't actually see the value of the id field because it's private in the base class.
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
