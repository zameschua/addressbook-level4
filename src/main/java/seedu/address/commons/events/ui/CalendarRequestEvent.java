package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for Calendar
 */

public class CalendarRequestEvent extends BaseEvent {

    public CalendarRequestEvent(){
    ;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
