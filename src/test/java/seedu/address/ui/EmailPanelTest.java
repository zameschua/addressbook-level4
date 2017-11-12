package seedu.address.ui;

//@@author ReneeSeet

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.EmailPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

//@@author ReneeSeet

public class EmailPanelTest extends GuiUnitTest  {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());
    private EmailPanel emailPanel;
    private EmailPanelHandle emailPanelHandle;
    private String expectedEmail;
    private ArrayList<String> emails;

    @Before
    public void setUp() {
        StringBuilder expectedEmailbuilder = new StringBuilder();
        emails = new ArrayList<String>();
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            emails.add(TYPICAL_PERSONS.get(i).getEmail().toString());
            expectedEmailbuilder.append(TYPICAL_PERSONS.get(i).getEmail().toString()).append(";");
        }
        expectedEmail = expectedEmailbuilder.toString();
        guiRobot.interact(() -> emailPanel = new EmailPanel(emails));
        uiPartRule.setUiPart(emailPanel);

        emailPanelHandle = new EmailPanelHandle(emailPanel.getRoot());
    }

    @Test
    public void display() {
        //check that the emails get added to "to:" textbox correctly
        assertEquals(expectedEmail, emailPanelHandle.getRecipientsText());
        //check that the subject box is empty
        assertEquals("", emailPanelHandle.getSubjectText());
        //check that the Message box is
        assertEquals("", emailPanelHandle.getMessageText());
    }
}
