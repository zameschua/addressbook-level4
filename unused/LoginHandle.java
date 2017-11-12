package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import  javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

//@@author ReneeSeet
/**
 * A handle to the {@code LoginPage} in the GUI.
 */

public class LoginHandle extends NodeHandle<Node> {

    public static final String LOGIN_DISPLAY_ID = "#LoginPanel";

    public LoginHandle(Node loginPanelNode) {
        super(loginPanelNode);
        TextField s = getChildNode("#emailBox");
        s.setText("test@gmail.com");
        PasswordField p = getChildNode("#passwordBox");
        p.setText("password");
        guiRobot.type(KeyCode.ENTER);
    }
}
