package seedu.address.external.sms.exceptions;

import seedu.address.commons.events.external.SendSmsRequestEvent;

/**
 * Represents an error which occurs during execution of a {@link SendSmsRequestEvent}.
 */
public class SmsServiceException extends Exception {
    public SmsServiceException(String message) {
        super("Error in sms service: " + message);
    }
}
