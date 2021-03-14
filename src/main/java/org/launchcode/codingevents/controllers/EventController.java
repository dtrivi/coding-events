package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Chris Bay
 */

// Refactor controller code so it can use our EventRepository in order to insert and retrieve objects from our database (as opposed to relying on the static EventData class to temporarily keep our event objects in memory).

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired // This annotation specifies that Spring Boot should auto populate the following field. We're not creating a constructor or setter to assign a value to the eventRepository field because we want Spring to manage this field for us. This is a feature of Spring called "dependency injection".
    private EventRepository eventRepository; // Autowired asks Spring Boot for an EventRepository object. Spring Boot looks around and if it finds such an object, it'll put it into this field and it'll use it. Our @Repository annotation in EventRepository is the flag Spring Boot is looking for - this is a class Spring Boot should manage for us. Prompts it to create an instance of this class and leave it laying around in case anyone/thing needs one. So create EventRepository instances and then inject them wherever our code ask for them (@Autowired is one way to ask for them).

    // findAll, save, findById - methods that are part of the EventRepository interface (because it extends CrudRepository).
    // Below, we replaced any methods using EventData with something that uses our EventRepository class.
    // Recap - replaced all uses of our EventData class that was storing event objects in an in-memory collection with a repository which is an interface that allows us to interact with our database. Thus, deleted the EventData class.

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", eventRepository.findAll()); // Previously used EventData.getAll() passed a collection of all event objects that our application knows about into the model.
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("types", EventType.values());
        return "events/create";
    }


    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            return "events/create";
        }

        eventRepository.save(newEvent);  // Previously used EventData.add(newEvent) to create and save a new event object from our form, when someone submits our form.
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll()); // Similar to above, this previously used EventData.getAll() passed a collection of all event objects that our application knows about into the model.
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id); // Previously, EventData.remove(id) removed each event that had the corresponding id in the interation.
            } // Note that .delete() and .deleteById() are not interchangeable
        }

        return "redirect:";
    }

}