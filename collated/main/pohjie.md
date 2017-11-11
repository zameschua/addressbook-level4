# pohjie
###### \java\seedu\address\commons\events\ui\JumpToListAllTagsRequestEvent.java
``` java
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
```
###### \java\seedu\address\logic\commands\AddAttendanceCommand.java
``` java
/**
 * Increment attendance count of an existing person in the address book.
 */
public class AddAttendanceCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Increment the attendance person identified"
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) ";

    public static final String MESSAGE_ADD_ATTENDANCE_SUCCESS = "Edited Person's Attendance: %1$s";
    public static final String MESSAGE_ATTENDANCE_NOT_ADDED =
            "Either invalid index or the attended sessions is already 8";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditAttendancePersonDescriptor editAttendancePersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public AddAttendanceCommand(Index index, EditAttendancePersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editAttendancePersonDescriptor = new EditAttendancePersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editAttendancePersonDescriptor);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (PersonMaxAttendanceException pmae) {
            throw new CommandException(MESSAGE_ATTENDANCE_NOT_ADDED);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_ATTENDANCE_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditAttendancePersonDescriptor editAttendancePersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editAttendancePersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editAttendancePersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editAttendancePersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editAttendancePersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editAttendancePersonDescriptor.getTags().orElse(personToEdit.getTags());
```
###### \java\seedu\address\logic\commands\AddAttendanceCommand.java
``` java
        Attendance attendance = editAttendancePersonDescriptor.getAttendance();
        ProfilePicture updatedProfilePicture = editAttendancePersonDescriptor.getProfilePicture();
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, date, updatedTags);
```
###### \java\seedu\address\logic\commands\AddAttendanceCommand.java
``` java
        private Attendance attendance;
        private ProfilePicture profilePicture;

```
###### \java\seedu\address\logic\commands\AddAttendanceCommand.java
``` java
            this.attendance = toCopy.attendance;
            this.attendance.addAttendance();
            this.profilePicture = toCopy.profilePicture;
```
###### \java\seedu\address\logic\commands\AddAttendanceCommand.java
``` java
        public void setAttendance(Attendance attendance) {
            this.attendance = attendance;
        }

        public Attendance getAttendance() {
            return attendance;
        }

        public void setProfilePicture(ProfilePicture profilePicture) {
            this.profilePicture = profilePicture;
        }

        public ProfilePicture getProfilePicture() {
            return profilePicture;
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        private ProfilePicture profilePicture;
        private Attendance attendance;

```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            this.profilePicture = toCopy.profilePicture;
            this.attendance = toCopy.attendance;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setProfilePicture(ProfilePicture profilePicture) {
            this.profilePicture = profilePicture;
        }

        public ProfilePicture getProfilePicture() {
            return profilePicture;
        }

        public void setAttendance(Attendance attendance) {
            this.attendance = attendance;
        }

        public Attendance getAttendance() {
            return attendance;
        }
```
###### \java\seedu\address\logic\commands\ListAllTagsCommand.java
``` java
/**
 * List all tags that exist in software to user
 */
public class ListAllTagsCommand extends Command {

    public static final String COMMAND_WORD = "listalltags";

    public static final String MESSAGE_SUCCESS = "All tags listed!";

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        ObservableList<Tag> allTags = model.getFilteredTagList();

        EventsCenter.getInstance().post(new JumpToListAllTagsRequestEvent());
        return new CommandResult(getMessageForTagListShownSummary(allTags));
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return model.getFilteredTagList();
    }
```
###### \java\seedu\address\logic\parser\AddAttendanceCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class AddAttendanceCommandParser implements Parser<AddAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAttendanceCommand
     * and returns an AddAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttendanceCommand.MESSAGE_USAGE));
        }

        EditAttendancePersonDescriptor editPersonDescriptor = new EditAttendancePersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddAttendanceCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ListAllTagsCommand.COMMAND_WORD:
            return new ListAllTagsCommand();
        case AddAttendanceCommand.COMMAND_WORD:
            return new AddAttendanceCommandParser().parse(arguments);
```
###### \java\seedu\address\model\AddressBook.java
``` java
        try {
            persons.add(newPerson);
            syncMasterTagListWith(newPerson);
        } catch (DuplicatePersonException e) {
            throw new DuplicatePersonException();
        }
```
###### \java\seedu\address\model\AddressBook.java
``` java
        try {
            persons.setPerson(target, editedPerson);
            syncMasterTagListWith(editedPerson);
        } catch (DuplicatePersonException e) {
            throw new DuplicatePersonException();
        } catch (PersonNotFoundException e) {
            throw new PersonNotFoundException();
        }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    }

    //// event-level operations

    /**
     * Adds a event to the calendar.
     */
    public void addEvent(ReadOnlyCalendarEvent event) {
        CalendarEvent newEvent = new CalendarEvent(event);
        try {
            CalendarApi.addEvent(newEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        person.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these persons:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Person)
     */
    private void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    /**
     * Method to get UniqueTagList
     * @return UniqueTagList
     */
    public UniqueTagList getUniqueTagList() {
        return tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Filtered Tag List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tag} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return FXCollections.unmodifiableObservableList(filteredTags);
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }
}
```
###### \java\seedu\address\model\person\Attendance.java
``` java
/**
 * Represents a Person's attendance in the address book.
 * Hardcoded now till further versions support actual implementation of attendance
 */
public class Attendance {

    public final int maxAttendance = 8;
    private int attended;
    private int missed;

    /**
     * Default constructor
     * All Person will get this default image in V1.4 and V1.5
     */
    public Attendance() {
        attended = 0;
        missed = 8;
    }


    /**
     * User can choose to set the number of attended sessions if it is not zero.
     * @param attended
     * @throws IllegalValueException
     */
    public Attendance(int attended) throws IllegalValueException { // make sure attendance is valid. Tests here
        this.attended = attended;
        missed = maxAttendance - attended;
    }

    public int getAttended() {
        return attended;
    }

    public int getMissed() {
        return missed;
    }

    /**
     * Increments attended by one and decrements missed by one
     * to signify that a person has attended a session.
     */
    public void addAttendance() {
        attended++;
        missed--;
    }

    @Override
    public String toString() {
        return "Attended is: " + attended + ", and missed is: " + missed;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Attendance
                && this.attended == (((Attendance) other).attended)
                && this.missed == (((Attendance) other).missed));

    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    private ProfilePicture profilePic;
    private Attendance attendance;
```
###### \java\seedu\address\model\person\Person.java
``` java

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getJoinDate(),
                source.getTags());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ProfilePicture getProfilePic() {
        return profilePic;
    }

    public Attendance getAttendance() {
        return attendance;
    }

```
###### \java\seedu\address\model\person\ProfilePicture.java
``` java
/**
 * Represents a Person's profile picture in the address book.
 * Hardcoded now till further versions support image uploading
 */
public class ProfilePicture {

    public final String profilePicPath;

    /**
     * Default constructor
     * All Person will get this default image in V1.4 and V1.5
     */
    public ProfilePicture() {
        profilePicPath = "/images/defaultProfilePic.png";
    }

    /**
     * User can choose to add in profile pictures hosted on personal file hosting service.
     * @param path
     * @throws IllegalValueException
     */
    public ProfilePicture(String path) throws IllegalValueException {
        requireNonNull(path);
        profilePicPath = path;
    }

    @Override
    public String toString() {
        return profilePicPath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ProfilePicture
                && this.profilePicPath.equals(((ProfilePicture) other).profilePicPath));
    }

    @Override
    public int hashCode() {
        return profilePicPath.hashCode();
    }
}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ProfilePicture getProfilePic();
    Attendance getAttendance();
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the tag list panel
     */
    public void handleTagListPanel() {
        tagListPanel = new TagListPanel(logic.getFilteredTagList());
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(tagListPanel.getRoot());
    }

    /**
     * Loads the information of the person in the BrowserPanel position
     * @param person
     */
    private void loadPersonInfo(ReadOnlyPerson person) {
        personInfo = new PersonInfo(person);
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(personInfo.getRoot());
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInfo(event.getNewSelection().person);
    }

    @Subscribe
    private void handleListAllTagsEvent(JumpToListAllTagsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleTagListPanel();
    }
}
```
###### \java\seedu\address\ui\PersonInfo.java
``` java
/**
 * Panel containing the list of persons.
 */
public class PersonInfo extends UiPart<Region> {
    private static final String FXML = "PersonInfo.fxml";
    public final ReadOnlyPerson person;
    private final Logger logger = LogsCenter.getLogger(PersonInfo.class);

    @FXML
    private ImageView profilePic;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private PieChart attendance;

    public PersonInfo (ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        profilePic.setImage(new Image(person.getProfilePic().toString()));
        name.setText(person.getName().toString());
        address.setText(person.getAddress().toString());
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());

        // This is not bound to the person. If we change attendance or missed when the person is
        // shown in browser panel this will not be reflected
        ObservableList<PieChart.Data> attendanceData = FXCollections.observableArrayList(
                new PieChart.Data("Present", person.getAttendance().getAttended()),
                new PieChart.Data("Absent", person.getAttendance().getMissed()));

        attendance.setData(attendanceData);
    }

}
```
###### \java\seedu\address\ui\TagCard.java
``` java
/**
 * An UI component that displays information of a {@code Tag}.
 */
public class TagCard extends UiPart<Region> {
    private static final String FXML = "TagListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Tag tag;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label tagString;


    public TagCard(Tag tag, int displayedIndex) {
        super(FXML);
        this.tag = tag;
        id.setText(displayedIndex + ". ");
        tagString.setText(tag.tagName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCard)) {
            return false;
        }

        // state check
        TagCard card = (TagCard) other;
        return id.getText().equals(card.id.getText())
                && tag.equals(card.tag);
    }
}
```
###### \java\seedu\address\ui\TagListPanel.java
``` java
/**
 * Panel containing the list of tags.
 */
public class TagListPanel extends UiPart<Region> {
    private static final String FXML = "TagListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TagListPanel.class);

    @FXML
    private ListView<TagCard> tagListView;

    public TagListPanel(ObservableList<Tag> tagList) {
        super(FXML);
        setConnections(tagList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Tag> tagList) {
        ObservableList<TagCard> mappedList = EasyBind.map(tagList, (tag) -> new TagCard(tag, tagList.indexOf(tag) + 1));
        tagListView.setItems(mappedList);
        tagListView.setCellFactory(listView -> new TagListViewCell());
        //setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        tagListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in tag list panel changed to : '" + newValue + "'");
                        raise(new TagPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code TagCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            tagListView.scrollTo(index);
            tagListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListAllTagsRequestEvent(JumpToListAllTagsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TagCard}.
     */
    class TagListViewCell extends ListCell<TagCard> {

        @Override
        protected void updateItem(TagCard tagCard, boolean empty) {
            super.updateItem(tagCard, empty);

            if (empty || tagCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(tagCard.getRoot());
            }
        }
    }
}
```
###### \resources\view\PersonInfo.fxml
``` fxml

<VBox styleClass="card" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane VBox.vgrow="ALWAYS">
        <columnConstraints>
          <ColumnConstraints halignment="CENTER" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="165.0" valignment="CENTER" vgrow="SOMETIMES" />
          <RowConstraints minHeight="10.0" prefHeight="74.0" valignment="CENTER" vgrow="SOMETIMES" />
          <RowConstraints minHeight="10.0" prefHeight="139.0" valignment="CENTER" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <GridPane alignment="CENTER" GridPane.halignment="CENTER" GridPane.hgrow="ALWAYS" GridPane.rowIndex="1" GridPane.valignment="CENTER">
              <columnConstraints>
                <ColumnConstraints halignment="CENTER" minWidth="10.0" prefWidth="100.0" />
              </columnConstraints>
              <rowConstraints>
                <RowConstraints prefHeight="30.0" valignment="CENTER" vgrow="SOMETIMES" />
                <RowConstraints prefHeight="30.0" valignment="CENTER" vgrow="SOMETIMES" />
                  <RowConstraints prefHeight="30.0" valignment="CENTER" vgrow="SOMETIMES" />
                  <RowConstraints prefHeight="30.0" valignment="CENTER" vgrow="SOMETIMES" />
              </rowConstraints>
               <children>
                  <Label fx:id="name" alignment="CENTER" text="Name" textAlignment="JUSTIFY">
                     <font>
                        <Font size="18.0" />
                     </font>
                  </Label>
                  <Label fx:id="phone" alignment="CENTER" text="Phone" textAlignment="JUSTIFY" GridPane.rowIndex="1">
                     <font>
                        <Font size="18.0" />
                     </font>
                  </Label>
                  <Label fx:id="email" alignment="CENTER" text="Email" textAlignment="JUSTIFY" GridPane.hgrow="ALWAYS" GridPane.rowIndex="3" GridPane.valignment="CENTER" GridPane.vgrow="ALWAYS">
                     <font>
                        <Font size="18.0" />
                     </font>
                  </Label>
                  <Label fx:id="address" alignment="CENTER" text="Address" GridPane.rowIndex="2">
                     <font>
                        <Font size="18.0" />
                     </font>
                  </Label>
               </children>
            </GridPane>
            <ImageView fx:id="profilePic" fitHeight="150.0" fitWidth="200.0" pickOnBounds="true" preserveRatio="true">
               <GridPane.margin>
                  <Insets />
               </GridPane.margin></ImageView>
            <PieChart fx:id="attendance" GridPane.rowIndex="2" />
         </children>
      </GridPane>
   </children>
</VBox>
```
###### \resources\view\TagListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="tagString" text="\$first" styleClass="cell_big_label" />
            </HBox>
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\TagListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="tagListView" VBox.vgrow="ALWAYS" />
</VBox>
```
