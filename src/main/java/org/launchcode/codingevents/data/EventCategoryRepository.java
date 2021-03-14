package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.EventCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Chris Bay
 */

// Added through coding-events-demo repo since hadn't completed CHAPTER 17 Exercises or Studio

@Repository
public interface EventCategoryRepository extends CrudRepository<EventCategory, Integer> {

}