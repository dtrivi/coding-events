package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", EventData.getAll());
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        return "events/create";
    }


    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent, // @Valid says that not only should Spring Boot do model binding when this data comes in, but it should check any validation annotations that are on the fields for this class and make sure that they're satisfied.
                                         Errors errors, Model model) { // 'Errors' object will be populated by any validation errors that occur when Spring Boot does validation on the 'newEvent' object (corresponding to the annotation validations that we set up). The Model object is included because we want to send our user back to the Event Create form if there are any errors, and since we're setting a title for an event form, we need a Model object (like the code in processCreateEventForm).
        if(errors.hasErrors()) { // This checks to see if there are any validation errors. Now, if there are any errors in the Errors object, we will instead of creating the object, we'll just go back to the form.
            model.addAttribute("title", "Create Event");
            model.addAttribute("errorMsg", "Bad data!"); // If we have an error, we've added a message. Doing this inside the conditional. Then you go into the create.html template to add a paragraph that includes ${errorMsg}. The paragraph tag (in create.html) will be populated by the value of hte errorMsg parameter if it exists.
            return "events/create";
        }

        EventData.add(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                EventData.remove(id);
            }
        }

        return "redirect:";
    }

}