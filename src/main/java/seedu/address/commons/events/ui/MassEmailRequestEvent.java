package seedu.address.commons.events.ui;

import java.util.ArrayList;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for MassEmailRequest
 */

public class MassEmailRequestEvent extends BaseEvent {

    private final ArrayList<String> emails;

    public MassEmailRequestEvent(ArrayList<String> emails) {
        this.emails = emails;
    }

    public ArrayList<String> getEmailList() {
        return emails;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
