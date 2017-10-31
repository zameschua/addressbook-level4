package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class PersonInfo extends UiPart<Region> {
    private static final String FXML = "PersonInfo.fxml";
    public final ReadOnlyPerson person;
    private final Logger logger = LogsCenter.getLogger(PersonInfo.class);

    @FXML
    private ImageView profilePic;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private ImageView attendance;

    public PersonInfo (ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        profilePic.setImage(new Image(person.getProfilePic().toString()));
        name.setText(person.getName().toString());
        address.setText(person.getAddress().toString());
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());
        attendance.setImage(new Image(
                "https://upload.wikimedia.org/wikipedia/commons"
                        + "/0/05/Swingley_mpost_scorecard_black_pitchers_clean.png"));
    }

}
