package seedu.address.logic.commands;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SmsCommandRequestEvent;
import seedu.address.model.person.MassEmailPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Brings up the smsPanel
 * Then finds and lists all persons in address book whose tags match those in the command, for group SMSsing
 * Keyword matching is case-sensitive.
 */

public class SmsCommand extends Command {

    public static final String COMMAND_WORD = "sms";
    public static final String MESSAGE_SUCCESS = "Listed all required SMSses";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": To SMS all persons with tag\n"
            + COMMAND_WORD + " all : To send an SMS to everyone in CYNC\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]... \n"
            + "Example: " + COMMAND_WORD + " Sec 2 Sec 3\n";

    // TODO: Change to something like tagMatchingPredicate
    private final MassEmailPredicate predicate;

    public SmsCommand(MassEmailPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        ObservableList<ReadOnlyPerson> allPerson = model.getFilteredPersonList();
        ArrayList<String> phoneNumbers = new ArrayList<String>();
        for (ReadOnlyPerson person : allPerson) {
            phoneNumbers.add(person.getPhone().toString());
        }
        EventsCenter.getInstance().post(new SmsCommandRequestEvent(phoneNumbers));
        return new CommandResult(super.getMessageForSms(allPerson.size(), phoneNumbers));
    }

    // TODO: Change to something like tag matching predicate
    public MassEmailPredicate getPredicate() {
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MassEmailCommand // instanceof handles nulls
                && this.predicate.equals(((SmsCommand) other).predicate)); // state check
    }
}
