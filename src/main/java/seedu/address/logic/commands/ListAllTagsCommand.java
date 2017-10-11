package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

/**
 * List all tags that exist in software to user
 */
public class ListAllTagsCommand extends Command {

    public static final String COMMAND_WORD = "listalltags";

    public static final String MESSAGE_SUCCESS = "Listed all tags";

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
