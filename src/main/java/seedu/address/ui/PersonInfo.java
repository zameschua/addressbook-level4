package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

//@@author pohjie
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
    private PieChart attendance;

    public PersonInfo (ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        profilePic.setImage(new Image(person.getProfilePic().toString()));
        name.setText(person.getName().toString());
        address.setText(person.getAddress().toString());
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());

        // This is not bound to the person. If we change attendance or missed when the person is
        // shown in browser panel this will not be reflected
        ObservableList<PieChart.Data> attendanceData = FXCollections.observableArrayList(
                new PieChart.Data("Present", person.getAttendance().getAttended()),
                new PieChart.Data("Absent", person.getAttendance().getMissed()));

        attendance.setData(attendanceData);
    }

}
