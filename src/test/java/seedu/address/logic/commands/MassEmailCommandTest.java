package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MassEmailPredicate;
import seedu.address.model.person.ReadOnlyPerson;

//@@author ReneeSeet

/**
 * Contains integration tests (interaction with the Model) and unit tests for MassEmailCommand.
 */

public class MassEmailCommandTest {
    private Model model;
    private Model originalModel;
    private int original;

    @Test
    public void execute_massEmail_success() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        originalModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        original = originalModel.getAddressBook().getPersonList().size();
        MassEmailCommand command = prepareCommand("all");
        model.updateFilteredPersonList(command.getPredicate());
        int actual = model.getFilteredPersonList().size();
        System.out.print("ex:" + original + "ac:" + actual);
        assertListSize(original, actual);
        assertequalList(originalModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    // one valid tag
    public void  execute_tagEmail_success() throws  Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        originalModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        original = originalModel.getAddressBook().getPersonList().size();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        MassEmailCommand command = prepareCommand("friends");
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        int expected = expectedModel.getAddressBook().getPersonList().size();

        model.updateFilteredPersonList(command.getPredicate());
        int actual = model.getFilteredPersonList().size();
        System.out.print("ex:" + expected + "ac:" + actual);
        assertdifferentListSize(expected, actual);
        assertunequalList(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    // no vaild tag
    public void  executenoVaildTagEmailsuccess() throws  Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        originalModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        original = originalModel.getAddressBook().getPersonList().size();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        MassEmailCommand command = prepareCommand("hello");
        model.updateFilteredPersonList(command.getPredicate());
        int actual = model.getFilteredPersonList().size();
        System.out.print("ex:" + original + "ac:" + actual);
        assertdifferentListSize(original, actual);
        assert(model.getFilteredPersonList().isEmpty());
    }

    @Test
    // 1 vaild tag and 1 invalid tag
    public void  executevalidInvalidtagEmailsuccess() throws  Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        originalModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        original = originalModel.getAddressBook().getPersonList().size();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        MassEmailCommand command = prepareCommand("friends hello");
        model.updateFilteredPersonList(command.getPredicate());
        int actual = model.getFilteredPersonList().size();
        System.out.print("ex:" + original + "ac:" + actual);
        assertdifferentListSize(original, actual);
        assert(!model.getFilteredPersonList().isEmpty());
        assertunequalList(originalModel.getAddressBook().getPersonList(), model.getFilteredPersonList());
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

    private void assertequalList(ObservableList<ReadOnlyPerson> ex, ObservableList<ReadOnlyPerson> act) {
        assert (act.containsAll(ex));
    }

    private void assertunequalList(ObservableList<ReadOnlyPerson> ex, ObservableList<ReadOnlyPerson> act) {
        assert !(act.containsAll(ex));
    }
}
