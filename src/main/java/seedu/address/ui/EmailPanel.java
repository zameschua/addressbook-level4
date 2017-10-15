package seedu.address.ui;

import static seedu.address.commons.events.model.CallGmailApi.getGmailService;
import static seedu.address.logic.SendEmail.createEmail;
import static seedu.address.logic.SendEmail.sendMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.api.services.gmail.Gmail;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * The Email Panel of the App.
 */

public class EmailPanel extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EmailPanel.fxml";
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
                try {
                    sendEmail();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                logger.info("SEND BUTTON CLICK!!!!!");
            }
            });
        String recipients = appendEmails(emailList);
        recipientsBox.setText(recipients);
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
     * Method calls createEmail and sendMessage to each recipient
     */
    private void sendEmail() throws IOException, MessagingException {
        // Build a new authorized API client service.
        String subject = emailSubjectBox.getText();
        String message = emailMessage.getText();
        String[] recipients = recipientsBox.getText().split(";");
        Gmail service = getGmailService();
        String user = "me";
        for (String s : recipients) {
            MimeMessage email = createEmail(s, user, subject, message);
            sendMessage(service, user, email);
            logger.info("EMAIL SENT");
        }
    }
}
