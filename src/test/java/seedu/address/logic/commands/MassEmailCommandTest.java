package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_NOBODY_FOUND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagMatchingPredicate;

//@@author ReneeSeet

/**
 * Contains integration tests (interaction with the Model) and unit tests for MassEmailCommand.
 */

public class MassEmailCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_NOBODY_FOUND);
        MassEmailCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    //test 'all' predicate
   @Test
    public void execute_massEmail_all_success() throws Exception {
        MassEmailCommand command = prepareCommand("all");
        String expectedMessage = buildExpectedMessage(getTypicalAddressBook().getPersonList());
        assertCommandSuccess(command,expectedMessage,  getTypicalAddressBook().getPersonList());
    }

    @Test
    // one valid tag
    public void  execute_tagEmail_success() throws  Exception {
        MassEmailCommand command = prepareCommand("family");
        String expectedMessage = buildExpectedMessage( Arrays.asList(ALICE));
        assertCommandSuccess(command,expectedMessage, Arrays.asList(ALICE));
    }

    @Test
    //no vaild tag
    public void  executenoVaildTagEmailsuccess() throws  Exception {
        MassEmailCommand command = prepareCommand("hello");
        String expectedMessage = buildExpectedMessage(Collections.emptyList());
        assertCommandSuccess(command,expectedMessage, Collections.emptyList());
    }

    @Test
    // 1 vaild tag and 1 invalid tag
    public void  executevalidInvalidtagEmailsuccess() throws  Exception {
        MassEmailCommand command = prepareCommand("family hello");
        String expectedMessage = buildExpectedMessage( Arrays.asList(ALICE));
        assertCommandSuccess(command,expectedMessage, Arrays.asList(ALICE));
    }

    /**
     * Parses {@code userInput} into a {@code MassEmailCommand}.
     */
    private MassEmailCommand prepareCommand(String userInput) {
        MassEmailCommand command =
                new MassEmailCommand(new TagMatchingPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(MassEmailCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    /**
     * Builds Expected Message for Mass Email
     */
    private String buildExpectedMessage(List<ReadOnlyPerson> expectedList) {
        if (!expectedList.isEmpty()) {
            StringBuilder mess = new StringBuilder(String.format(Messages.MESSAGE_SMS_CONFIRMATION, expectedList.size()));
            mess.append("\n");
            for (int i = 0; i<expectedList.size(); i++) {
                mess.append(expectedList.get(i).getEmail());
                mess.append("\n");
            }
            return mess.toString();
        } else {
            return Messages.MESSAGE_NOBODY_FOUND;
        }
    }
}
