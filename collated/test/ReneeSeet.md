# ReneeSeet
###### \java\seedu\address\logic\commands\MassEmailCommandTest.java
``` java

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
```
###### \java\seedu\address\logic\parser\MassEmailCommandParserTest.java
``` java

public class MassEmailCommandParserTest {

    private MassEmailParser parser = new MassEmailParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMassEmailCommand() {
        // no leading and trailing whitespaces
        MassEmailCommand expectedMassEmailCommand =
                new MassEmailCommand(new MassEmailPredicate((Arrays.asList("friends", "OwesMoney"))));
        assertParseSuccess(parser, "friends OwesMoney", expectedMassEmailCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t OwesMoney  \t", expectedMassEmailCommand);
    }
}
```
