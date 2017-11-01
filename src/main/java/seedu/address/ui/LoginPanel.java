package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoginRequestEvent;

//@@author ReneeSeet

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
