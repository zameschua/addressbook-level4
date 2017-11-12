package seedu.address.external.addevent;

import java.io.IOException;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.events.external.AddEventRequestEvent;
import seedu.address.external.ExternalCall;
import seedu.address.model.calendarevent.CalendarEvent;

//@@author yilun-zhu
/**
 * Follows Singleton and Facade design pattern,
 * for other parts of the app to interact with the google calendar API service
 */
public class AddEventManager extends ExternalCall {

    private static AddEventManager instance = null;

    protected AddEventManager() {
        registerAsAnEventHandler(this);
    }

    /**
     * Creates an instance of the CalendarApi and registers it as an event handler
     * @return The Singleton instance of the CalendarApi
     */
    public static AddEventManager init() {
        if (instance == null) {
            instance = new AddEventManager();
        }
        return instance;
    }

    @Subscribe
    public static void handleAddEventRequestEvent(AddEventRequestEvent addEventRequestEvent) throws IOException {
        CalendarEvent eventSent = addEventRequestEvent.getCalendarEvent();
        CalendarApi.createEvent(eventSent);
    }


}
