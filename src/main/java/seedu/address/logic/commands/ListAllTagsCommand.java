package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

/**
 * List all tags that exist in software to user
 */
public class ListAllTagsCommand extends Command {

    public static final String COMMAND_WORD = "listalltags";

    public static final String MESSAGE_SUCCESS = "All tags listed! They are: ";

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        ObservableList<Tag> allTags = model.getFilteredTagList();

        StringBuilder sb = new StringBuilder();
        for (Tag tag : allTags) {
            sb.append(tag.tagName + " ");
        }

        return new CommandResult(MESSAGE_SUCCESS + sb);
    }
}
