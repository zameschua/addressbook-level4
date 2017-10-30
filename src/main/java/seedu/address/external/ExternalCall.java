package seedu.address.external;

import seedu.address.commons.core.EventsCenter;

/**
 * Represents External Calls for external API
 * Allow external class to handle events
 */

public abstract class ExternalCall {
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }
}
