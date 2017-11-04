package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

//@@author ReneeSeet
/**
 * A handler for the {@code MassEmailPanel} of the UI.
 */

public class EmailPanelHandle extends NodeHandle<Node>  {
    
    public static final String EMAIL_ID = "#emailpanel";
    public static final String SUBJECT_TEXTBOX_ID = "emailSubjectBox";
    public static final String TO_TEXTBOX_ID = "recipientsBox";
    public static final String SEND_BUTTON_ID = "sendButton";
    public static final String MESSAGE_TEXTBOX_ID = "emailMessage";
    
    Node recipientBox; 
    Node SubjectBox; 
    Node SendButton; 
    Node emailMessage;

    public EmailPanelHandle(Node EmailPanelNode) {
        super(EmailPanelNode);
        Pane pane = getChildNode(EMAIL_ID);
        ObservableList<Node> listNode = pane.getChildren();
        for (int i = 0; i < listNode.size(); i++) {
            if (listNode.get(i).getId() != null) {
                if (listNode.get(i).getId().equals(TO_TEXTBOX_ID)) {
                    recipientBox = listNode.get(i);
                } else if (listNode.get(i).getId().equals(SEND_BUTTON_ID)) {
                    SendButton = listNode.get(i);
                } else if (listNode.get(i).getId().equals(MESSAGE_TEXTBOX_ID)) {
                    emailMessage = listNode.get(i); 
                } else if (listNode.get(i).getId().equals(SUBJECT_TEXTBOX_ID)) {
                    SubjectBox = listNode.get(i); 
                }
            }
        }
    }
    
    public String getRecipientsText(){ 
        TextField f = (TextField)recipientBox;
        return f.getText();
    }

    public String getSubjectText(){
        TextField f = (TextField)SubjectBox;
        return f.getText();
    }
    
    public String getMessageText(){
        TextArea f = (TextArea)emailMessage;
        return f.getText();
    }
    
    
}
