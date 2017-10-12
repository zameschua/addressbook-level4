package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * List all emails in address book.
 */

public class MassEmailCommand extends Command {

    public static final String COMMAND_WORD = "mass";
    public static final String MESSAGE_SUCCESS = "Listed all emails";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all emails ";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> allPerson = model.getFilteredPersonList();
        ArrayList<String> emails = new ArrayList<String>();
        for (ReadOnlyPerson person : allPerson) {
            emails.add(person.getEmail().toString());
        }
        return new CommandResult(getMessageForMassEmail(allPerson.size(), emails));
    }
}
