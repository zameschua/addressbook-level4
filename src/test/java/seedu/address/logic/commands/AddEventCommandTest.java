package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.ReadOnlyCalendarEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CalendarEventBuilder;

//@@author yilun-zhu

public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullCalendarEventThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void executeEventAcceptedByModelAddSuccessful() throws Exception {
        ModelStubAcceptingCalendarEventAdded modelStub = new ModelStubAcceptingCalendarEventAdded();
        CalendarEvent validEvent = new CalendarEventBuilder().build();

        CommandResult commandResult = getAddEventCommandForCalendarEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }


    @Test
    public void equals() {
        CalendarEvent halloween = new CalendarEventBuilder().withName("Halloween").build();
        CalendarEvent danceClass = new CalendarEventBuilder().withName("Dance class").build();
        AddEventCommand addHalloweenCommand = new AddEventCommand(halloween);
        AddEventCommand addDanceClassCommand = new AddEventCommand(danceClass);

        // same object -> returns true
        assertTrue(addHalloweenCommand.equals(addHalloweenCommand));

        // same values -> returns true
        AddEventCommand addHalloweenCommandCopy = new AddEventCommand(halloween);
        assertTrue(addHalloweenCommand.equals(addHalloweenCommandCopy));

        // different types -> returns false
        assertFalse(addHalloweenCommand.equals(1));

        // different event -> returns false
        assertFalse(addHalloweenCommand.equals(addDanceClassCommand));
    }

    /**
     * Generates a new AddEventCommand with the details of the given event.
     */
    private AddEventCommand getAddEventCommandForCalendarEvent(CalendarEvent event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ReadOnlyCalendarEvent event) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            fail("This method should not be called.");
        }
    }


    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingCalendarEventAdded extends ModelStub {
        private final ArrayList<CalendarEvent> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(ReadOnlyCalendarEvent event) {
            eventsAdded.add(new CalendarEvent(event));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
