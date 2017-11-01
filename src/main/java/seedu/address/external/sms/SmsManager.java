package seedu.address.external.sms;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.external.SendSmsRequestEvent;

/**
 * Follows Singleton and Facade design pattern,
 * for other parts of the app to interface with the Twilio SMS service
 */
public class SmsManager {

    private static SmsManager instance = null;

    protected SmsManager() {
        registerAsAnEventHandler(this);
    }

    public static SmsManager getInstance() {
        if (instance == null) {
            instance = new SmsManager();
        }
        return instance;
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    @Subscribe
    public void handleSendSmsRequestEvent(SendSmsRequestEvent event) {
        String[] recipients = event.getRecipients();
        String message = event.getMessage();

        for (String phoneNumber : recipients) {
            TwilioApiHelper.sendSms(message, phoneNumber);
        }
    }
}
