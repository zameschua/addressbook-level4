package seedu.address.external.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * Helper class to handle the sending of SMS using Twilio API
 */
public class TwilioApiHelper {
    // I shouldn't be storing the Account SIDs here
    public static final String ACCOUNT_SID = "AC8e7d80947bd2e877013c66d99b0faa06";
    public static final String AUTH_TOKEN = "46abad64b64c0b29c468434ff69e36ca";

    private static final String MESSAGE_SMS_SUCCESS = "SMS Successfully sent";
    private static final String COUNTRY_CODE_SINGAPORE = "+65";

    /**
     * Helper method that calls the Twilio REST API for sending SMS
     * @param to the target phone number. Has to contain the country code like +65
     * @param message The message to send as the content of the SMS
     */
    public static void sendSms(String message, String to) {
        to = checkPhoneNumberFormat(to);

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // TODO: Secure the from phone number properly
        Message.creator(new PhoneNumber(to), new PhoneNumber("+1 954-320-0045"), message).create();
        EventsCenter.getInstance().post(new NewResultAvailableEvent(MESSAGE_SMS_SUCCESS));
    }

    /**
     * Helper method that prepends the country code to a phone number if
     * it doesn't have the country code
     * @param phoneNumber The phone number to check
     */
    public static String checkPhoneNumberFormat(String phoneNumber) {
        if (!phoneNumber.startsWith(COUNTRY_CODE_SINGAPORE)) {
            return COUNTRY_CODE_SINGAPORE + phoneNumber;
        } else {
            return phoneNumber;
        }
    }
}
