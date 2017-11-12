package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_TIME;

import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.ReadOnlyCalendarEvent;


//@@author yilun-zhu
/**
 * Adds a event to the google calendar.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the google calendar. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "EVENT NAME "
            + PREFIX_EVENT_START_DATE + "START DATE "
            + PREFIX_EVENT_START_TIME + "START TIME "
            + PREFIX_EVENT_END_DATE + "END DATE "
            + PREFIX_EVENT_END_TIME + "END TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Halloween Party "
            + PREFIX_EVENT_START_DATE + "2015-07-08 "
            + PREFIX_EVENT_START_TIME + "15:00 "
            + PREFIX_EVENT_END_DATE + "2015-07-08 "
            + PREFIX_EVENT_END_TIME + "18:00";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";


    private final CalendarEvent toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddEventCommand(ReadOnlyCalendarEvent event) {
        toAdd = new CalendarEvent(event);
    }

    @Override
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
