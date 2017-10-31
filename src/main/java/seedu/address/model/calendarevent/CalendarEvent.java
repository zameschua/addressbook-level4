package seedu.address.model.calendarevent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a calendar event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class CalendarEvent implements ReadOnlyCalendarEvent {

    private ObjectProperty<EventName> name;
    private ObjectProperty<EventStart> start;
    private ObjectProperty<EventEnd> end;


    /**
     * Every field must be present and not null.
     */
    public CalendarEvent(EventName name, EventStart start, EventEnd end) {
        requireAllNonNull(name, start, end);
        this.name = new SimpleObjectProperty<>(name);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public CalendarEvent(ReadOnlyCalendarEvent source) {
        this(source.getEventName(), source.getStartTime(), source.getEndTime());
    }

    public void setName(EventName name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<EventName> nameProperty() {
        return name;
    }

    @Override
    public EventName getEventName() {
        return name.get();
    }

    public void setStart(EventStart time) {
        this.start.set(requireNonNull(time));
    }

    public void setEnd(EventEnd time) {
        this.end.set(requireNonNull(time));
    }

    @Override
    public ObjectProperty<EventEnd> endProperty() {
        return this.end;
    }

    @Override
    public ObjectProperty<EventStart> startProperty() {
        return this.start;
    }

    @Override
    public EventStart getStartTime() {
        return this.start.get();
    }

    @Override
    public EventEnd getEndTime() {
        return this.end.get();
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyCalendarEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyCalendarEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, start, end);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
