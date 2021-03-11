package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", EventData.getAll()); // previously had our events list which was being housed in the controller. Now it references our EventData class and getAll() method. That method is a static method, so we don't initialize the class. We just call the static method off the class itself.
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@RequestParam String eventName, @RequestParam String eventDescription) {
        EventData.add(new Event(eventName, eventDescription)); // We were previously adding a new Event object to our static list in the controller. With that gone, now we just reference the EventData data layer static class.
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) { // This is a different model from the model we created in the Event class
        model.addAttribute("title", "Delete Events"); // This is how we put a title on the page
        model.addAttribute("events", EventData.getAll()); // In order to render a form that deletes events, we need to know what the events are. So we're passing in a collection of events. We call the attribute "events". Then we pass in EventData.getAll() which gives us a collection we can loop over in the template. Ultimately, we're trying to create a list of our events with checkboxes that allow the user to select which events to delete.
        return "events/delete";
    }

    @PostMapping("delete") // Will actually communicate that we're deleting events
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) { // The ids are ints, and since in our delete form we gave them all the same name, they're going to be submitted in a collection of ids. That's why we're passing in int[] (array). It's important that this parameter name is the same as the name attribute of the form element that we gave it in the delete form template. We had to introduce a new parameter for @RequestParam, which is required=false. Otherwise, required is going to be 'true' and Spring will not map our request to the handler method because it doesn't, you know, if it's submitted without anything checked because this won't exist in a request. This now allows method to be called without any event ids which is what we want in the case someone hits the delete button without selecting any of the check boxes.

        // After adding, required=false, now it's possible for this this object to be null. We need to guard against referring to that object in the case that it's null, but wrapping our for-loop in a null check.
        if (eventIds != null) {
            for (int id : eventIds) { // We want to loop over this collection of ids
                EventData.remove(id); // We want to remove the event from the collection. We pass the id for the specific iteration.
            }
        }

        return "redirect:"; // Like the create event, we want to redirect back to the event listing. Normally there's a relative path to the controller we're in after the colon, but we leave it empty because we want to go to the index endpoint of the controller we're in.
    }

}