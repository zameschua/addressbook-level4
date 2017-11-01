package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ReneeSeet

/**
 * Indicates a request for Logging
 */

public class LoginRequestEvent extends BaseEvent {

    public LoginRequestEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
