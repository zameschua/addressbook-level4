# pohjie
###### \java\seedu\address\logic\commands\ListAllTagsCommandTest.java
``` java
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

    @Test
    public void execute() throws Exception {
        assertCommandSuccess(listAllTagsCommand, model, ListAllTagsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
