package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.tag.Tag;

/**
 * Indicates a request to jump to the list of tags
 */

public class JumpToListAllTagsRequestEvent extends BaseEvent {


    public JumpToListAllTagsRequestEvent () { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
