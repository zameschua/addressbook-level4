# ReneeSeet
###### \java\seedu\address\commons\events\external\SendEmailRequestEvent.java
``` java

/**
 * Indicates a request for Sending of Emails
 */

public class SendEmailRequestEvent extends BaseEvent {

    private String subject;
    private String message;
    private String[] recipients;

    public SendEmailRequestEvent(String subject, String message, String[] recipients) {
        this.subject = subject;
        this.message = message;
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String[] getRecipients() {
        return recipients;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\LoginRequestEvent.java
``` java

/**
 * Indicates a request for Logging
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
###### \java\seedu\address\commons\events\ui\MassEmailRequestEvent.java
``` java

/**
 * Indicates a request for MassEmailRequest
 */

public class MassEmailRequestEvent extends BaseEvent {

    private final ArrayList<String> emails;

    public MassEmailRequestEvent(ArrayList<String> emails) {
        this.emails = emails;
    }

    public ArrayList<String> getEmailList() {
        return emails;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\external\CallGmailApi.java
``` java

/** call gmail API * */

public class CallGmailApi extends ExternalCall {

    private static final Logger logger = LogsCenter.getLogger(CallGmailApi.class);
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Gmail API Java Quickstart";
    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/gmail-java-quickstart");
    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoryFactory;
    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();
    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;
    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.GMAIL_COMPOSE, GmailScopes.GMAIL_SEND);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoryFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    public CallGmailApi() {
        registerAsAnEventHandler(this);
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                CallGmailApi.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(dataStoryFactory)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }
    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    public static Message sendMessage(Gmail service,
                                      String userId,
                                      MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
        return message;
    }
    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
    @Subscribe
    public void handleSendEmailRequestEvent(SendEmailRequestEvent event) throws IOException, MessagingException {
        // Build a new authorized API client service.
        try {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            Gmail service = getGmailService();
            String user = "me";
            String[] recipients = event.getRecipients();
            for (String s : recipients) {
                MimeMessage email = createEmail(s, user, event.getSubject(), event.getMessage());
                sendMessage(service, user, email);
                logger.info("EMAIL SENT");
            }
            EventsCenter.getInstance().post(new NewResultAvailableEvent(MESSAGE_EMAIL_SUCCESS));
        } catch (IOException e) {
            logger.info("IO");
        } catch (MessagingException d) {
            logger.info("messageException");
        }
    }
}
```
###### \java\seedu\address\external\ExternalCall.java
``` java

/**
 * Represents External Calls for external API
 * Allow external class to handle events
 */

public abstract class ExternalCall {
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }
}
```
###### \java\seedu\address\external\SendEmail.java
``` java

/**
 * This Class contains the different email creation methods.
 */

public class SendEmail {

    /**
     * Create draft email.
     *
     * @param service an authorized Gmail API instance
     * @param userId user's email address. The special value "me"
     * can be used to indicate the authenticated user
     * @param emailContent the MimeMessage used as email within the draft
     * @return the created draft
     * @throws MessagingException
     * @throws IOException
     */
    public static Draft createDraft(Gmail service,
                                    String userId,
                                    MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        Draft draft = new Draft();
        draft.setMessage(message);
        draft = service.users().drafts().create(userId, draft).execute();

        System.out.println("Draft id: " + draft.getId());
        System.out.println(draft.toPrettyString());
        return draft;
    }


    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    public static Message sendMessage(Gmail service,
                                      String userId,
                                      MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
        return message;
    }


    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }


    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to Email address of the receiver.
     * @param from Email address of the sender, the mailbox account.
     * @param subject Subject of the email.
     * @param bodyText Body text of the email.
     * @param file Path to the file to be attached.
     * @return MimeMessage to be used to send email.
     * @throws MessagingException
     */
    public static MimeMessage createEmailWithAttachment(String to,
                                                        String from,
                                                        String subject,
                                                        String bodyText,
                                                        File file)
            throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(bodyText, "text/plain");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(file);

        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName(file.getName());

        multipart.addBodyPart(mimeBodyPart);
        email.setContent(multipart);

        return email;
    }

}
```
###### \java\seedu\address\logic\commands\MassEmailCommand.java
``` java

/**
 * Finds and lists all persons in address book whose tags keywords for group emailing
 * Keyword matching is case-sensitive.
 */

public class MassEmailCommand extends Command {

    public static final String COMMAND_WORD = "mass";
    public static final String MESSAGE_SUCCESS = "Listed all required emails";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": To email all persons with tag\n"
             + COMMAND_WORD + " all : To email everyone in CYNC\n"
             + "Parameters: KEYWORD [MORE_KEYWORDS]... \n"
             + "Example: " + COMMAND_WORD + " Sec 2 Sec 3\n";

    private final MassEmailPredicate predicate;

    public MassEmailCommand(MassEmailPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        ObservableList<ReadOnlyPerson> allPerson = model.getFilteredPersonList();
        ArrayList<String> emails = new ArrayList<String>();
        for (ReadOnlyPerson person : allPerson) {
            emails.add(person.getEmail().toString());
        }
        EventsCenter.getInstance().post(new MassEmailRequestEvent(emails));
        return new CommandResult(getMessageForMassEmail(allPerson.size(), emails));
    }

    public MassEmailPredicate getPredicate() {
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MassEmailCommand // instanceof handles nulls
                && this.predicate.equals(((MassEmailCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\MassEmailParser.java
``` java

/**
 * Parses input arguments and creates a new MassEmailCommand object
 */

public class MassEmailParser implements Parser<MassEmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MassCommand
     * and returns an MassCommand object for execution.
     */

    public MassEmailCommand parse(String args)throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new MassEmailCommand(new MassEmailPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \java\seedu\address\model\person\MassEmailPredicate.java
``` java

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches any of the Tag keywords given.
 * if Tag Keywords is "all", returns true for everyone
 */


public class MassEmailPredicate implements Predicate<ReadOnlyPerson> {

    private final List<String> keytags;

    public MassEmailPredicate(List<String> keytags) {
        this.keytags = keytags;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (keytags.size() == 1 && keytags.contains("all")) {
            return true;
        }
        Set<Tag> tags = person.getTags();
        for (Tag s : tags) {
            for (String key : keytags) {
                if (key.equals(s.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MassEmailPredicate // instanceof handles nulls
                && this.keytags.equals(((MassEmailPredicate) other).keytags)); // state check
    }

}
```
###### \java\seedu\address\ui\EmailPanel.java
``` java

/**
 * The Email Panel of the App.
 */

public class EmailPanel extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EmailPanel.fxml";
    private static final String signature = "Best Regards,\n"
            + "Ms.Renee Seet\n" + "Customer Manager\n";  // Should be able to customise
    private static final Logger logger = LogsCenter.getLogger(EmailPanel.class);

    @FXML
    private TextField emailSubjectBox;
    @FXML
    private TextField recipientsBox;
    @FXML
    private TextArea emailMessage;
    @FXML
    private Button sendButton;

    public  EmailPanel(ArrayList<String> emailList) {
        super(FXML);
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    sendEmail();
                logger.info("SEND BUTTON CLICKED");
            }
            });
        String recipients = appendEmails(emailList);
        recipientsBox.setText(recipients);
        emailMessage.setText(signature);
        registerAsAnEventHandler(this);
    }

    /**
     * Method returns a string to be displayed in recipientBox
     */
    private String appendEmails(ArrayList<String> emails) {
        StringBuilder emailList = new StringBuilder();
        for (String s: emails) {
            emailList.append(s).append(";");
        }
        return emailList.toString();
    }
    /**
     * Method posts SendEmailRequestEvent
     */
    private void sendEmail() {
        String subject = emailSubjectBox.getText();
        String message = emailMessage.getText();
        String[] recipients = recipientsBox.getText().split(";");
        new CallGmailApi();
        EventsCenter.getInstance().post(new SendEmailRequestEvent(subject, message, recipients));
    }
}
```
###### \java\seedu\address\ui\LoginPanel.java
``` java

/**
 * The Login Panel of the App.
 */
public class LoginPanel extends UiPart<Region> {

    private static final String FXML = "LoginPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(LoginPanel.class);

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
    * Catch Enter button
    */

    @FXML
    public void onEnter(ActionEvent ae) {
        if (!emailBox.getText().equals("") && !passwordBox.getText().equals("")) {
            EventsCenter.getInstance().post(new LoginRequestEvent());
        } else {
            loginText.setText("Please enter email and password");
            logger.info("nothing entered");
        }
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
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
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleMassEmailEvent(MassEmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleEmail(event.getEmailList());
    }
```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Subscribe
    private void handleLogin(LoginRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.fillInnerParts();
    }
}
```
###### \resources\view\EmailPanel.fxml
``` fxml

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<Pane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="0.0" prefHeight="678.0" prefWidth="973.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Text layoutX="46.0" layoutY="53.0" strokeType="OUTSIDE" strokeWidth="0.0" text="To:">
         <font>
            <Font size="23.0" />
         </font></Text>
      <TextField fx:id="recipientsBox" layoutX="108.0" layoutY="28.0" prefHeight="45.0" prefWidth="839.0" />
      <Text layoutX="21.0" layoutY="114.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Subject:">
         <font>
            <Font size="23.0" />
         </font></Text>
      <TextField fx:id="emailSubjectBox" layoutX="108.0" layoutY="82.0" prefHeight="45.0" prefWidth="839.0" />
      <TextArea fx:id="emailMessage" layoutX="29.0" layoutY="149.0" prefHeight="433.0" prefWidth="930.0" />
      <Button fx:id="sendButton" layoutX="449.0" layoutY="601.0" mnemonicParsing="false" text="Send" />
   </children>
</Pane>
```
###### \resources\view\LoginPanel.fxml
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
        <URL value="@Styles.css" />
        <URL value="@Extensions.css" />
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
