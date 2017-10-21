package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import javafx.fxml.FXML;
import seedu.address.commons.events.ui.CommandBoxKeyInputEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

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
    private static ObservableList<String> SEARCH_PREDICTION_RESULTS_INITIAL = FXCollections.observableArrayList(
            "help", "add", "list", "edit", "find", "delete", "select",
            "history", "undo", "redo", "clear", "exit");
    
    @FXML
    private ListView<String> searchPredictionListView;

    public SearchPredictionPanel() {
        super(FXML);
        registerAsAnEventHandler(this);

        ObservableList<String> searchPredictionResults = SEARCH_PREDICTION_RESULTS_INITIAL;
        
        // Attach ObservableList to ListView
        searchPredictionListView.setItems(searchPredictionResults);
    }

    public void updatePredictionResults(String newText) {
        searchPredictionResults = SEARCH_PREDICTION_RESULTS_INITIAL
                .stream()
                .filter(p -> p.startsWith(newText))
                .collect(toCollection(FXCollections::observableArrayList));

        System.out.println(searchPredictionResults);
        searchPredictionListView.refresh();
        
        // SETVISIBLE DOESN'T WORK YET
        if (newText.equals("")) {
            System.out.println("EMPTY");
            searchPredictionListView.setVisible(false);

        } else {
            System.out.println("doens't seem empty");
            searchPredictionListView.setVisible(true);
            
        }
    }

    @Subscribe
    private void handleCommandBoxKeyInputEvent(CommandBoxKeyInputEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updatePredictionResults(event.getCommandText());
    }
}
