# ReneeSeet
###### \java\guitests\guihandles\EmailPanelHandle.java
``` java
/**
 * A handler for the {@code MassEmailPanel} of the UI.
 */

public class EmailPanelHandle extends NodeHandle<Node>  {

    public static final String EMAIL_ID = "#emailpanel";
    public static final String SUBJECT_TEXTBOX_ID = "emailSubjectBox";
    public static final String TO_TEXTBOX_ID = "recipientsBox";
    public static final String SEND_BUTTON_ID = "sendButton";
    public static final String MESSAGE_TEXTBOX_ID = "emailMessage";

    private Node recipientBox;
    private Node subjectBox;
    private Node sendButton;
    private Node emailMessage;

    public EmailPanelHandle(Node emailPanelNode) {
        super(emailPanelNode);
        Pane pane = getChildNode(EMAIL_ID);
        ObservableList<Node> listNode = pane.getChildren();
        for (int i = 0; i < listNode.size(); i++) {
            if (listNode.get(i).getId() != null) {
                if (listNode.get(i).getId().equals(TO_TEXTBOX_ID)) {
                    recipientBox = listNode.get(i);
                } else if (listNode.get(i).getId().equals(SEND_BUTTON_ID)) {
                    sendButton = listNode.get(i);
                } else if (listNode.get(i).getId().equals(MESSAGE_TEXTBOX_ID)) {
                    emailMessage = listNode.get(i);
                } else if (listNode.get(i).getId().equals(SUBJECT_TEXTBOX_ID)) {
                    subjectBox = listNode.get(i);
                }
            }
        }
    }

    public String getRecipientsText() {
        TextField f = (TextField) recipientBox;
        return f.getText();
    }

    public String getSubjectText() {
        TextField f = (TextField) subjectBox;
        return f.getText();
    }

    public String getMessageText() {
        TextArea f = (TextArea) emailMessage;
        return f.getText();
    }

    public Button getSendButton() {
        Button f = (Button) sendButton;
        return f;
    }

}
```
###### \java\seedu\address\logic\commands\MassEmailCommandTest.java
``` java

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
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public static final String DEFAULT_DATE = "27/20/2017";
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
            JoinDate defaultDate = new JoinDate(DEFAULT_DATE);
            this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress, defaultDate, defaultTags);
```
###### \java\seedu\address\ui\EmailPanelTest.java
``` java

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.EmailPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

```
###### \java\seedu\address\ui\EmailPanelTest.java
``` java

public class EmailPanelTest extends GuiUnitTest  {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());
    private EmailPanel emailPanel;
    private EmailPanelHandle emailPanelHandle;
    private String expectedEmail;
    private ArrayList<String> emails;

    @Before
    public void setUp() {
        StringBuilder expectedEmailbuilder = new StringBuilder();
        emails = new ArrayList<String>();
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            emails.add(TYPICAL_PERSONS.get(i).getEmail().toString());
            expectedEmailbuilder.append(TYPICAL_PERSONS.get(i).getEmail().toString()).append(";");
        }
        expectedEmail = expectedEmailbuilder.toString();
        guiRobot.interact(() -> emailPanel = new EmailPanel(emails));
        uiPartRule.setUiPart(emailPanel);

        emailPanelHandle = new EmailPanelHandle(emailPanel.getRoot());
    }

    @Test
    public void display() {
        //check that the emails get added to "to:" textbox correctly
        assertEquals(expectedEmail, emailPanelHandle.getRecipientsText());
        //check that the subject box is empty
        assertEquals("", emailPanelHandle.getSubjectText());
        //check that the Message box is
        assertEquals("", emailPanelHandle.getMessageText());
    }
}
```
