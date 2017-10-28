package seedu.address.external;

import seedu.address.commons.core.EventsCenter;

public abstract class externalClass {
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }
}
