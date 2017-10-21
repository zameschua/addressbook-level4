package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import javafx.fxml.FXML;
import seedu.address.commons.events.ui.CommandBoxKeyInputEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toCollection;

/**
 * Panel containing search predictions
 * It only shows when the user types something into the search box
 * And a search prediction is expected
 * Kinda like Google's search prediction.
 */
public class SearchPredictionPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(SearchPredictionPanel.class);
    private static final String FXML = "SearchPredictionPanel.fxml";

    private static ObservableList<String> searchPredictionResults;
    // tempPredictionResults used to store the results from filtering through SEARCH_PREDICTION_RESULTS_INITIAL
    private ArrayList<String> tempPredictionResults;

    private static final ArrayList<String> SEARCH_PREDICTION_RESULTS_INITIAL =
            new ArrayList<String>( Arrays.asList(
                    "help", "add", "list", "edit", "find", "delete", "select",
                    "history", "undo", "redo", "clear", "exit"));
    
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
    }

    public void updatePredictionResults(String newText) {
        // Set the prediction to be invisible if there is nothing typed in the Command Box
        if (newText.equals("")) {
            searchPredictionListView.setVisible(false);
        } else {
            searchPredictionListView.setVisible(true);
        }
        
        searchPredictionResults.clear();
        tempPredictionResults = SEARCH_PREDICTION_RESULTS_INITIAL
                .stream()
                .filter(p -> p.startsWith(newText))
                .collect(toCollection(ArrayList::new));

        searchPredictionResults.addAll(tempPredictionResults);
        searchPredictionListView.setItems(searchPredictionResults);
    }
    
    @Subscribe
    private void handleCommandBoxKeyInputEvent(CommandBoxKeyInputEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updatePredictionResults(event.getCommandText());
    }
}
