# ReneeSeet
###### \AddAttendanceCommand.java
``` java
        JoinDate date = editAttendancePersonDescriptor.getJoinDate();
```
###### \AddAttendanceCommand.java
``` java
        private JoinDate date;
```
###### \AddAttendanceCommand.java
``` java
            this.date = toCopy.date;
```
###### \AddAttendanceCommand.java
``` java
        public void setJoinDate(JoinDate date) {
            this.date = date;
        }

        public JoinDate getJoinDate() {
            return date;
        }

```
###### \GmailApiTest.java
``` java
/**
 * Contains tests for {@link GmailApi}
 * Note the verification of sending emails are done done via exploratory testing
 * Because it is diffcult for test to authenticate google account and send out an actual email via Gmail's API
 * testCase below is not run but this can help test if  google account has been pre autheticated.
 */

public class GmailApiTest {
    private GmailApi gmailApi;
    private static final String STUB_MESSAGE = "This is a stub message";
    private static final String STUB_EMAIL = "stub@gmail.com";
    private static final String STUB_SUBJECT = "test";

   @Test
    public void execute_Sending_email() throws IOException, MessagingException {
        gmailApi = new GmailApi();
        Gmail service = GmailApi.getGmailService();
        String user = "me";
        MimeMessage email = GmailApi.createEmail(STUB_EMAIL, user, STUB_SUBJECT , STUB_MESSAGE);
        GmailApi.sendMessage(service, user, email);
    }
}
```
###### \LoginHandle.java
``` java
/**
 * This is LoginHandle for the login page.
 * It will fill up the email textfield and password textfield and press ENTER.
 * Reason why this was unused: I dedcided to remove the login page as it was not good
 * to store passsword and username and it caused coupling with the other features.
 */
/**
 * A handle to the {@code LoginPage} in the GUI.
 */

public class LoginHandle extends NodeHandle<Node> {

    public static final String LOGIN_DISPLAY_ID = "#LoginPanel";
    private static final String DEFAULT_EMAIL = "test@gmail.com";
    private static final String DEFAULT_PASSWORD = "password";

    public LoginHandle(Node loginPanelNode) {
        super(loginPanelNode);
        TextField s = getChildNode("#emailBox");
        s.setText(DEFAULT_EMAIL);
        PasswordField p = getChildNode("#passwordBox");
        p.setText(DEFAULT_PASSWORD);
        guiRobot.type(KeyCode.ENTER);
    }
}
```
###### \LoginPanel.fxml
``` fxml

<?import java.net.URL?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<Pane fx:id="LoginPanel" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="605.0" prefWidth="594.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <stylesheets>
        <URL value="@../src/main/resources/view/Styles.css" />
        <URL value="@../src/main/resources/view/Extensions.css" />
    </stylesheets>
    <children>
        <PasswordField fx:id="passwordBox" layoutX="246.0" layoutY="406.0" onAction="#onEnter" />
        <Text layoutX="158.0" layoutY="427.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Password" />
        <Text layoutX="172.0" layoutY="362.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Email" />
        <Text fx:id="loginText" layoutX="142.0" layoutY="536.0" strokeType="OUTSIDE" strokeWidth="0.0" text="" wrappingWidth="373.0" />
        <TextField fx:id="emailBox" layoutX="246.0" layoutY="341.0" onAction="#onEnter" />
        <Button fx:id="loginButton" layoutX="273.0" layoutY="469.0" mnemonicParsing="false" text="Login" />
        <Text layoutX="246.0" layoutY="260.0" strokeType="OUTSIDE" strokeWidth="0.0" text="CYNC">
            <font>
                <Font size="54.0" />
            </font>
        </Text>
        <Text layoutX="222.0" layoutY="306.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Customer Manager" textAlignment="CENTER" wrappingWidth="186.13476524021826" />
    </children>
</Pane>
```
###### \LoginPanel.java
``` java

/**
 * The Login Panel of the App.
 */
public class LoginPanel extends UiPart<Region> {

    private static final String FXML = "LoginPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(LoginPanel.class);
    private static final String DEFAULT_EMAIL = "test@gmail.com";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String INVALID_EMAIL_PASSWORD = "Invalid password and email";
    private static final String LOGGER_SUCCESSFUL_LOGIN = "SUCCESSFUL LOGIN";
    private static final String EMPTY_FIELDS = "Please enter email and password";
    private static final string LOGGER_EMPTY_FIELDS = "email textbox and password textbox are empty";

    @FXML
    private Button loginButton;

    @FXML
    private Text loginText;

    @FXML
    private TextField emailBox;

    @FXML
    private PasswordField passwordBox;

    public LoginPanel() {
        super(FXML);
    }

    /**
    * Upon ENTER, check the password and email textbox for password and email.
    * If password and email are valid , post LoginRequestEvent
    */

    @FXML
    public void onEnter(ActionEvent ae) {
        if (!emailBox.getText().equals("") && !passwordBox.getText().equals("")) {
            if(emailBox.getText().equals(DEFAULT_EMAIL) && passwordBox.getText().equals(DEFAULT_PASSWORD)) {
                EventsCenter.getInstance().post(new LoginRequestEvent());
                logger.info(LOGGER_SUCCESSFUL_LOGIN);
            }else {
                loginText.setText(INVALID_EMAIL_PASSWORD);
                logger.info(INVALID_EMAIL_PASSWORD);
            }
        } else {
            loginText.setText(EMPTY_FIELDS);
            logger.info(LOGGER_EMPTY_FIELDS);
        }
    }
}
```
###### \LoginRequestEvent.java
``` java

/**
 * LoginRequestEvent will be handled by the UIManager.
 * Reason why this was unused: I dedcided to remove the login page as it was not good
 * to store passsword and username and it caused coupling with the other features.
 */

/**
 * Indicates a request to Login after entering valid email and password
 */

public class LoginRequestEvent extends BaseEvent {

    public LoginRequestEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \MainWindow.java
``` java
    /**
     * Fills up all the placeholders of this window for login.
     */
    void fillLogin() {
        loginPanel = new LoginPanel();
        browserPanel = new BrowserPanel();
        calendarPanel = new CalendarPanel();
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(), logic.getFilteredPersonList().size());
        resultDisplay = new ResultDisplay();
        commandBox = new CommandBox(logic);
        browserPlaceholder.getChildren().add(loginPanel.getRoot());
    }

```
###### \MainWindow.java
``` java
    /**
     * Switch to the Email panel.
     */
    @FXML
    public void handleEmail(ArrayList<String> emails) {
        emailPanel = new EmailPanel(emails);
        browserPlaceholder.getChildren().add(emailPanel.getRoot());
        browserPlaceholder.getChildren().setAll(emailPanel.getRoot());
    }
```
###### \MainWindow.java
``` java
    @Subscribe
    private void handleMassEmailEvent(MassEmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleEmail(event.getEmailList());
    }
```
###### \MainWindowWithLoginHandle.java
``` java

/**
 * When testing, this will handle the Login Panel before handling the main window.
 * Reason why this was unused: This is not a good practice as this causes increase in coupling
 * and cause the testing of other features to be dependent on the Login feature.
 */
/**
 * Provides a handle to the main menu of the app.
 */

public class MainWindowWithLoginHandle extends StageHandle {

    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private StatusBarFooterHandle statusBarFooter;
    private MainMenuHandle mainMenu;
    private BrowserPanelHandle browserPanel;
    private final LoginHandle loginPanel;

    public MainWindowWithLoginHandle(Stage stage) {
        super(stage);
        loginPanel = new LoginHandle(getChildNode(LoginHandle.LOGIN_DISPLAY_ID));
        EventsCenter.getInstance().post(new LoginRequestEvent()); //unlock for system test
        try {
            personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
            resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
            commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
            statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
            mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
            browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
        } catch (NodeNotFoundException ne) {
            EventsCenter.getInstance().post(new LoginRequestEvent()); //unlock for system test
            personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
            resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
            commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
            statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
            mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
            browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
        }
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }
}
```
###### \UiManager.java
``` java
    @Subscribe
    private void handleLogin(LoginRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.fillInnerParts();
    }
```
