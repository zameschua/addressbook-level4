package seedu.address.model.calendarevent;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author yilun-zhu
/**
 * Represents event start time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EventStart {


    public static final String START_TIME_CONSTRAINTS =
            "Time can only contain numbers, and should be at least 3 digits long 2015-05-29T17:00:00-07:00";
    public static final String TIME_VALIDATION_REGEX = "";
    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public EventStart(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        /*if (!isValidTime(trimmedTime)) {
            throw new IllegalValueException(START_TIME_CONSTRAINTS);
        }*/
        this.value = trimmedTime;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventStart // instanceof handles nulls
                && this.value.equals(((EventStart) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
