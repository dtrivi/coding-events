package org.launchcode.codingevents.controllers;

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

    // Make a list accessible to multiple methods, so we create our list at the class level. Otherwise our only list we were working with is inside the displayAllEvents handler, meaning it's only a local variable.
    private static List<String> events = new ArrayList<>();

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("events", events); // Recall a static field is one that belongs to the class rather than instances of the class. Means it's shared by every instance of our eventController object.
        return "events/index";
    }

    // lives at /events/create
    @GetMapping("create")
    public String renderCreateEventForm() {
        return "events/create";
    }

    // renderCreateEventForm and createEvent live at the same path, which is OK if they handle different types of requests (GET v. POST)

    // lives at /events/create
    @PostMapping("create")
    public String createEvent(@RequestParam String eventName) { // We can pull data out of an incoming request using @RequestParam annotation. Name of parameter 'eventName' is the same as our label we used to name our variable in create.html.
        events.add(eventName);
        return "redirect:"; // Says return a redirect response that instructs the browser to go to a different page. Typically looks like redirect:/path but in this case we're just going to /events which is set up for the whole class already.
    }

}