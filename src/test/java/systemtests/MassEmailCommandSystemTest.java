package systemtests;

import static seedu.address.testutil.TypicalPersons.ALICE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.MassEmailCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.MassEmailPredicate;

//@@author ReneeSeet

public class MassEmailCommandSystemTest extends AddressBookSystemTest{

    private static final String MESSAGE_INVALID_EMAIL_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE);
    @Test
    public void MassEmail() throws Exception {
        Model expectedModel = getModel();
       //Case: Invalid mass email command
        String command = MassEmailCommand.COMMAND_WORD;
        String expectedResultMessage = String.format(MESSAGE_INVALID_EMAIL_COMMAND_FORMAT);
        assertCommandFailure(command, expectedResultMessage);

        //Case: valid mass email command
        command = MassEmailCommand.COMMAND_WORD + " " + "all";
        expectedResultMessage = buildExpectedMessage(getModel().getAddressBook().getPersonList());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Case: one valid tag
        command = MassEmailCommand.COMMAND_WORD + " " + "family";
        expectedModel.updateFilteredPersonList(new MassEmailPredicate(Arrays.asList("family")));
        expectedResultMessage = buildExpectedMessage(Arrays.asList(ALICE));
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Case: no valid tag
        command = MassEmailCommand.COMMAND_WORD + " " + "hello";
        expectedModel.updateFilteredPersonList(new MassEmailPredicate(Arrays.asList("hello")));
        expectedResultMessage = buildExpectedMessage(Collections.emptyList());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        // Case: undo  --> nothing to undo
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        //case:redo -->nothing to redo
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);
       }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
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
