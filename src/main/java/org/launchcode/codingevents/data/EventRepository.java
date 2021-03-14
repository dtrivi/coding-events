package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Previously used EventData, which was very specific to how our application was written. It stored events in a Hashmap and provided methods to retrieve or add events to that Hashmap. We used it because, besides just that it was a best practice to extract data management out of our controller, but also mimics the way the data layer we use going forward will work as well.

// Note that you can't actually create an object from an interface class (like the one below). You can't call a constructor on EventRepository. Our code doesn't have a concrete class that implements EventRepository (something like public class MyRepository implements EventRepository {} ). Fortunately, this is something that Spring Boot does for us using the CrudRepository interface. Since every CrudRepository is so boilerplate, Spring can actually create these classes for us. Spring will see the EventRepository interface, and it will create a class that implements EventRepository in memory on the fly. Then it will make sure that class is Autowired into our controller's eventRepository field.

@Repository // Flags to Spring Boot that this should be considered a class that is going to store objects in the database (a repository class).
public interface EventRepository extends CrudRepository<Event, Integer> { // CrudRespository is a parameterizd class - it takes class parameters like a list or hashmap would take. When we create a CrudRepository, we need to tell that interface what types of things we're storing. First thing in this case is the Event object (type of thing we're storing), and the second thing is the data type of the primary key for that class. CrudRepository is our base class that we're extending to create our EventRepository. CrudRepository contains all the basic behaviors we want, ability to put objects in a database, retrieve them, delete them. It allows us to interact with objects in a database. Also note, we typed in caps 'I' for Integer (class version) because even though our primary key is 'int', Java will conduct 'autoboxing' where it automatically translates between primitive and object types for integer.
}
