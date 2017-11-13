# zameschua
###### \java\guitests\CommandPredictionPanelGuiTest.java
``` java
public class CommandPredictionPanelGuiTest extends AddressBookGuiTest {
    private CommandPredictionPanelHandle commandPredictionPanelHandle;
    private String testCaseText = "";
    private List<String> expectedResults;
    private List<String> actualResults;

    @Before
    public void setUp() {
        CommandPredictionPanel commandPredictionPanel = new CommandPredictionPanel();
        commandPredictionPanelHandle = new CommandPredictionPanelHandle(commandPredictionPanel.getListView());
    }

    @Test
    public void execute_emptyString() {
        // Test case: String with empty string
        testCaseText = "";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_singleWhitespace() {
        // Test case: String with 1 whitespace
        testCaseText = " ";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_singleValidLetter() {
        // Test case: String with 1 valid letter
        testCaseText = "e";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }
    @Test
    public void execute_twoValidLetters() {
        // Test case: String with 2 valid letters
        testCaseText = "ex";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_wholeCommand() {
        // Test case: String with a whole command
        testCaseText = "exit";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_singleInvalidCharacter() {
        // Test case: String with 1 invalid character
        testCaseText = "*";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_startsWithInvalidCharacter() {
        // Test case: String starting with invalid character
        testCaseText = "*exit";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_startsWithWhitespace() {
        // Test case: String starting with whitespace
        testCaseText = " exit";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    /**
     * Helper method to clear the {@link seedu.address.ui.CommandBox}, then fill it with {@code inputText}
     * @param inputText the String to fill the {@code CommandBox} with
     */
    private void enterText(String inputText) {
        getCommandBox().enterText("");
        getCommandBox().enterText(inputText);
    }
}
```
###### \java\guitests\guihandles\CommandBoxHandle.java
``` java
    /**
     * Sets the text in the {@link CommandBox} to a certain text
     * Used in {@link CommandPredictionPanelGuiTest}
     * @param text the text to fill the CommandBox with
     */
    public void enterText(String text) {
        click();
        guiRobot.interact(() -> getRootNode().setText(text));
    }
```
###### \java\guitests\guihandles\SmsPanelHandle.java
``` java
/**
 * A handler for the {@link seedu.address.ui.SmsPanel} of the UI
 */
public class SmsPanelHandle extends NodeHandle<Node>  {

    public static final String SMS_PANEL_ID = "#smsPanel";
    public static final String TO_TEXTBOX_ID = "recipientsBox";
    public static final String MESSAGE_TEXTBOX_ID = "smsMessage";

    private Node recipientBox;
    private Node smsMessage;

    public SmsPanelHandle(Node smsPanelNode) {
        super(smsPanelNode);
        Pane pane = getChildNode(SMS_PANEL_ID);
        ObservableList<Node> listNode = pane.getChildren();
        for (int i = 0; i < listNode.size(); i++) {
            if (listNode.get(i).getId() == null) {
                continue;
            }
            switch (listNode.get(i).getId()) {
            case TO_TEXTBOX_ID:
                recipientBox = listNode.get(i);
                break;
            case MESSAGE_TEXTBOX_ID:
                smsMessage = listNode.get(i);
                break;
            default:
                break;
            }
        }
    }

    public String getRecipientsText() {
        TextField f = (TextField) recipientBox;
        return f.getText();
    }

    public String getMessageText() {
        TextArea f = (TextArea) smsMessage;
        return f.getText();
    }
}
```
###### \java\seedu\address\externals\TwilioApiHelperTest.java
``` java
/**
 * Contains tests for {@link TwilioApiHelper}
 * Note that test case for valid phone number is done via exploratory testing
 * Because it sends an actual SMS via Twilio's API
 */
public class TwilioApiHelperTest {
    private static final String STUB_MESSAGE = "This is a stub message";
    private static final String STUB_PHONE_NUMBER_INVALID = "1";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_invalidPhoneNumber_throwsAssertionException() {
        thrown.expect(AssertionError.class);
        TwilioApiHelper.init();
        TwilioApiHelper.sendSms(STUB_MESSAGE, STUB_PHONE_NUMBER_INVALID);
    }
}
```
###### \java\seedu\address\logic\commands\SmsCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SmsCommand.
 */
public class SmsCommandTest {
    private static final String STUB_USER_INPUT_ALL = "all";
    private static final String STUB_USER_INPUT_VALID = "owesMoney";
    private static final String STUB_USER_INPUT_INVALID = "invalid";

    private Model model;
    private Model originalModel;

    @Test
    public void execute_allTag_success() throws Exception {
        setupModel();
        int expectedCount = originalModel.getAddressBook().getPersonList().size();
        SmsCommand command = prepareCommand(STUB_USER_INPUT_ALL);

        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assertEqualList(originalModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    // one valid tag
    public void  execute_oneValidTag_success() throws Exception {
        setupModel();
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add(STUB_USER_INPUT_VALID);
        TagMatchingPredicate predicate = new TagMatchingPredicate(tagList);
        ObservableList expectedPersons =  originalModel.getAddressBook().getPersonList().filtered(predicate);
        int expectedCount = expectedPersons.size();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SmsCommand command = prepareCommand(STUB_USER_INPUT_VALID);
        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assertEqualList(expectedPersons, model.getFilteredPersonList());
    }

    @Test
    // no vaild tag
    public void  execute_oneInvalidTag_error() throws Exception {
        setupModel();
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add(STUB_USER_INPUT_INVALID);
        TagMatchingPredicate predicate = new TagMatchingPredicate(tagList);
        ObservableList expectedPersons =  originalModel.getAddressBook().getPersonList().filtered(predicate);
        int expectedCount = expectedPersons.size();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SmsCommand command = prepareCommand(STUB_USER_INPUT_INVALID);
        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assert(model.getFilteredPersonList().isEmpty());
    }

    @Test
    // 1 vaild tag and 1 invalid tag
    public void  execute_mixedValidInvalidTag_error() throws Exception {
        setupModel();
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add(STUB_USER_INPUT_VALID);
        tagList.add(STUB_USER_INPUT_INVALID);
        TagMatchingPredicate predicate = new TagMatchingPredicate(tagList);
        ObservableList expectedPersons =  originalModel.getAddressBook().getPersonList().filtered(predicate);
        int expectedCount = expectedPersons.size();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SmsCommand command = prepareCommand(STUB_USER_INPUT_INVALID + " " + STUB_USER_INPUT_VALID);
        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assert(!model.getFilteredPersonList().isEmpty());
        assertEqualList(expectedPersons, model.getFilteredPersonList());
    }

    /**
     * Helper method to setup a model {@code AddressBook}
     */
    private void setupModel() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        originalModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    /**
     * Parses {@code userInput} into a {@code SmsCommand}.
     */
    private SmsCommand prepareCommand(String userInput) {
        SmsCommand command =
                new SmsCommand(new TagMatchingPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private void assertListSize(int ex, int act) {
        assert (ex == act);
    }

    private void assertEqualList(ObservableList<ReadOnlyPerson> ex, ObservableList<ReadOnlyPerson> act) {
        assert (act.containsAll(ex));
    }
}
```
###### \java\seedu\address\logic\parser\SmsCommandParserTest.java
``` java
public class SmsCommandParserTest {

    private static final String STUB_TAG_VALID_FIRST = "owesMoney";
    private static final String STUB_TAG_VALID_SECOND = "classA";

    private SmsCommandParser parser = new SmsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSmsCommand() {
        SmsCommand expectedSmsCommand =
                new SmsCommand(new TagMatchingPredicate((Arrays.asList(STUB_TAG_VALID_FIRST, STUB_TAG_VALID_SECOND))));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, STUB_TAG_VALID_FIRST + " " + STUB_TAG_VALID_SECOND, expectedSmsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " \n " + STUB_TAG_VALID_FIRST + " \n \t " + STUB_TAG_VALID_SECOND + "  \t",
                expectedSmsCommand);
    }
}
```
###### \java\seedu\address\ui\SmsPanelTest.java
``` java
/**
 * GUI Tests for {@link SmsPanel}
 */
public class SmsPanelTest extends GuiUnitTest  {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());
    private SmsPanel smsPanel;
    private SmsPanelHandle smsPanelHandle;
    private String expectedPhoneNumbers;
    private ArrayList<String> phoneNumbers;

    @Before
    public void setUp() {
        StringBuilder expectedPhoneNumbersBuilder = new StringBuilder();
        phoneNumbers = new ArrayList<String>();
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            phoneNumbers.add(TYPICAL_PERSONS.get(i).getPhone().toString());
            expectedPhoneNumbersBuilder.append(TYPICAL_PERSONS.get(i).getPhone().toString()).append(";");
        }
        expectedPhoneNumbers = expectedPhoneNumbersBuilder.toString();
        guiRobot.interact(() -> smsPanel = new SmsPanel(phoneNumbers));
        uiPartRule.setUiPart(smsPanel);

        smsPanelHandle = new SmsPanelHandle(smsPanel.getRoot());
    }

    @Test
    public void execute_smsPanelGui_phoneNumbersAddedCorrectly() {
        //check that the phone numbers get added to "to:" textbox correctly
        assertEquals(expectedPhoneNumbers, smsPanelHandle.getRecipientsText());
        //check that the Message box is empty
        assertEquals("", smsPanelHandle.getMessageText());
    }
}
```
