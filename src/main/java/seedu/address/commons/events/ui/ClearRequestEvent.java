package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ReneeSeet
/**
 * Indicates a request for Clear of CYNC
 */

public class ClearRequestEvent extends BaseEvent {

    public ClearRequestEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
