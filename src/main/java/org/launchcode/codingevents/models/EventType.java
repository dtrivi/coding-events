package org.launchcode.codingevents.models;

// We want to define different types of events
public enum EventType {

    // These CONSTANTS are our field names. We don't want to display them this way to the user, so we create displayName below.
    // In order to associate a specific displayName with our enum values, we call the constructor here when we define the values.
    CONFERENCE("Conference"),
    MEETUP("Meetup"),
    WORKSHOP("Workshop"),
    SOCIAL("Social");

    private final String displayName;

    EventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
