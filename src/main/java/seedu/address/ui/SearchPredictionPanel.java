package seedu.address.ui;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CommandBoxKeyInputEvent;
import seedu.address.commons.events.ui.SearchPredictionPanelHideEvent;
import seedu.address.commons.events.ui.SearchPredictionPanelNextSelectionEvent;
import seedu.address.commons.events.ui.SearchPredictionPanelPreviousSelectionEvent;
import seedu.address.commons.events.ui.SearchPredictionPanelSelectionChangedEvent;

/**
 * Panel containing search predictions
 * It only shows when the user types something into the search box
 * And a search prediction is expected
 * Kinda like Google's search prediction.
 */
public class SearchPredictionPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(SearchPredictionPanel.class);
    private static final String FXML = "SearchPredictionPanel.fxml";
    private static final ArrayList<String> SEARCH_PREDICTION_RESULTS_INITIAL =
            new ArrayList<String>(Arrays.asList(
                    "help", "add", "list", "edit", "find", "delete", "select",
                    "history", "undo", "redo", "clear", "exit"));

    private static ObservableList<String> searchPredictionResults;
    // tempPredictionResults used to store the results from filtering through SEARCH_PREDICTION_RESULTS_INITIAL
    private ArrayList<String> tempPredictionResults;

    @FXML
    private ListView<String> searchPredictionListView;

    public SearchPredictionPanel() {
        super(FXML);
        registerAsAnEventHandler(this);

        searchPredictionListView.setVisible(false);

        tempPredictionResults = new ArrayList<String>();
        searchPredictionResults = FXCollections.observableArrayList(tempPredictionResults);
        // Attach ObservableList to ListView
        searchPredictionListView.setItems(searchPredictionResults);

        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * This method refreshes the SearchPredictionPanel with results that start with `newText`
     * @param newText
     */
    private void updatePredictionResults(String newText) {
        searchPredictionResults.clear();
        tempPredictionResults = SEARCH_PREDICTION_RESULTS_INITIAL
                .stream()
                .filter(p -> p.startsWith(newText))
                .collect(toCollection(ArrayList::new));

        searchPredictionResults.addAll(tempPredictionResults);
        searchPredictionListView.setItems(searchPredictionResults);
        searchPredictionListView.getSelectionModel().selectFirst();

        // Set the prediction to be invisible if there is nothing typed in the Command Box
        // Or if there is no prediction to show
        if (newText.equals("") || searchPredictionResults.isEmpty()) {
            searchPredictionListView.setVisible(false);
        } else {
            searchPredictionListView.setVisible(true);
        }
    }

    private void setEventHandlerForSelectionChangeEvent() {
        searchPredictionListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in search prediction panel changed to : '" + newValue + "'");
                        raise(new SearchPredictionPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    @Subscribe
    private void handleCommandBoxKeyInputEvent(CommandBoxKeyInputEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updatePredictionResults(event.getCommandText());
    }


    @Subscribe
    private void handleSearchPredictionPanelNextSelectionEvent(SearchPredictionPanelNextSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        searchPredictionListView.getSelectionModel().selectNext();
    }

    @Subscribe
    private void handleSearchPredictionPanelPreviousSelectionEvent(SearchPredictionPanelPreviousSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        searchPredictionListView.getSelectionModel().selectPrevious();
    }

    @Subscribe
    private void handleSearchPredictionPanelHideEvent(SearchPredictionPanelHideEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        searchPredictionListView.setVisible(false);
    }
}
