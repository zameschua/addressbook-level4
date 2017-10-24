package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Panel containing the list of persons.
 */
public class PersonInfo extends UiPart<Region> {
    private static final String FXML = "PersonInfo.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonInfo.class);
    public final ReadOnlyPerson person;

    @FXML
    private ImageView profilePic;

    @FXML
    private Label name;

    @FXML
    private Label address;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private ImageView attendance;



    public PersonInfo (ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().toString());
        address.setText(person.getAddress().toString());
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());
        profilePic.setImage(new Image("https://vignette.wikia.nocookie.net/disney/images/f/f6/MickeyArt.jpg/revision/latest/scale-to-width-down/250?cb=20130705054827"));
        attendance.setImage(new Image("http://communityofhope.church/wp-content/uploads/2015/02/Next-Attendance-Graph.jpg"));
    }

}
