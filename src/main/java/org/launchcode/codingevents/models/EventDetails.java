package org.launchcode.codingevents.models;


import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class EventDetails extends AbstractEntity {


    @Size(max = 500, message = "Description too long.")
    private String description;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

    // Right now, an Event knows about an associated EventDetails object, but an EventDetails object doesn't know about the Event that owns it. To create an inverse relationship, so that your EventDetails object knows which Event they're associated with, you do the following:
//    @OneToOne(mappedBy = "eventDetails")
//    private Event event;

    public EventDetails(@Size(max = 500, message = "Description too long.") String description, @NotBlank(message = "Email is required.") @Email(message = "Invalid email. Try again.") String contactEmail) {
        this.description = description;
        this.contactEmail = contactEmail;
    }

    // Always put a no-arg constructor in an Entity class
    public EventDetails() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
