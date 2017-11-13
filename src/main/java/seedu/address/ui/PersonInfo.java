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
    private static final String JOIN_DATE = "Joined date: ";

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
    //@@author ReneeSeet
    @FXML
    private Label date;
    //@@author pohjie
    @FXML
    private PieChart attendance;

    /**
     * This method is not bound to the person. In other words, if we make an edit to the person's info
     * while he/she is showing in PersonInfo, then we have to do manual refresh by re-selecting the person to
     * show the updated data.
     * @param person
     */
    public PersonInfo (ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        profilePic.setImage(new Image(person.getProfilePic().toString()));
        name.setText(person.getName().toString());
        address.setText(person.getAddress().toString());
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());
        //@@author ReneeSeet
        date.setText(JOIN_DATE + person.getJoinDate().toString());
        //@@author pohjie

        ObservableList<PieChart.Data> attendanceData = FXCollections.observableArrayList(
                new PieChart.Data("Present", person.getAttendance().getAttended()),
                new PieChart.Data("Absent", person.getAttendance().getMissed()));

        attendance.setData(attendanceData);
    }

}
