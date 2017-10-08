package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import javafx.fxml.FXML;
import java.util.logging.Logger;

/**
 * Panel containing search predictions
 * It only shows when the user types something into the search box
 * And a search prediction is expected
 * Kinda like Google's search prediction.
 */
public class SearchPredictionPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(SearchPredictionPanel.class);
    private static final String FXML = "SearchPredictionPanel.fxml";

    @FXML
    private ListView<String> searchPredictionListView;
    
    public SearchPredictionPanel() {
        super(FXML);
        registerAsAnEventHandler(this);

        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");
        searchPredictionListView.setItems(names);
    }
    
    public void updatePredictionResults(String newText) {
        ;
    }
}
