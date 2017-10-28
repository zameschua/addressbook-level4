package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END;


import seedu.address.model.calendarEvent.CalendarEvent;
import seedu.address.model.calendarEvent.ReadOnlyCalendarEvent;


/**
 * Adds a event to the google calendar.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the google calendar. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "EVENT NAME "
            + PREFIX_EVENT_START + "START DATE AND TIME "
            + PREFIX_EVENT_END + "END DATE AND TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Halloween Party "
            + PREFIX_EVENT_START + "START DATE AND TIME "
            + PREFIX_EVENT_END + "END DATE AND TIME ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";


    private final CalendarEvent toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddEventCommand(ReadOnlyCalendarEvent event) {
        toAdd = new CalendarEvent(event);
    }


    public CommandResult execute() {
        requireNonNull(model);
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
