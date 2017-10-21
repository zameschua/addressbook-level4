package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to go the previous selection in the SearchPredictionPanel
 */
public class SearchPredictionPanelPreviousSelectionEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
