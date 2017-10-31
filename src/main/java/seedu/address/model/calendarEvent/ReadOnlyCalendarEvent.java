package seedu.address.model.calendarEvent;

import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyCalendarEvent {

    ObjectProperty<EventName> nameProperty();
    EventName getEventName();
    ObjectProperty<EventStart> startProperty();
    EventStart getStartTime();
    ObjectProperty<EventEnd> endProperty();
    EventEnd getEndTime();


    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyCalendarEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getEventName().equals(this.getEventName()) // state checks here onwards
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Start: ")
                .append(getStartTime())
                .append(" End: ")
                .append(getEndTime());
        return builder.toString();
    }

}
