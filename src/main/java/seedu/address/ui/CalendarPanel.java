package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarRequestEvent;

//@@author yilun-zhu
/**
 * The Browser Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    public static final String DEFAULT_CALENDAR_URL = "https://calendar.google.com/calendar";

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView calendar;

    public CalendarPanel() {

        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultCalendarPage();
        registerAsAnEventHandler(this);
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    }

    private void loadDefaultCalendarPage() {
        loadCalendarPage(DEFAULT_CALENDAR_URL);
    }

    public void loadCalendarPage(String url) {
        Platform.runLater(() -> calendar.getEngine().load(url));
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        calendar = null;
    }

    @Subscribe
    private void handleCalendarRequestEvent(CalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCalendarPage(DEFAULT_CALENDAR_URL);
    }

}
