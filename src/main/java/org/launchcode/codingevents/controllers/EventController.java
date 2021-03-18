package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.launchcode.codingevents.models.Tag;
import org.launchcode.codingevents.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Chris Bay
 */

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    // We want to be able to pass in a query parameter called categoryId, so we add @RequestParam below. The parameter name ('categoryId') needs to match the query parameter name for this to work. Also, we don't want to make the categoryId to be required, so we mark it as false next to the @RequestParam. By making it false, we can just view all events (???).
    @GetMapping
    public String displayAllEvents(@RequestParam(required = false)Integer categoryId, Model model) {
        // Now we have to check if someone passed in a categoryId. If the user doesn't, the categoryId is null, which displays all events.
        if (categoryId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        } else { //Instead of doing this in the template, we can dictate which eventCategory we see through the conditional
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId); // findById returns something that has Optional<EventCategory>. The Optional object is a container object which may or may not contain a non-null value. This is essentially is a way for Java to return something when it doesn't find a result in the database. The Optional object is going to contain an EventCategory if there was one in the database with the given id, otherwise it's going to be empty.
            if (result.isEmpty()) { // result.isEmpty() means that someone passed a categoryId and when we asked the database for the object with that categoryId, it didn't find anything. Basically saying that's an invalid Id.
                model.addAttribute("title", "Invalid Category ID: " + categoryId);
            } else {
                EventCategory category = result.get();
                // Can't do result.get(categoryId) because 'get()' in 'java.util.Optional' cannot be applied to '(java.lang.Integer)'
                model.addAttribute("title", "Events in category: " + category.getName()); // category references the local variable set up in this method on line 42
                model.addAttribute("events", category.getEvents()); // This calls the getter for the events field in EventCategory class
            }
        }
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            return "events/create";
        }
        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }

        return "redirect:";
    }

    @GetMapping("detail")
    public String displayEventDetails(@RequestParam Integer eventId, Model model) {

        Optional<Event> result = eventRepository.findById(eventId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID: " + eventId);
        } else {
            Event event = result.get();
            model.addAttribute("title", event.getName() + " Details");
            model.addAttribute("event", event);
            model.addAttribute("tags", event.getTags());
        }

        return "events/detail";
    }

    // Add a form that allows users to add a tag to an event. In order to display this form, for a specific event, will have a drop down with all the available tags and allow you to add a tag to it.
    // responds to /events/add-tag?eventId=13 (13 is just an example)
    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model) { // Use model to pass things into the view
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        model.addAttribute("title", "Add Tag to: " + event.getName());
        model.addAttribute("tags", tagRepository.findAll()); // Passes in a parameter called tags into the template that will contain a list of all of them and we can use that to display a dropdown.
        // Need to pass in our event object because our form needs to know which event it's attaching a tag to
        EventTagDTO eventTag = new EventTagDTO();
        eventTag.setEvent(event);
        model.addAttribute("eventTag", eventTag); // We're going to use model binding with the data transfer object which will allow us to pass in a single object that can be validated (and that single object will be an instance of EventTagDTO).
        return "events/add-tag.html";
    }

    // handler
    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag,
                                    Errors errors,
                                    Model model) {

        if (!errors.hasErrors()) {
            Event event = eventTag.getEvent();
            Tag tag = eventTag.getTag();
            if (!event.getTags().contains(tag)) {
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:detail?eventId=" + event.getId();
        }

        return "redirect:add-tag";

    }

}