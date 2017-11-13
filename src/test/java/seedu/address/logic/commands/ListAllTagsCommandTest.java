package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.JumpToListAllTagsRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author pohjie
/**
* Contains integration tests (interaction with the Model) and unit tests for ListAllTagsCommand.
*/
public class ListAllTagsCommandTest {

    private Model model;
    private Model expectedModel;
    private ListAllTagsCommand listAllTagsCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listAllTagsCommand = new ListAllTagsCommand();
        listAllTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    
    @Test
    public void execute_listAllTags_success() {
        try {
            CommandResult result = listAllTagsCommand.execute();
        } catch (CommandException e) {
            System.out.println("CommandException");
        }
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof JumpToListAllTagsRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_personListUnfiltered_showsEverything() throws Exception {
        assertCommandSuccess(listAllTagsCommand, model, ListAllTagsCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
