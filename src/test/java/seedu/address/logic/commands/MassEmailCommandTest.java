package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MassEmailPredicate;

//@@author ReneeSeet

/**
 * Contains integration tests (interaction with the Model) and unit tests for MassEmailCommand.
 */

public class MassEmailCommandTest {
    private Model model;

    @Test
    public void execute_massEmail_success() throws Exception {

        MassEmailCommand command = prepareCommand("all");
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        int expected = expectedModel.getAddressBook().getPersonList().size();

        model.updateFilteredPersonList(command.getPredicate());
        int actual = model.getFilteredPersonList().size();
        System.out.print("ex:" + expected + "ac:" + actual);

        assertListSize(expected, actual);
    }

    @Test
    public void  execute_tagEmail_success() throws  Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        MassEmailCommand command = prepareCommand("friends");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        int expected = expectedModel.getAddressBook().getPersonList().size();

        model.updateFilteredPersonList(command.getPredicate());
        int actual = model.getFilteredPersonList().size();
        System.out.print("ex:" + expected + "ac:" + actual);
        assertdifferentListSize(expected, actual);
    }

    /**
     * Parses {@code userInput} into a {@code MassEmailCommand}.
     */
    private MassEmailCommand prepareCommand(String userInput) {
        MassEmailCommand command =
                new MassEmailCommand(new MassEmailPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private void assertListSize(int ex, int act) {
        assert (ex == act);
    }

    private void assertdifferentListSize(int ex, int act) {
        assert (ex != act);
    }


}
