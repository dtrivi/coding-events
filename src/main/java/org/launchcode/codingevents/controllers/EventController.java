package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // This method is/was the handler that gets called when someone submits the form to create a new event. The way it was set up, we had to refer to every single piece of data for an event that you wanted to create, specifically what we had in the parameters for this method eventName and eventDescription.
    // Model binding allows us to streamline this process so we don't have to have such a long list of parameters. It also does validation on our data.
    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute Event newEvent) { // We replaced our list of parameters with a single parameter enabled by the @ModelAttribute annotation. Now when Spring tries to call this method (a POST request is made to the /create route), it will look  for fields that match up with fields of the Event class. It'll look for parameters in the request that have the same class identifiers.
        EventData.add(newEvent); // With model binding, this method will now create an Event object and populate those fields with the values. Previously we called the Event constructor with the values of the parameters passed in. Here, we previously were passing in the eventName and eventDescription. Now we're allowing Spring to do this for us. Now we just add newEvent.
        return "redirect:"; // Last thing we need to do to tie everything together is go back to our form and make sure the names of the form inputs match up with the names of the fields in this class.
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