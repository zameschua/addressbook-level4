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
import seedu.address.commons.events.ui.CommandBoxContentsChangedEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelHideEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelNextSelectionEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelPreviousSelectionEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelSelectionChangedEvent;

//@@author zameschua
/**
 * Panel containing command predictions
 * It only shows when the user types something into the search box
 * And a command prediction is expected
 * Works similarly to Google's search prediction.
 */
public class CommandPredictionPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(CommandPredictionPanel.class);
    private static final String FXML = "CommandPredictionPanel.fxml";
    private static final ArrayList<String> COMMAND_PREDICTION_RESULTS_INITIAL =
            new ArrayList<String>(Arrays.asList(
                    "help", "add", "list", "listalltags", "edit", "find", "delete", "select",
                    "history", "calendar", "addEvent", "mass", "sms", "undo", "redo", "clear", "exit"));

    private static ObservableList<String> commandPredictionResults;
    // tempPredictionResults used to store the results from filtering through COMMAND_PREDICTION_RESULTS_INITIAL
    private ArrayList<String> tempPredictionResults;

    @FXML
    private ListView<String> commandPredictionListView;

    public CommandPredictionPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        initDataStructures();
        initListView();
        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * Helper method for the constructor to initialise the various data structures used
     * in the CommandPredictionPanel
     */
    private void initDataStructures() {
        tempPredictionResults = new ArrayList<String>();
        commandPredictionResults = FXCollections.observableArrayList(tempPredictionResults);
    }

    /**
     * Helper method for the constructor to initialise the ListView UI
     */
    private void initListView() {
        commandPredictionListView.setVisible(false);
        // Attach ObservableList to ListView
        commandPredictionListView.setItems(commandPredictionResults);
    }

    /**
     * This method refreshes the CommandPredictionPanel with results that start with `newText`
     * @param newText
     */
    private void updatePredictionResults(String newText) {
        commandPredictionResults.clear();
        tempPredictionResults = COMMAND_PREDICTION_RESULTS_INITIAL
                .stream()
                .filter(p -> p.startsWith(newText))
                .collect(toCollection(ArrayList::new));

        commandPredictionResults.addAll(tempPredictionResults);
        commandPredictionListView.setItems(commandPredictionResults);
        commandPredictionListView.getSelectionModel().selectFirst();

        // Set the prediction to be invisible if there is nothing typed in the Command Box
        // Or if there is no prediction to show
        if (newText.equals("") || commandPredictionResults.isEmpty()) {
            commandPredictionListView.setVisible(false);
        } else {
            commandPredictionListView.setVisible(true);
        }
    }

    /**
     * Helper method for the constructor
     * Attaches an event handler to the CommandPredictionPanel to track when
     * the user changes the CommandPrediction
     *
     * The method fires another event to the {@link seedu.address.commons.core.EventsCenter},
     * which is handled by {@link CommandBox} and in turns changes its state
     */
    private void setEventHandlerForSelectionChangeEvent() {
        commandPredictionListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in command prediction panel changed to : '" + newValue + "'");
                        raise(new CommandPredictionPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    @Subscribe
    private void handleCommandBoxContentsChangedEvent(CommandBoxContentsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updatePredictionResults(event.getCommandText());
    }

    @Subscribe
    private void handleCommandPredictionPanelNextSelectionEvent(CommandPredictionPanelNextSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionListView.getSelectionModel().selectNext();
    }

    @Subscribe
    private void handleCommandPredictionPanelPreviousSelectionEvent(
            CommandPredictionPanelPreviousSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionListView.getSelectionModel().selectPrevious();
    }

    @Subscribe
    private void handleCommandPredictionPanelHideEvent(CommandPredictionPanelHideEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionListView.setVisible(false);
    }
}
