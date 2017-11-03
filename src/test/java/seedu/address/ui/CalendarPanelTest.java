package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.CalendarPanel.DEFAULT_CALENDAR_URL;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CalendarPanelHandle;
import seedu.address.commons.events.ui.CalendarRequestEvent;

//@@author yilun-zhu

public class CalendarPanelTest extends GuiUnitTest {

    private CalendarPanel calendarPanel;
    private CalendarPanelHandle calendarPanelHandle;
    private CalendarRequestEvent calendarRequestEvent;

    @Before
    public void setUp() {
        calendarRequestEvent = new CalendarRequestEvent();

        guiRobot.interact(() -> calendarPanel = new CalendarPanel());
        uiPartRule.setUiPart(calendarPanel);

        calendarPanelHandle = new CalendarPanelHandle(calendarPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default calendar page
        URL expectedDefaultPageUrl = new URL(DEFAULT_CALENDAR_URL);
        assertEquals(expectedDefaultPageUrl.toString(), calendarPanelHandle.getLoadedUrl().toString());

    }
}
