package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
// import seedu.address.commons.events.ui.JumpToListTagRequestEvent;
// import seedu.address.commons.events.ui.TagPanelSelectionChangedEvent;
import seedu.address.model.tag.Tag;

public class TagListPanel {
    /**
     * Panel containing the list of tags.
     */
    public class TagListPanel extends UiPart<Region> {
        private static final String FXML = "TagListPanel.fxml";
        private final Logger logger = LogsCenter.getLogger(TagListPanel.class);

        @FXML
        private ListView<PersonCard> personListView;

        public TagListPanel(ObservableList<Tag> tagList) {
            super(FXML);
            setConnections(tagList);
            registerAsAnEventHandler(this);
        }

        private void setConnections(ObservableList<Tag> personList) {
            ObservableList<PersonCard> mappedList = EasyBind.map(
                    personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
            personListView.setItems(mappedList);
            personListView.setCellFactory(listView -> new PersonListViewCell());
            setEventHandlerForSelectionChangeEvent();
        }

        private void setEventHandlerForSelectionChangeEvent() {
            personListView.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                            raise(new PersonPanelSelectionChangedEvent(newValue));
                        }
                    });
        }

        /**
         * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
         */
        private void scrollTo(int index) {
            Platform.runLater(() -> {
                personListView.scrollTo(index);
                personListView.getSelectionModel().clearAndSelect(index);
            });
        }

        @Subscribe
        private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            scrollTo(event.targetIndex);
        }

        /**
         * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
         */
        class PersonListViewCell extends ListCell<PersonCard> {

            @Override
            protected void updateItem(PersonCard person, boolean empty) {
                super.updateItem(person, empty);

                if (empty || person == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(person.getRoot());
                }
            }
        }
}
